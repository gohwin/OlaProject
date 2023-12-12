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
import com.ola.repository.CommunityRepository;
import com.ola.repository.ReplyRepository;
import com.ola.security.SecurityUser;
import com.ola.service.ReplyService;

@Controller
public class ReplyController {
	@Autowired
	private ReplyRepository replyRepo;

	@Autowired
	private CommunityRepository commuRepo;

	@Autowired
	private ReplyService replyService;

	@PostMapping("/addReply")
	public String addReply(@RequestParam("communityNo") Long communityNo, @RequestParam("replycontent") String content,
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
		replyRepo.save(reply);

		community.setCommentCount(community.getCommentCount() + 1);
		commuRepo.save(community);

		return "redirect:/getCommuBoard?communityNo=" + communityNo;
	}

	@PostMapping("/deleteReply")
	public String deleteReply(@RequestParam("replyNo") Long replyNo, Principal principal) {
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

}
