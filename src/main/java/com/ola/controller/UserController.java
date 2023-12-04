package com.ola.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ola.entity.Member;
import com.ola.security.SecurityUser;
import com.ola.service.MemberService;
import com.ola.service.UserService;

@RestController
public class UserController {

	private MemberService memberService;
	
	private UserService userService;

	@Autowired
	public UserController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/checkIdAvailability")
	public ResponseEntity<Map<String, Boolean>> checkIdAvailability(@RequestParam String memberId) {
		boolean isAvailable = !memberService.isMemberIdExists(memberId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("available", isAvailable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/user/mypage")
	public String myPage(Model model, @AuthenticationPrincipal SecurityUser principal) {
		Member user = principal.getMember();
		model.addAttribute("user", user);
		return "user/mypage";
	}

	@GetMapping("/user/editProfile")
	public String editProfile(Model model, @AuthenticationPrincipal SecurityUser principal) {
		Member user = principal.getMember();
		model.addAttribute("user", user);
		return "editProfile";
	}

	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute Member updatedUser, @AuthenticationPrincipal SecurityUser principal) {
		Member currentUser = principal.getMember();

		currentUser.setName(updatedUser.getName());
		currentUser.setEmail(updatedUser.getEmail());
		currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
		currentUser.setAddress(updatedUser.getAddress());
		currentUser.setDetailedAddress(updatedUser.getDetailedAddress());


		userService.updateUser(currentUser);

		return "redirect:/mypage";
	}
}