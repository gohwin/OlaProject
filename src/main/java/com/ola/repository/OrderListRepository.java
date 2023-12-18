package com.ola.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ola.entity.Member;
import com.ola.entity.OrderList;


@Transactional
public interface OrderListRepository extends JpaRepository<OrderList, Long> {
	
	@Query("SELECT o FROM OrderList o WHERE o.member=:member")
	List<OrderList> findByMember(@Param(value="member")Member member);
	
	@Query("SELECT o FROM OrderList o WHERE o.orderNo=:orderNo")
	OrderList getOrderDetails(@Param(value="orderNo")Long orderNo);
	
	// 날짜 필터링을 위한 새로운 메소드
    @Query("SELECT o FROM OrderList o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<OrderList> findOrdersBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Modifying
    @Query("DELETE FROM OrderList r WHERE r.member.memberId LIKE %:memberId%")
	void deleteByMember(@Param("memberId") String memberId);
}