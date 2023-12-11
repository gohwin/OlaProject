package com.ola.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ola.entity.Community;
import com.ola.entity.Member;
import com.ola.entity.Reply;
import com.ola.entity.TradeBoard;
import com.ola.repository.CommunityRepository;
import com.ola.repository.MemberRepository;
import com.ola.repository.TradeBoardRepository;

import jakarta.transaction.Transactional;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private TradeBoardRepository boardRepo;
	@Autowired
	private CommunityRepository comRepo;

	@Autowired
	private MemberRepository memberRepo;

	@Override
	@Transactional
	public void insertTradeBoard(TradeBoard board) {

		boardRepo.save(board);
	}

	@Override
	public void insertBoard(Community commu) {
		comRepo.save(commu);
	}

	@Override
	public void updateBoard(TradeBoard board) {
		TradeBoard newBoard = boardRepo.findById(board.getTradeBoardNo()).get();

		newBoard.setTitle(board.getTitle());
		newBoard.setContent(board.getContent());
		newBoard.setTradeType(board.getTradeType());
		newBoard.setRegistrationDate(new Date());

		boardRepo.save(newBoard);
	}

	@Override
	public void deleteBoard(Long tradeBoardNo) {
		boardRepo.deleteById(tradeBoardNo);
	}

	@Override
	public TradeBoard getTradeBoardById(Long tradeBoardNo) {
		return boardRepo.findById(tradeBoardNo).orElse(null);
	}

	@Transactional
	@Override
	public Community getCommunityWithRepliesByNo(Long communityNo) {
		Optional<Community> optionalCommunity = comRepo.findById(communityNo);

		if (optionalCommunity.isPresent()) {
			Community existingCommunity = optionalCommunity.get();

			int newViewCount = existingCommunity.getViewCount() + 1;
			existingCommunity.setViewCount(newViewCount);

			// 댓글 정보를 함께 로드 (지연 로딩)
			List<Reply> sortedReplies = existingCommunity.getReplies().stream()
					.sorted(Comparator.comparing(Reply::getRegDate).reversed()) // 댓글을 regDate 기준으로 내림차순 정렬
					.collect(Collectors.toList());
			existingCommunity.setReplies(sortedReplies);

			comRepo.save(existingCommunity);

			return existingCommunity;
		} else {
			return null;
		}
	}

	@Override
	public Page<TradeBoard> tradeBoardList(Pageable pageable) {

		return boardRepo.findAll(pageable);
	}

	@Override
	public Page<Community> communityBoardList(Pageable pageable) {

		return comRepo.findAll(pageable);
	}

	public void likeCommunity(Long communityNo, String memberId) {
		Community community = comRepo.findById(communityNo).orElse(null);
		Member member = memberRepo.findById(memberId).orElse(null);

		if (community != null && member != null) {
			// Check if the member has already liked the community
			if (!community.getLikedByMembers().contains(member)) {
				int currentLikeCount = community.getLikeCount();
				community.setLikeCount(currentLikeCount + 1);
				community.getLikedByMembers().add(member);
				comRepo.save(community);
			}
		}
	}

	public void unlikeCommunity(Long communityNo, String memberId) {
		Community community = comRepo.findById(communityNo).orElse(null);
		Member member = memberRepo.findById(memberId).orElse(null);

		if (community != null && member != null) {
			// Check if the member has liked the community
			if (community.getLikedByMembers().contains(member)) {
				int currentLikeCount = community.getLikeCount();
				community.setLikeCount(currentLikeCount - 1);
				community.getLikedByMembers().remove(member);
				comRepo.save(community);
			}
		}
	}

	// 게시글 삭제 서비스 메소드
	public void deleteCommunity(Long communityNo) {
		comRepo.deleteById(communityNo);
	}

	@Override
	public Community getCommunityById(Long communityNo) {
		return comRepo.findById(communityNo).orElse(null);

	}

	@Transactional
	@Override
	public void saveCommunity(Community community) {
		comRepo.save(community);
	}

	@Override
	public Page<Community> getBoardByTitleOrAuthor(String search, Pageable pageable) {
		return comRepo.findByTitleOrMemberNameContaining(search, search, pageable);
	}

	@Override
	public Page<TradeBoard> getTradeBoardByTitleOrAuthor(String search, Pageable pageable) {
		return boardRepo.findByTitleContainingOrMemberNameContaining(search, search, pageable);
	}
}
