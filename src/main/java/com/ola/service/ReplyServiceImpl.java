package com.ola.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ola.DTO.ReplyDTO;
import com.ola.entity.Community;
import com.ola.entity.Member;
import com.ola.entity.Reply;
import com.ola.repository.CommunityRepository;
import com.ola.repository.MemberRepository;
import com.ola.repository.ReplyRepository;
import com.ola.security.SecurityUser;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	@Autowired
	private CommunityRepository communityRepository;
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public void addReply(ReplyDTO replyDTO) {
		// 현재 사용자의 Authentication 객체 얻기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 사용자 정보 얻기
		if (authentication != null && authentication.isAuthenticated()) {
			// 사용자의 ID
			String memberId = authentication.getName();

			// 사용자의 이름
			String memberName = ((SecurityUser) authentication.getPrincipal()).getMember().getName();

			// ReplyDTO에서 가져온 정보
			Long communityNo = replyDTO.getCommunityNo();
			String content = replyDTO.getContent();
			Date regDate = replyDTO.getRegDate();

			// 필요한 정보를 Reply 엔터티에 설정
			Reply reply = new Reply();
			Community community = communityRepository.findById(communityNo).orElse(null);
			Member member = memberRepository.findByName(memberName);
			reply.setCommunity(community);
			reply.setMember(member);
			reply.setContent(content);
			reply.setRegDate(regDate);

			replyRepository.save(reply);
			updateCommentCount(communityNo);
		}
	}

	private void updateCommentCount(Long communityNo) {
	    Community community = communityRepository.findById(communityNo).orElse(null);
	    if (community != null) {
	        int newCommentCount = community.getReplies().size();
	        community.setCommentCount(newCommentCount);
	        communityRepository.save(community);
	    }
	}
}