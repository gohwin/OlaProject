package com.ola.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ola.entity.Community;
import com.ola.entity.Member;
import com.ola.entity.Reply;
import com.ola.repository.CommunityRepository;
import com.ola.repository.MemberRepository;
import com.ola.repository.ReplyRepository;
import com.ola.security.SecurityUser;

import jakarta.transaction.Transactional;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	@Autowired
	private CommunityRepository communityRepository;
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public Reply getReplyByReplyNo(Long replyNo) {
		return replyRepository.findById(replyNo).orElse(null);
	}

	@Transactional
	@Override
	public void addReply(Reply reply) {
		// 현재 사용자의 Authentication 객체 얻기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 사용자 정보 얻기
		if (authentication != null && authentication.isAuthenticated()) {
			// 사용자의 ID
			String memberId = authentication.getName();

			// 사용자의 이름
			String memberName = ((SecurityUser) authentication.getPrincipal()).getMember().getName();

			// reply에서 가져온 정보
			Long communityNo = reply.getCommunity().getCommunityNo();
			String content = reply.getContent();
			Date regDate = reply.getRegDate();

			// 필요한 정보를 Reply 엔터티에 설정
			Reply reply1 = new Reply();
			Community community = communityRepository.findById(communityNo).orElse(null);
			Member member = memberRepository.findByName(memberName);
			reply1.setCommunity(community);
			reply1.setMember(member);
			reply1.setContent(content);
			reply1.setRegDate(regDate);

			replyRepository.save(reply1);
		}
	}

	@Transactional
	@Override
	public void deleteReply(Long replyNo) {
		Reply reply = replyRepository.findById(replyNo).orElse(null);
		if (reply != null) {
			replyRepository.deleteById(replyNo);
		}
	}

	@Override
	public Community getCommunityByReplyNo(Long replyNo) {
		Reply reply = replyRepository.findById(replyNo).orElse(null);
		return (reply != null) ? reply.getCommunity() : null;
	}

}