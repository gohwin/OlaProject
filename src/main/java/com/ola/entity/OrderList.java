package com.ola.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ORDER_LIST")
public class OrderList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_no")
	private Long orderNo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member member;

	@ElementCollection
	@CollectionTable(name = "order_product_quantity", joinColumns = @JoinColumn(name = "order_no"))
	@MapKeyColumn(name = "product_no")
	@Column(name = "quantity")
	@Builder.Default
	private Map<Long, Integer> productQuantities = new HashMap<>();

	@CreatedDate
	@Column(name = "regdate", updatable = false)
	private Date orderDate;
	
}