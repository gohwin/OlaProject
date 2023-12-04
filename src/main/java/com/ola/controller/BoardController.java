package com.ola.controller;

import java.util.Date;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private TradeBoardRepository boardRepo;
	@Autowired
    private CommunityRepository comRepo;
	

	@RequestMapping("/tradeBoardList")
	public String TradeBoardList(Model model, Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			// 사용자가 로그인하지 않았거나 인증되지 않았을 경우, 로그인 페이지로 리다이렉트
			return "redirect:/system/login";
		}

		Pageable pageable = PageRequest.of(0, 26, Sort.by("tradeBoardNo").descending());
		Page<TradeBoard> boardList = boardService.tradeBoardList(pageable);

		model.addAttribute("tradeBoardList", boardList);

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

		model.addAttribute("communities", boardList);
		System.out.println(boardList);
		return "board/communityBoardList";
	}

	@GetMapping("/getTradeBoard")
	public String getTradeBoard(@RequestParam Long tradeBoardNo, Model model) {
		TradeBoard tradeBoard = boardRepo.findById(tradeBoardNo).orElse(null);
		
		if (tradeBoard != null) {
			model.addAttribute("tradeBoard",tradeBoard);
			return "board/getTradeBoard";
		} else {
			return "errorPage";
		}

	}

	@GetMapping("/getBoard")
	public String getCommunity(@RequestParam Long communityNo, Model model) {
		Community community = comRepo.findById(communityNo).orElse(null);
		
		if (community != null) {
            model.addAttribute("community", community);
            return "board/getBoard"; 
        } else {
            return "errorPage"; 
        }
	}

	@GetMapping("/communityInsert")
	public void communityInsertView() {

	}

	@GetMapping("/tradeInsert")
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

	@PostMapping("/tradeInsert")
	public String tradeInsertAction(@ModelAttribute TradeBoard board, @AuthenticationPrincipal SecurityUser principal) {

		board.setRegistrationDate(new Date());

		board.setMember(principal.getMember());

		boardService.insertBoard(board);

		return "redirect:/board/tradeBoardList";
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
