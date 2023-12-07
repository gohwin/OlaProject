package com.ola.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Basket;
import com.ola.entity.Product;
import com.ola.repository.BasketRepository;
import com.ola.repository.ProductRepository;

@Controller
public class OrderController {
	
	@Autowired
	private BasketRepository basketRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
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


}
