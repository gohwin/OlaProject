package com.ola.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ola.DTO.ReplyDTO;
import com.ola.entity.Member;
import com.ola.security.SecurityUser;
import com.ola.service.ReplyService;

@Controller
public class ReplyController {
	@Autowired
	private ReplyService replyService;

	@PostMapping("/addReply")
	public String addReply(@ModelAttribute ReplyDTO replyDTO, Authentication authentication) {
		SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
		Member currentMember = userDetails.getMember();

		// 댓글 등록을 위한 필요한 정보 설정
		replyDTO.setMemberName(currentMember.getName());
		replyDTO.setRegDate(new Date());

		replyService.addReply(replyDTO);

		// 댓글 등록 후, 해당 게시글로 리다이렉트
		return "redirect:/getCommuBoard?communityNo=" + replyDTO.getCommunityNo();
	}

}
