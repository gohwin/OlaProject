package com.ola.controller;

import java.util.Date;
import java.util.HashMap;
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

import jakarta.transaction.Transactional;

@Controller
public class OrderController {

	@Autowired
	private BasketRepository basketRepo;

	@Autowired
	private ProductRepository prodRepo;

	@Autowired
	private OrderListRepository orderRepo;

	@PostMapping("/basketOrder")
	@Transactional
	public String submitOrder(@RequestParam Map<String, String> allParams,
			@RequestParam(name = "basketId") Long basketId, Model model) {

		OrderList orderList = new OrderList();
		SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
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

				Product product = prodRepo.findById(productNo)
						.orElseThrow(() -> new RuntimeException("Product not found"));
				product.reduceInventory(quantity); // 재고 감소
				productsMap.put(productNo, product);
			}

		}

		orderList.setProductQuantities(productQuantities);
		orderRepo.save(orderList);
		basketRepo.deleteById(basketId);

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

	@PostMapping("/insertBasket")
	public String insertBasketAction(@RequestParam("productNo") Long productNo,
			@AuthenticationPrincipal SecurityUser principal, Model model) {
		int quantity = 1;
		// 현재 로그인한 사용자의 Member 객체를 가져옵니다.
		Member member = principal.getMember();

		// 사용자와 연관된 Basket 객체를 조회합니다.
		Basket basket = basketRepo.findByUser(member);

		// 장바구니에 상품을 추가합니다.
		if (basket != null) {
			// 상품을 Product 테이블에서 조회합니다.
			Product product = prodRepo.findById(productNo).orElse(null);

			if (product != null) {
				// 상품이 존재하면 장바구니에 추가합니다.
				basket.addProduct(product, quantity);
				basketRepo.save(basket);
			}
		} else {
			// 사용자와 연관된 Basket 객체가 없는 경우, 새로운 Basket 객체를 생성합니다.
			basket = Basket.builder().member(member).build();
			basketRepo.save(basket);

			// 상품을 Product 테이블에서 조회합니다.
			Product product = prodRepo.findById(productNo).orElse(null);

			if (product != null) {
				// 상품이 존재하면 장바구니에 추가합니다.
				basket.addProduct(product, quantity);
				basketRepo.save(basket);
			}
		}
		model.addAttribute("basket", basket);
		// 장바구니 페이지로 리다이렉트합니다.
		return "redirect:/mypage/basket";
	}

	@GetMapping("/directOrderView")
	public String directOrder(@RequestParam("productNo") Long productNo, @AuthenticationPrincipal SecurityUser principal, Model model) {
	    Product product = prodRepo.findById(productNo)
	                     .orElseThrow(() -> new IllegalArgumentException("Invalid product No:" + productNo));
	    Member member = principal.getMember();
	    
	    model.addAttribute("product", product);
	    model.addAttribute("member", member);

	    return "order/directOrder"; // 주문 페이지의 Thymeleaf 템플릿 파일명을 사용합니다.
	}


	@PostMapping("/directOrder")
	@Transactional
	public String submitOrder(@RequestParam("productNo") Long productNo, @RequestParam("quantity") int quantity,
	        @AuthenticationPrincipal SecurityUser principal,
	        Model model) {

	    Member member = principal.getMember();

	    // 주문 목록 생성
	    OrderList orderList = new OrderList();
	    orderList.setMember(member);
	    orderList.setOrderDate(new Date());

	    // 제품 번호와 수량을 맵에 저장합니다.
	    Map<Long, Integer> productQuantities = new HashMap<>();
	    productQuantities.put(productNo, quantity);
	    orderList.setProductQuantities(productQuantities);

	    // 주문 저장
	    orderRepo.save(orderList);

	    // 제품 재고 감소
	    Product product = prodRepo.findById(productNo)
	        .orElseThrow(() -> new RuntimeException("Product not found"));
	    product.reduceInventory(quantity);
	    prodRepo.save(product);

	    // 주문된 제품 정보를 포함하는 맵을 생성
	    Map<Long, Product> productsMap = new HashMap<>();
	    productsMap.put(productNo, product);

	    // 모델에 주문 내역과 제품 정보 맵 추가
	    model.addAttribute("order", orderList);
	    model.addAttribute("productsMap", productsMap);

	    // 주문 확인 페이지로 이동
	    return "order/orderConfirm";
	}

}
