package com.ola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ola.entity.Member;
import com.ola.security.SecurityUser;
import com.ola.service.OrderListService;
import com.ola.service.UserService;

@Controller
@RequestMapping("mypage")
public class MyPageController {

	@Autowired
	private UserService userService;
	@Autowired
	private OrderListService orderService;

	@GetMapping("/mypages")
	public String myPage(Model model, @AuthenticationPrincipal SecurityUser principal) {
		Member user = principal.getMember();
		model.addAttribute("user", user);
		return "mypage/mypages";
	}

	@GetMapping("/editProfile")
	public String editProfile(Model model, @AuthenticationPrincipal SecurityUser principal) {
		Member user = principal.getMember();
		model.addAttribute("user", user);
		return "mypage/editProfile";
	}

	@PostMapping("/editProfile")
	public String updateProfile(@ModelAttribute Member updatedUser, @AuthenticationPrincipal SecurityUser principal) {
		Member currentUser = principal.getMember();

		currentUser.setName(updatedUser.getName());
		currentUser.setEmail(updatedUser.getEmail());
		currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
		currentUser.setAddress(updatedUser.getAddress());
		currentUser.setDetailedAddress(updatedUser.getDetailedAddress());

		userService.updateUser(currentUser);

		return "redirect:/mypage/mypages";
	}

	@GetMapping("/orderHistory")
	public String orderHistory(Model model, @AuthenticationPrincipal SecurityUser principal) {
		Member user = principal.getMember();
		model.addAttribute("orderHistory", orderService.getOrderHistory(user));
		return "mypage/orderHistory"; 
	}
}
