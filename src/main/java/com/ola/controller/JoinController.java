package com.ola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ola.entity.Member;

@Controller
public class JoinController {
	
	// 회원가입 약관 페이지 이동
	@GetMapping("/join/contract")
    public String showContractPage() {
        return "join/contract"; // "contract"는 Thymeleaf 템플릿의 이름을 가정합니다.
    }
	
	// 회원가입 작성 폼 이동
	@PostMapping("/joinForm")
    public String joinFormPage(Model model) {
        // 빈 User 객체를 모델에 추가하여 Thymeleaf에서 사용
		
		model.addAttribute("member", new Member());
		 
        return "join/joinForm";
    }


    
}

