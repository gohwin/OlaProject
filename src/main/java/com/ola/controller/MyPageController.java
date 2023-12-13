package com.ola.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Basket;
import com.ola.entity.Member;
import com.ola.repository.BasketRepository;
import com.ola.repository.OrderListRepository;
import com.ola.security.SecurityUser;
import com.ola.service.UserService;

@Controller
@RequestMapping("mypage")
public class MyPageController {

	@Autowired
	private UserService userService;
	@Autowired
	private OrderListRepository orderRepo;
	@Autowired
	private BasketRepository basketRepo;

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
		currentUser.setZipNum(updatedUser.getZipNum());

		userService.updateUser(currentUser);

		return "redirect:/mypage/mypages";
	}

	@GetMapping("/orderHistory")
	public String orderHistory(Model model, @AuthenticationPrincipal SecurityUser principal) {
		Member user = principal.getMember();
		model.addAttribute("myOrders", orderRepo.findByMember(user));
		return "mypage/orderHistory"; 
	}
	
	@GetMapping("/basket")
	public String basketView(Model model, @AuthenticationPrincipal SecurityUser principal) {
	    // 현재 로그인한 사용자의 Member 객체를 가져옵니다.
	    Member member = principal.getMember();

	    // 사용자와 연관된 단일 Basket 객체를 조회합니다.
	    // findByMember 메소드는 Member 객체를 받아 해당 Member와 연관된 Basket 객체를 반환합니다.
	    // 실제 구현에 따라 메소드 이름과 로직이 다를 수 있습니다.
	    Basket basket = basketRepo.findByUser(member);

	    // 조회된 Basket 객체를 모델에 추가합니다.
	    model.addAttribute("member", member);
	    model.addAttribute("basket", basket);

	    // 장바구니 페이지의 Thymeleaf 템플릿 파일명을 반환합니다.
	    return "mypage/basket";
	}
	
	@PostMapping("/deleteBasket")
    public ResponseEntity<?> removeProductFromBasket(@RequestParam Long productNo, @AuthenticationPrincipal SecurityUser principal) {
        // 현재 로그인한 사용자 정보를 가져옵니다.
        Member member = principal.getMember();

        // 사용자의 장바구니를 조회합니다.
        Basket basket = basketRepo.findByUser(member);

        // 장바구니에서 해당 상품을 찾아 삭제합니다.
        // 이 부분은 당신의 도메인 모델에 따라 다를 수 있습니다.
        basket.removeProduct(productNo);

        // 변경된 장바구니를 저장합니다.
        basketRepo.save(basket);

        // 클라이언트에 성공 메시지를 보냅니다.
        return ResponseEntity.ok("상품이 성공적으로 삭제되었습니다.");
    }









}
