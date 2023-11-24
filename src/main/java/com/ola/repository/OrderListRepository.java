package com.ola.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ola.entity.OrderList;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {
}