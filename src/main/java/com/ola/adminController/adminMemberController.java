package com.ola.adminController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Member;
import com.ola.entity.OrderList;
import com.ola.entity.Product;
import com.ola.repository.BasketRepository;
import com.ola.repository.CommunityRepository;
import com.ola.repository.MemberRepository;
import com.ola.repository.OrderListRepository;
import com.ola.repository.ProductRepository;
import com.ola.repository.ReplyRepository;
import com.ola.repository.TradeBoardRepository;
import com.ola.service.member.MemberService;

@Controller
public class adminMemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrderListRepository orderRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private CommunityRepository commuRepo;
	@Autowired
	private TradeBoardRepository tradeRepo;
	@Autowired
	private ReplyRepository replyRepo;
	@Autowired
	private BasketRepository basketRepo;

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
			List<OrderList> myOrders = orderRepo.findByMember(member);
			Map<Long, Product> productsMap = new HashMap<>();

	        for (OrderList order : myOrders) {
	            for (Long productId : order.getProductQuantities().keySet()) {
	                if (!productsMap.containsKey(productId)) {
	                    productsMap.put(productId, productRepo.findById(productId).orElse(null));
	                }
	            }
	        }

			// 모델에 회원 정보 추가
			model.addAttribute("member", member);
			model.addAttribute("myOrders", orderRepo.findByMember(member));
			model.addAttribute("productsMap", productsMap);
			// Thymeleaf 템플릿 이름 반환
			return "admin/member_details";
		} else {
			// 회원이 존재하지 않을 경우에 대한 처리
			return "redirect:/admin/member_list"; // 또는 에러 페이지로 리다이렉트 등의 처리
		}
	}

	@PostMapping("/removeMember")
	public String deleteMember(Model model, @RequestParam("memberId") String memberId) {
			commuRepo.deleteByMember(memberId);
			basketRepo.deleteByMember(memberId);
			orderRepo.deleteByMember(memberId);
			tradeRepo.deleteByMember(memberId);
			replyRepo.deleteByMember(memberId);
			memberService.deleteMemberById(memberId);
		
		model.addAttribute("members", memberService.getAllMembers());
		return "admin/member_list";
	}
}
