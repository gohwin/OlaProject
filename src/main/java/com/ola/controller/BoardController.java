package com.ola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ola.board.service.BoardService;
import com.ola.entity.Community;
import com.ola.entity.TradeBoard;
import com.ola.repository.TradeBoardRepository;
import com.ola.security.SecurityUser;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private TradeBoardRepository boardRepo;

	@RequestMapping("/tradeBoardList")
	public String TradeBoardList(Model model, Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			// 사용자가 로그인하지 않았거나 인증되지 않았을 경우, 로그인 페이지로 리다이렉트
			return "redirect:/system/login";
		}

		Pageable pageable = PageRequest.of(0, 26, Sort.by("tradeBoardNo").descending());
		Page<TradeBoard> boardList = boardService.tradeBoardList(pageable);

		model.addAttribute("boardList", boardList);

		return "board/tradeBoardList";
	}

	@RequestMapping("/communityBoardList")
	public String CommunityBoardList(Model model, Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			// 사용자가 로그인하지 않았거나 인증되지 않았을 경우, 로그인 페이지로 리다이렉트
			return "redirect:/system/login";
		}
		Pageable pageable = PageRequest.of(0, 26, Sort.by("communityNo").descending());
		Page<Community> boardList = boardService.communityBoardList(pageable);

		model.addAttribute("boardList", boardList);

		return "board/communityBoardList";
	}

	@GetMapping("/getBoard")
	public void getBoard(TradeBoard board, Model model) {

		model.addAttribute("board", boardService.getBoard(board));
	}

	@GetMapping("/insertBoard")
	public void insertBoardView() {

	}

	/*
	 * @AuthenticationPrincipal: 인증된 정보를 가지고 있는 SecurityUser 객체가 저장됨
	 */
	@PostMapping("/insertBoard")
	public String insertBoardAction(TradeBoard board, @AuthenticationPrincipal SecurityUser principal) {
		board.setMember(principal.getMember());
		boardService.insertBoard(board);

		return "redirect:getBoardList";
	}

	@PostMapping("/updateBoard")
	public String updateBoard(TradeBoard board) {
		boardService.updateBoard(board);

		return "redirect:getBoardList";
	}

	@GetMapping("/deleteBoard")
	public String deleteBoard(TradeBoard board) {
		boardService.deleteBoard(board);

		return "redirect:getBoardList";
	}
}
