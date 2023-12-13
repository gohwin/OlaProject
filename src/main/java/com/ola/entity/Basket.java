package com.ola.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long basketId;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "basket_product",
        joinColumns = @JoinColumn(name = "basket_id"),
        inverseJoinColumns = @JoinColumn(name = "product_no")
    )
    @Builder.Default
    private Set<Product> products = new HashSet<>();

    @ElementCollection
    @CollectionTable(
        name = "basket_product_quantity",
        joinColumns = @JoinColumn(name = "basket_id")
    )
    @MapKeyColumn(name = "product_no")
    @Column(name = "quantity")
    @Builder.Default
    private Map<Long, Integer> productQuantityMap = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        // products 세트에 제품 추가
        this.products.add(product);
        // productQuantityMap에 수량 추가
        this.productQuantityMap.put(product.getProductNo(), quantity);
    }
    
    public void removeProduct(Long productNo) {
        // products 세트에서 상품 제거
        this.products.removeIf(product -> product.getProductNo().equals(productNo));
        // productQuantityMap에서 해당 상품의 수량 제거
        this.productQuantityMap.remove(productNo);
    }
}
