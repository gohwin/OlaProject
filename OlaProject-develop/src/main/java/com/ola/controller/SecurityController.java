package com.ola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

	/*
	 *  로그인 화면 표시
	 */
	@GetMapping("/system/login")
	public void login() {

	}
	
	@GetMapping("/system/accessDenied")
	public void accessDenied() {
		
	}
	

	@GetMapping("/admin/adminPage")
	public void forAdmin() {

	}
	
	@GetMapping("/admin/logout")
	public void logout() {
		
	}
	
}
