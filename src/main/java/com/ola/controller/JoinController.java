package com.ola.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Member;
import com.ola.entity.Role;
import com.ola.repository.MemberRepository;
import com.ola.service.VerificationCodeService;
import com.ola.service.user.EmailService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class JoinController {
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private EmailService emailService; // EmailService를 Autowired 합니다.

	@Autowired
	private VerificationCodeService verificationCodeService; // VerificationCodeService를 Autowired 합니다.

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

	// 회원가입 처리
	@PostMapping(value = "/register", consumes = "application/x-www-form-urlencoded")
	public String registerUser(@ModelAttribute Member member,
            @RequestParam(value = "emailId") String emailId,
            @RequestParam(value = "emailDomain") String emailDomain,
            @RequestParam(value = "memberId") String memberId,
            Model model, HttpServletRequest request) {
		
		// 이메일 주소를 조합
	    String fullEmail = emailId + "@" + emailDomain;

	    if (memberRepo.existsById(memberId)) {
	        model.addAttribute("idExistsError", "이미 사용 중인 아이디입니다.");
	        return "join/joinForm";
	    }

	    member.setMemberId(memberId);
	    member.setEmail(fullEmail);
	    member.setRole(Role.ROLE_MEMBER);
	    member.setPassword(encoder.encode(member.getPassword()));

	    memberRepo.save(member);

	    // 나중에 사용할 수 있도록 인증 코드 저장
	    String verificationCode = verificationCodeService.generateVerificationCode();
	    verificationCodeService.saveVerificationCode(fullEmail, verificationCode);

	    // 인증 이메일 전송
	    emailService.sendVerificationEmail(fullEmail, verificationCode);

	    return "redirect:/system/login";
	}

}
