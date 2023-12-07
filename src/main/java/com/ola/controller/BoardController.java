package com.ola.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Community;
import com.ola.entity.TradeBoard;
import com.ola.repository.CommunityRepository;
import com.ola.repository.TradeBoardRepository;
import com.ola.security.SecurityUser;
import com.ola.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private TradeBoardRepository boardRepo;
	@Autowired
	private CommunityRepository comRepo;


	@RequestMapping("/tradeBoardList")
	public String TradeBoardList(Model model, Authentication authentication,
			@PageableDefault(size = 10, sort = "registrationDate", direction = Direction.DESC) Pageable pageable) {
		if (authentication == null || !authentication.isAuthenticated()) {
			// 사용자가 로그인하지 않았거나 인증되지 않았을 경우, 로그인 페이지로 리다이렉트
			return "redirect:/system/login";
		}
		List<TradeBoard> adminWrite = boardRepo.findByAdminWrite();
		model.addAttribute("adminWrite", adminWrite);

		Page<TradeBoard> memberWrite = boardRepo.findByMemberWrite(pageable);

		model.addAttribute("memberWrite", memberWrite);
		model.addAttribute("memberCurrentPage", memberWrite.getNumber() + 1);
		model.addAttribute("memberTotalPages", memberWrite.getTotalPages());

		return "board/tradeBoardList";
	}

	@GetMapping("/communityBoardList")
	public String CommunityBoardList(Model model, Authentication authentication,
			@PageableDefault(size = 10, sort = "regDate", direction = Direction.DESC) Pageable pageable) {
		if (authentication == null || !authentication.isAuthenticated()) {
			// 사용자가 로그인하지 않았거나 인증되지 않았을 경우, 로그인 페이지로 리다이렉트
			return "redirect:/system/login";
		}
		List<Community> adminWrite = comRepo.findByAdminWrite();
		model.addAttribute("adminWrite", adminWrite);

		Page<Community> memberWrite = comRepo.findByMemberWrite(pageable);

		model.addAttribute("memberWrite", memberWrite);
		model.addAttribute("memberCurrentPage", memberWrite.getNumber() + 1);
		model.addAttribute("memberTotalPages", memberWrite.getTotalPages());

		return "board/communityBoardList";
	}

	@GetMapping("/getTradeBoard")
	public String getTradeBoardView(@RequestParam Long tradeBoardNo, Model model) {
		TradeBoard tradeBoard = boardRepo.findById(tradeBoardNo).orElse(null);

		if (tradeBoard != null) {
			model.addAttribute("tradeBoard", tradeBoard);
			return "board/getTradeBoard";
		} else {
			return "errorPage";
		}

	}

	@GetMapping("/getCommuBoard")
	public String getCommunity(@RequestParam Long communityNo, Model model) {
		// 조회수 증가를 위해 서비스 계층의 메소드를 호출
		Community community = boardService.getCommunityByNo(communityNo);

		if (community != null) {
			model.addAttribute("community", community);
			return "board/getCommuBoard";
		} else {
			return "errorPage";
		}
	}

	@GetMapping("/board/communityInsert")
	public String communityInsertView() {
		return "board/communityInsert";
	}

	@GetMapping("/board/tradeInsert")
	public void tradeInsertView() {

	}

	/*
	 * @AuthenticationPrincipal: 인증된 정보를 가지고 있는 SecurityUser 객체가 저장됨
	 */

	@PostMapping("/communityInsert")
	public String communityInsertAction(@ModelAttribute Community board,
			@AuthenticationPrincipal SecurityUser principal) {

		board.setRegDate(new Date());

		board.setMember(principal.getMember());

		boardService.insertBoard(board);

		return "redirect:communityBoardList";
	}

	@PostMapping("/board/tradeInsert")
	public String tradeInsertAction(@ModelAttribute TradeBoard board, @AuthenticationPrincipal SecurityUser principal) {

		board.setRegistrationDate(new Date());

		board.setMember(principal.getMember());

		boardService.insertBoard(board);

		return "redirect:/tradeBoardList";
	}

	@PostMapping("/board/updateBoard")
	public String updateBoard(TradeBoard board) {
		boardService.updateBoard(board);

		return "redirect:getBoardList";
	}

	@GetMapping("/board/deleteBoard")
	public String deleteBoard(TradeBoard board) {
		boardService.deleteBoard(board);

		return "redirect:getBoardList";
	}
}
