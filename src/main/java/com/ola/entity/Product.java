package com.ola.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productNo;

	private String productName;

	private int prodCategory;

	private Long price;

	private String prodSize;

	private Long salesQuantity;

	// 재고
	private int inventory;


	// 재고 수량 감소 메소드
	public void reduceInventory(int quantity) {
		if (this.inventory < quantity) {
			throw new IllegalStateException("Insufficient inventory for product: " + this.productName);
		}
		this.inventory -= quantity;
	}
}