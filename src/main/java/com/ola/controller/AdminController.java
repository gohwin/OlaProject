package com.ola.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Community;
import com.ola.entity.Member;
import com.ola.entity.TradeBoard;
import com.ola.repository.CommunityRepository;
import com.ola.repository.TradeBoardRepository;
import com.ola.security.SecurityUser;
import com.ola.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

	@Autowired
	private CommunityRepository communityRepo;

	@Autowired
	private TradeBoardRepository tradeRepo;

	@Autowired
	private BoardService boardService;

	@GetMapping("/adminMain")
	public String adminMain() {
		return "adminMain"; // 이 부분은 실제 리턴하는 뷰의 이름입니다.
	}

	@GetMapping("/adminCommunityBoardList")
	public String adminCommuView(Model model, @PageableDefault(size = 10, sort = "regDate", direction = Direction.DESC) Pageable pageable) {
		// admin이 작성한 게시글 가져오기
		List<Community> adminWrite = communityRepo.findByAdminWrite();
		model.addAttribute("adminWrite", adminWrite);

		Page<Community> memberWrite = communityRepo.findByMemberWrite(pageable);
		
		model.addAttribute("memberWrite", memberWrite);
		model.addAttribute("memberCurrentPage", memberWrite.getNumber() + 1);
		model.addAttribute("memberTotalPages", memberWrite.getTotalPages());

		return "admin/adminCommunityBoardList"; // View 반환
	}
	@GetMapping("/adminTradeBoardList")
	public String adminTradeView(Model model, @PageableDefault(size = 10, sort = "registrationDate", direction = Direction.DESC) Pageable pageable) {
		// admin이 작성한 게시글 가져오기
		List<TradeBoard> adminWrite = tradeRepo.findByAdminWrite();
		model.addAttribute("adminWrite", adminWrite);
		
		Page<TradeBoard> memberWrite = tradeRepo.findByMemberWrite(pageable);
		
		model.addAttribute("memberWrite", memberWrite);
		model.addAttribute("memberCurrentPage", memberWrite.getNumber() + 1);
		model.addAttribute("memberTotalPages", memberWrite.getTotalPages());
		
		return "admin/adminTradeBoardList"; // View 반환
	}


	@GetMapping("/adminGetCommuBoard")
	public String getCommuBoard(@RequestParam Long communityNo, Model model) {
		Community community = communityRepo.findById(communityNo).orElse(null); // communityNo에 해당하는 Community 객체를 조회

		if (community != null) {
			model.addAttribute("community", community);
			return "admin/adminCommuBoard"; // 게시글 상세보기 페이지의 뷰 이름
		} else {
			return "errorPage"; // 에러 페이지의 뷰 이름.
		}
	}
	
	@GetMapping("/adminGetTradeBoard")
	public String getTradeBoard(@RequestParam Long tradeBoardNo, Model model) {
		TradeBoard tradeBoard = tradeRepo.findById(tradeBoardNo).orElse(null); // communityNo에 해당하는 Community 객체를 조회
		
		if (tradeBoard != null) {
			model.addAttribute("tradeBoard", tradeBoard);
			return "admin/adminTradeBoard"; // 게시글 상세보기 페이지의 뷰 이름
		} else {
			return "errorPage"; // 에러 페이지의 뷰 이름.
		}
	}

	@GetMapping("/deleteAdminCommuBoard")
	public String deleteAdminCommuBoard(@RequestParam Long communityNo) {
		communityRepo.deleteById(communityNo); // 게시글 삭제

		return "redirect:adminCommunityBoardList"; // 삭제 후 목록 페이지로 리디렉션
	}
	@GetMapping("/deleteAdminTradeBoard")
	public String deleteAdminTradeBoard(@RequestParam Long tradeBoardNo) {
		tradeRepo.deleteById(tradeBoardNo); // 게시글 삭제
		
		return "redirect:adminTradeBoardList"; // 삭제 후 목록 페이지로 리디렉션
	}

	@GetMapping("/adminRegisterCommu")
	public String registerCommuView() {
		return "admin/adminRegisterCommu";
	}

	@PostMapping("/adminRegisterCommu")
	public String registerCommuAction(Community board, @AuthenticationPrincipal SecurityUser principal) {
		board.setMember(principal.getMember());
		board.setRegDate(new Date());
		boardService.insertBoard(board);
		
		return "redirect:adminCommunityBoardList";
	}
	
	@GetMapping("/adminRegisterTrade")
	public String registerTradeView() {
		return "admin/adminRegisterTrade";
	}
	
	@PostMapping("/adminRegisterTrade")
	public String registerTradeAction(TradeBoard board, @AuthenticationPrincipal SecurityUser principal) {
		board.setMember(principal.getMember());
		board.setRegistrationDate(new Date());
		boardService.insertBoard(board);
		
		return "redirect:adminCommunityBoardList";
	}
}
