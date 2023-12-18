package com.ola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ola.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT p FROM Product p WHERE p.prodCategory = :category")
	List<Product> findByProdCategory(@Param("category") int category);
	
	/* 상품 번호를 조회해서 주문리스트에 상품이름 출력하기*/
	Product findByProductNo(Long productNo);
}
