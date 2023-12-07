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

    @ManyToOne
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
    
    public void addProduct(Long productId, int quantity) {
        this.productQuantityMap.put(productId, quantity);
    }

}

