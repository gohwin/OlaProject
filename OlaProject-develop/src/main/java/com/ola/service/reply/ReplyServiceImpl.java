package com.ola.service.reply;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ola.entity.Community;
import com.ola.entity.Member;
import com.ola.entity.Reply;
import com.ola.entity.TradeBoard;
import com.ola.repository.CommunityRepository;
import com.ola.repository.MemberRepository;
import com.ola.repository.ReplyRepository;
import com.ola.repository.TradeBoardRepository;
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
	@Autowired
	private TradeBoardRepository tradeBoardRepository;

	@Override
	public Reply getReplyByReplyNo(Long replyNo) {
		return replyRepository.findById(replyNo).orElse(null);
	}

	@Transactional
	@Override
	public void addCommuReply(Reply reply) {
		// 현재 사용자의 Authentication 객체 얻기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 사용자 정보 얻기
		if (authentication != null && authentication.isAuthenticated()) {

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
	public void addTradeReply(Reply reply) {
		// 현재 사용자의 Authentication 객체 얻기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 사용자 정보 얻기
		if (authentication != null && authentication.isAuthenticated()) {

			// 사용자의 이름
			String memberName = ((SecurityUser) authentication.getPrincipal()).getMember().getName();

			// reply에서 가져온 정보
			Long tradeBoardNo = reply.getTradeBoard().getTradeBoardNo();
			String content = reply.getContent();
			Date regDate = reply.getRegDate();

			// 필요한 정보를 Reply 엔터티에 설정
			Reply reply1 = new Reply();
			TradeBoard tradeBoard = tradeBoardRepository.findById(tradeBoardNo).orElse(null);
			Member member = memberRepository.findByName(memberName);
			reply1.setTradeBoard(tradeBoard);
			reply1.setMember(member);
			reply1.setContent(content);
			reply1.setRegDate(regDate);

			replyRepository.save(reply1);
		}
	}

	@Transactional
	@Override
	public void addReply(Reply reply, Long parentReplyNo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String memberId = authentication.getName();
			Member member = memberRepository.findById(memberId).orElse(null);
			Reply parentReply = replyRepository.findById(parentReplyNo).orElse(null);

			Reply newReply = new Reply();
			newReply.setMember(member);
			newReply.setContent(reply.getContent());
			newReply.setRegDate(new Date()); // 현재 날짜와 시간
			newReply.setParent(parentReply); // 대댓글의 부모 설정

			if (parentReply != null) {
				if (parentReply.getCommunity() != null) {
					newReply.setCommunity(parentReply.getCommunity());
				} else if (parentReply.getTradeBoard() != null) {
					newReply.setTradeBoard(parentReply.getTradeBoard());
				}
			}

			replyRepository.save(newReply);
		}
	}

	@Override
	public void deleteReply(Long replyId) {
		replyRepository.deleteById(replyId);
	}

	@Override
	public Community getCommunityByReplyNo(Long replyNo) {
		Reply reply = replyRepository.findById(replyNo).orElse(null);
		return (reply != null) ? reply.getCommunity() : null;
	}

	@Override
	public List<Reply> getRepliesByCommunity(Community community) {
		return replyRepository.findByCommunityOrderByRegDateAsc(community);
	}

}