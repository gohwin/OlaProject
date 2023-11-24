package com.ola.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ola.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
