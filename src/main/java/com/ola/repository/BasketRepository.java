package com.ola.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ola.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    // 필요한 경우 사용자 정의 쿼리나 메서드를 여기에 추가할 수 있습니다.
}