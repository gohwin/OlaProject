package com.ola.controller;

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

@Controller
public class ReplyController {
	@Autowired
	private ReplyRepository replyRepo;
	
	@Autowired
	private CommunityRepository commuRepo;

	@PostMapping("/addReply")
    public String addReply(@RequestParam("communityNo") Long communityNo,
                           @RequestParam("replycontent") String content,
                           Authentication authentication) {
        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
        Member currentMember = userDetails.getMember();

        Community community = commuRepo.findById(communityNo)
                              .orElseThrow(() -> new IllegalArgumentException("Invalid community No:" + communityNo));

        Reply reply = new Reply();
        reply.setMember(currentMember);
        reply.setCommunity(community);
        reply.setContent(content);
        reply.setRegDate(new Date()); // Assuming you have a regDate field for the registration date

        replyRepo.save(reply);

        return "redirect:/getCommuBoard?communityNo=" + communityNo;
    }

}
