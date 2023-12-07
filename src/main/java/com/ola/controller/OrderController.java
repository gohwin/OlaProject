package com.ola.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Basket;
import com.ola.entity.Member;
import com.ola.entity.OrderList;
import com.ola.entity.Product;
import com.ola.repository.BasketRepository;
import com.ola.repository.OrderListRepository;
import com.ola.repository.ProductRepository;
import com.ola.security.SecurityUser;

@Controller
public class OrderController {
	
	@Autowired
	private BasketRepository basketRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private OrderListRepository orderRepo;
	
	@GetMapping("/order")
	public String orderView(
	    @RequestParam("basketId") Long basketId,
	    @RequestParam("productNo") Long productNo,
	    Model model
	) {
	    // basketId를 사용하여 장바구니 정보를 조회합니다.
	    Basket basket = basketRepo.findById(basketId).orElse(null);
	    
	    // productId를 사용하여 주문할 상품 정보를 조회합니다.
	    Product product = prodRepo.findById(productNo).orElse(null);
	    
	    // 장바구니와 상품 정보를 모델에 추가합니다.
	    model.addAttribute("basket", basket);
	    model.addAttribute("product", product);
	    
	    // 주문 페이지로 이동합니다.
	    return "order/order"; // 주문 페이지의 Thymeleaf 템플릿 파일명을 사용합니다.
	}
	
	@PostMapping("/order")
	public String submitOrder(
	    @RequestParam Map<String, String> allParams,
	    Model model) {

	    OrderList orderList = new OrderList();
	    SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Member member = securityUser.getMember();
	    orderList.setMember(member);
	    orderList.setOrderDate(new Date());

	    Map<Long, Integer> productQuantities = new HashMap<>();
	    Map<Long, Product> productsMap = new HashMap<>();

	    for (String paramName : allParams.keySet()) {
	        if (paramName.startsWith("quantity_")) {
	            String[] parts = paramName.split("_");
	            Long productNo = Long.parseLong(parts[1]);
	            Integer quantity = Integer.parseInt(allParams.get(paramName));

	            productQuantities.put(productNo, quantity);

	            Product product = prodRepo.findById(productNo).orElse(null);
	            if (product != null) {
	                productsMap.put(productNo, product);
	            }
	        }
	    }

	    orderList.setProductQuantities(productQuantities);
	    orderRepo.save(orderList);

	    model.addAttribute("order", orderList);
	    model.addAttribute("productsMap", productsMap);
	    return "order/orderConfirm";
	}
	
	@GetMapping("/orderDetails")
	public String orderDetails(@RequestParam("orderNo") Long orderNo, Model model) {
	    // orderNo를 사용하여 주문 세부 정보를 가져오는 서비스 메소드를 호출
	    OrderList orderDetails = orderRepo.getOrderDetails(orderNo);

	    // orderDetails를 모델에 추가
	    model.addAttribute("orderDetails", orderDetails);

	    // orderDetails.html로 이동
	    return "order/orderDetail"; // 주문 세부 정보를 표시할 Thymeleaf 템플릿 파일명
	}











}