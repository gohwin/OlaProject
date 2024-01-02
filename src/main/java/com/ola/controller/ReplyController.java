package com.ola.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Community;
import com.ola.entity.Member;
import com.ola.entity.Reply;
import com.ola.entity.TradeBoard;
import com.ola.repository.CommunityRepository;
import com.ola.repository.ReplyRepository;
import com.ola.repository.TradeBoardRepository;
import com.ola.security.SecurityUser;
import com.ola.service.reply.ReplyService;

@Controller
public class ReplyController {
	@Autowired
	private ReplyRepository replyRepo;

	@Autowired
	private CommunityRepository commuRepo;
	
	@Autowired
	private TradeBoardRepository tradeRepo;

	@Autowired
	private ReplyService replyService;


	@PostMapping("/addCommuReply")
	public String addCommuReply(@RequestParam("communityNo") Long communityNo, @RequestParam("replycontent") String content,
			@RequestParam(value = "isPrivate", required = false) String isPrivateStr,
            Authentication authentication) {
		SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
		Member currentMember = userDetails.getMember();

		Community community = commuRepo.findById(communityNo)
				.orElseThrow(() -> new IllegalArgumentException("Invalid community No:" + communityNo));

		Reply reply = new Reply();
		reply.setMember(currentMember);
		reply.setCommunity(community);
		reply.setContent(content);
		reply.setRegDate(new Date());
		boolean isPrivate = "on".equals(isPrivateStr);
	    reply.setPrivate(isPrivate); // 비밀댓글 설정 
		replyRepo.save(reply);

		community.setCommentCount(community.getCommentCount() + 1);
		commuRepo.save(community);

		return "redirect:/getCommuBoard?communityNo=" + communityNo;
	}

	@PostMapping("/deleteCommuReply")
	public String deleteCommuReply(@RequestParam("replyNo") Long replyNo, Principal principal) {

		// 현재 로그인한 사용자의 아이디를 가져옵니다.
		String loggedInUserId = principal.getName();

		Reply reply = replyService.getReplyByReplyNo(replyNo);

		// 댓글 작성자의 아이디와 현재 로그인한 사용자의 아이디를 비교하여 일치할 경우에만 삭제합니다.
		if (reply != null && reply.getMember().getMemberId().equals(loggedInUserId)) {
			Community community = reply.getCommunity();

			replyService.deleteReply(replyNo);

			if (community != null) {
				community.setCommentCount(community.getCommentCount() - 1);
				commuRepo.save(community);

				// 댓글이 속한 게시판으로 리다이렉트
				return "redirect:/getCommuBoard?communityNo=" + community.getCommunityNo();
			}
		}

		// 실패 시 전체 게시판 목록으로 리다이렉트
		return "redirect:/communityBoardList";
	}
	
	@PostMapping("/addTradeReply")
	public String addTradeReply(@RequestParam("tradeBoardNo") Long tradeBoardNo, @RequestParam("replycontent") String content,
			Authentication authentication) {
		SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
		Member currentMember = userDetails.getMember();
		
		TradeBoard tradeBoard = tradeRepo.findById(tradeBoardNo)
				.orElseThrow(() -> new IllegalArgumentException("Invalid tradeBoard No:" + tradeBoardNo));
		
		Reply reply = new Reply();
		reply.setMember(currentMember);
		reply.setTradeBoard(tradeBoard);
		reply.setCommunity(null);
		reply.setContent(content);
		reply.setRegDate(new Date());
		replyRepo.save(reply);
		
		tradeRepo.save(tradeBoard);
		
		return "redirect:/getTradeBoard?tradeBoardNo=" + tradeBoardNo;
	}
	
