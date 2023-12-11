package com.ola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ola.entity.Member;
import com.ola.entity.OrderList;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {
	
	@Query("SELECT o FROM OrderList o WHERE o.member=:member")
	List<OrderList> findByMember(@Param(value="member")Member member);
	
	@Query("SELECT o FROM OrderList o WHERE o.orderNo=:orderNo")
	OrderList getOrderDetails(@Param(value="orderNo")Long orderNo);
}