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

		return boardRepo.findByMemberWrite(pageable);
	}

	@Override
	public Page<Community> communityBoardList(Pageable pageable) {

		return comRepo.findByMemberWrite(pageable);
	}

	@Transactional
	public boolean toggleLike(Long communityNo, String memberId) {
		Optional<Community> communityOpt = comRepo.findById(communityNo);
		Optional<Member> memberOpt = memberRepo.findById(memberId);

		if (communityOpt.isPresent() && memberOpt.isPresent()) {
			Community community = communityOpt.get();
			Member member = memberOpt.get();

			boolean isLiked = community.getLikedByMembers().contains(member);
			if (isLiked) {
				// 이미 좋아요 상태이면 좋아요 취소
				community.getLikedByMembers().remove(member);
				community.setLikeCount(community.getLikeCount() - 1);
			} else {
				// 좋아요 상태가 아니면 좋아요 추가
				community.getLikedByMembers().add(member);
				community.setLikeCount(community.getLikeCount() + 1);
			}

			comRepo.save(community);
			return !isLiked;
		}

		return false;
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
	public Page<Community> getBoardByTitle(String search, Pageable pageable) {
		return comRepo.findByTitleContaining(search, pageable);
	}

	@Override
	public Page<Community> getBoardByAuthor(String search, Pageable pageable) {
		return comRepo.findByMemberNameContaining(search, pageable);
	}

	@Override
	public Page<TradeBoard> getTradeBoardByAuthor(String search, Pageable pageable) {
		return boardRepo.findByMemberNameContaining(search, pageable);
	}

	@Override
	public Page<TradeBoard> getTradeBoardByTitle(String search, Pageable pageable) {
		return boardRepo.findByTitleContaining(search, pageable);
	}
}
