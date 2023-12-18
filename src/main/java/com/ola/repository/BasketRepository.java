package com.ola.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ola.entity.Basket;
import com.ola.entity.Member;


@Transactional
public interface BasketRepository extends JpaRepository<Basket, Long> {
	@Query("SELECT b FROM Basket b WHERE b.member=:member")
	Basket findByUser(@Param("member") Member member);
	
	@Modifying
	@Query("DELETE FROM Basket r WHERE r.member.memberId LIKE %:memberId%")
	void deleteByMember(@Param("memberId") String memberId);
}