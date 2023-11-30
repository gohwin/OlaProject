package com.ola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ola.entity.Member;
import com.ola.repository.MemberRepository;

@Controller
public class JoinController {
	
	@Autowired
	private MemberRepository memberRepo;
	
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

	 @PostMapping("/register")
	 public String processRegistration(
		        @RequestParam(value = "email") String email,
		        @RequestParam(value = "emailDomain") String domain,
		        @RequestParam(value = "directEmail", required = false) String directEmail,
		        @RequestParam(value = "memberId") String memberId,
		        Member member, Model model) {

		    // 이미 존재하는 memberId 확인
		    if (memberRepo.existsById(memberId)) {
		        model.addAttribute("idExistsError", "이미 사용 중인 아이디입니다.");
		        return "join/joinForm"; // 동일한 아이디가 있을 경우 회원가입 폼으로 다시 이동
		    }

		    String memberEmail;

		    if ("direct".equals(domain)) {
		        memberEmail = email + "@" + directEmail;
		    } else {
		        memberEmail = email + "@" + domain;
		    }

		    // 회원 정보 저장
		    member.setMemberId(memberId);
		    member.setEmail(memberEmail);
		    memberRepo.save(member);

		    return "redirect:/system/login";
	 }

    
}

