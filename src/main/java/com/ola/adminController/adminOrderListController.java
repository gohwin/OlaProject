package com.ola.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.OrderList;
import com.ola.entity.OrderStatus;
import com.ola.repository.CommunityRepository;
import com.ola.repository.OrderListRepository;
import com.ola.repository.ProductRepository;
import com.ola.repository.TradeBoardRepository;
import com.ola.service.BoardService;

@Controller
public class adminOrderListController {
	@Autowired
	private CommunityRepository communityRepo;

	@Autowired
	private TradeBoardRepository tradeRepo;

	@Autowired
	private BoardService boardService;

	@Autowired
	private ProductRepository prodRepo;

	@Autowired
	private OrderListRepository orderRepo;

	@GetMapping("/admin/order_list")
	public String showOrderList(Model model) {
		List<OrderList> orderList = orderRepo.findAll();
		model.addAttribute("orderList", orderList);
		return "admin/order_list";
	}

	@PostMapping("/admin/updateOrderStatus")
	public String updateOrderStatus(@RequestParam("orderNo") Long orderNo, @RequestParam("status") OrderStatus status) {
		OrderList order = orderRepo.findById(orderNo).orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다"));
		order.setOrderStatus(status);
		orderRepo.save(order);
		return "redirect:/admin/order_list";
	}

	@GetMapping("/admin/orderDetails")
	public String orderDetails(@RequestParam("orderNo") Long orderNo, Model model) {
		// orderNo를 사용하여 주문 세부 정보를 가져오는 서비스 메소드를 호출
		OrderList orderDetails = orderRepo.getOrderDetails(orderNo);

		// orderDetails를 모델에 추가
		model.addAttribute("orderDetails", orderDetails);

		// orderDetails.html로 이동
		return "admin/orderDetails"; // 주문 세부 정보를 표시할 Thymeleaf 템플릿 파일명 
	}
}
