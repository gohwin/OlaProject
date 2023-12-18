package com.ola.adminController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Member;
import com.ola.repository.OrderListRepository;
import com.ola.service.MemberService;

@Controller
public class adminMemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrderListRepository orderRepo;

	@GetMapping("/admin/member")
	public String getAllMembers(Model model) {
		// 모든 맴버를 조회
		model.addAttribute("members", memberService.getAllMembers());

		// Thymeleaf 템플릿 이름 반환
		return "admin/member_list";
	}

	@GetMapping("/admin/member_details/{memberId}")
	public String getMemberDetails(@PathVariable String memberId, Model model) {
		// memberId를 사용하여 회원의 상세 정보를 가져오는 서비스 호출
		Optional<Member> memberOptional = memberService.findById(memberId);

		if (memberOptional.isPresent()) {
			Member member = memberOptional.get();

			// 모델에 회원 정보 추가
			model.addAttribute("member", member);
			model.addAttribute("myOrders", orderRepo.findByMember(member));
			// Thymeleaf 템플릿 이름 반환
			return "admin/member_details";
		} else {
			// 회원이 존재하지 않을 경우에 대한 처리
			return "redirect:/admin/member_list"; // 또는 에러 페이지로 리다이렉트 등의 처리
		}
	}

	@PostMapping("/removeMember")
	public String deleteMember(Model model, @RequestParam("memberId") String memberId) {
		memberService.deleteMemberById(memberId);
		model.addAttribute("members", memberService.getAllMembers());
		return "admin/member_list";
	}
}
