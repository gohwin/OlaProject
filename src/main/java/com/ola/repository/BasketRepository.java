package com.ola.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ola.entity.Basket;
import com.ola.entity.Member;

public interface BasketRepository extends JpaRepository<Basket, Long> {
	@Query("SELECT b FROM Basket b WHERE b.member=:member")
	Basket findByUser(@Param("member") Member member);
}