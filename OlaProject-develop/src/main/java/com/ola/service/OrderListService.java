package com.ola.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ola.entity.Member;
import com.ola.entity.OrderList;
import com.ola.repository.OrderListRepository;

@Service
public class OrderListService {

	@Autowired
    private final OrderListRepository orderListRepository;

    public OrderListService(OrderListRepository orderListRepository) {
        this.orderListRepository = orderListRepository;
    }

    // 특정 회원의 주문 내역 조회
    public List<OrderList> getOrderHistory(Member member) {
        return orderListRepository.findByMember(member);
    }
}
