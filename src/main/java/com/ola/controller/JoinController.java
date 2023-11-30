package com.ola.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Member;
import com.ola.entity.Role;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



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

	@GetMapping("/join/NewFile")
    public String showContract() {
        return "join/NewFile"; // "contract"는 Thymeleaf 템플릿의 이름을 가정합니다.
    }

	// 회원가입 처리

  

	 @PostMapping("/register")
	   public String registerUser(@ModelAttribute Member member, 
								  @RequestParam(value = "email") String email,
							      @RequestParam(value = "emailDomain") String domain,
							      @RequestParam(value = "directEmail", required = false) String directEmail,
							      @RequestParam(value = "memberId") String memberId,
							       Model model) {
		 
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
		    member.setRole(Role.ROLE_MEMBER);
		    member.setPassword(encoder.encode(member.getPassword()));
		    memberRepo.save(member);

		    return "redirect:/system/login";
	 }

	      Member newMem = Member.builder()
	               .name(member.getName())
	               .memberId(member.getMemberId())
	               .password(encoder.encode(member.getPassword()))
	               .phoneNumber(member.getPhoneNumber())
	               .role(Role.ROLE_MEMBER)
	               .zipNum(member.getZipNum())
	               .address(member.getAddress())
	               .detailedAddress(member.getDetailedAddress())
	               .email(memberEmail)
	               .build();
	      System.out.println(member.getAddress()+"dd");
	      
	      memberRepo.save(newMem);

	      
	      return "redirect:/system/login";
	   }


