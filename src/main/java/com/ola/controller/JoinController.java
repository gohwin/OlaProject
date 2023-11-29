package com.ola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ola.entity.Member;
import com.ola.entity.Role;
import com.ola.repository.MemberRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Controller
public class JoinController {
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
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
	public String registerUser(@ModelAttribute Member member) {
		Member newMem = Member.builder()
	            .name(member.getName())
	            .memberId(member.getMemberId())
	            .password(encoder.encode(member.getPassword()))
	            .phoneNumber(member.getPhoneNumber())
	            .role(Role.ROLE_MEMBER)
	            .address(member.getAddress())
	            .detailedAddress(member.getDetailedAddress())
	            .email(member.getEmail())
	            .build();
		
		memberRepo.save(newMem);

		
		return "redirect:/system/login";
	}
	
	


    
}