	@PostMapping("/deleteTradeReply")
	public String deleteTradeReply(@RequestParam("replyNo") Long replyNo, Principal principal) {
		// 현재 로그인한 사용자의 아이디를 가져옵니다.
		String loggedInUserId = principal.getName();
		
		Reply reply = replyService.getReplyByReplyNo(replyNo);
		
		// 댓글 작성자의 아이디와 현재 로그인한 사용자의 아이디를 비교하여 일치할 경우에만 삭제합니다.
		if (reply != null && reply.getMember().getMemberId().equals(loggedInUserId)) {
			TradeBoard tradeBoard = reply.getTradeBoard();
			
			replyService.deleteReply(replyNo);
			
			if (tradeBoard != null) {
				tradeRepo.save(tradeBoard);
				
				// 댓글이 속한 게시판으로 리다이렉트
				return "redirect:/getTradeBoard?tradeBoardNo=" + tradeBoard.getTradeBoardNo();
			}
		}
		
		// 실패 시 전체 게시판 목록으로 리다이렉트
		return "redirect:/tradeBoardList";
	}
	
	// 대댓글 추가
    @PostMapping("/addCommuSubReply")
    public String addCommuSubReply(@RequestParam("communityNo") Long communityNo, @RequestParam("parentReplyId") Long parentReplyNo,
                           @RequestParam("replyContent") String content,
                           Authentication authentication) {
        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
        Member currentMember = userDetails.getMember();

        Reply reply = new Reply();
        reply.setMember(currentMember);
        reply.setContent(content);
        reply.setRegDate(new Date());

        replyService.addReply(reply, parentReplyNo);

        // 대댓글이 추가된 부모 댓글이 속한 커뮤니티 또는 거래 게시판으로 리다이렉트
        // 이 부분은 시스템의 구조에 따라 달라질 수 있습니다.
        return "redirect:/getCommuBoard?communityNo=" + communityNo;
    }
    
    // 대댓글 추가
    @PostMapping("/addTradeSubReply")
    public String addTradeSubReply(@RequestParam("parentReplyNo") Long parentReplyNo,
    		@RequestParam("replyContent") String content,
    		@RequestParam("tradeBoardNo") Long tradeBoardNo,
    		Authentication authentication) {
    	SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
    	Member currentMember = userDetails.getMember();
    	
    	Reply reply = new Reply();
    	reply.setMember(currentMember);
    	reply.setContent(content);
    	reply.setRegDate(new Date());
    	
    	replyService.addReply(reply, parentReplyNo);
    	
    	// 대댓글이 추가된 부모 댓글이 속한 커뮤니티 또는 거래 게시판으로 리다이렉트
    	// 이 부분은 시스템의 구조에 따라 달라질 수 있습니다.
    	return "redirect:/getTradeBoard?tradeBoardNo=" + tradeBoardNo;
    }

    // 대댓글 삭제
    @PostMapping("/deleteCommuSubReply")
    public String deleteCommuSubReply(@RequestParam("replyNo") Long replyNo, Principal principal) {
        // 현재 로그인한 사용자의 아이디를 가져옵니다.
        String loggedInUserId = principal.getName();

        Reply reply = replyService.getReplyByReplyNo(replyNo);

        // 댓글 작성자의 아이디와 현재 로그인한 사용자의 아이디를 비교하여 일치할 경우에만 삭제합니다.
        if (reply != null && reply.getMember().getMemberId().equals(loggedInUserId)) {
            replyService.deleteReply(replyNo);

            // 댓글이 속한 게시판으로 리다이렉트
            return "redirect:/getCommuBoard";
        }

        // 실패 시 전체 게시판 목록으로 리다이렉트
        return "redirect:/communityBoardList";
    }
    
    // 대댓글 삭제
    @PostMapping("/deleteTradeSubReply")
    public String deleteTradeSubReply(@RequestParam("replyNo") Long replyNo, Principal principal) {
    	// 현재 로그인한 사용자의 아이디를 가져옵니다.
    	String loggedInUserId = principal.getName();
    	
    	Reply reply = replyService.getReplyByReplyNo(replyNo);
    	
    	// 댓글 작성자의 아이디와 현재 로그인한 사용자의 아이디를 비교하여 일치할 경우에만 삭제합니다.
    	if (reply != null && reply.getMember().getMemberId().equals(loggedInUserId)) {
    		replyService.deleteReply(replyNo);
    		
    		// 댓글이 속한 게시판으로 리다이렉트
    		return "redirect:/getTradeBoard";
    	}
    	
    	// 실패 시 전체 게시판 목록으로 리다이렉트
    	return "redirect:/tradeBoardList";
    }

}
