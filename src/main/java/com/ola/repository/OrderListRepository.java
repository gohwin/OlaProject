package com.ola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ola.entity.Member;
import com.ola.entity.OrderList;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {
	List<OrderList> findByMember(Member member);
}