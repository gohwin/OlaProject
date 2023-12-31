package com.ola.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ola.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT p FROM Product p WHERE p.prodCategory = :category")
	List<Product> findByProdCategory(@Param("category") int category);
	
	@Query("SELECT p FROM Product p WHERE lower(p.productName) LIKE lower(concat('%', :search, '%'))")
	Page<Product> searchProductsByNameIgnoreCase(String search, Pageable pageable);

}
