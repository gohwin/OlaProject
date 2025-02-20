package com.ola.service.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ola.entity.Community;
import com.ola.entity.TradeBoard;

public interface CommunityBoardService {

	void insertBoard(Community commu);

	void saveCommunity(Community community);

	void deleteCommunity(Long communityNo);

	Community getCommunityById(Long communityNo);

	void insertTradeBoard(TradeBoard board);

	void updateBoard(TradeBoard board);

	void deleteBoard(Long tradeBoardNo);

	Community getCommunityWithRepliesByNo(Long communityNo);

	TradeBoard getTradeBoardById(Long tradeBoardNo);

	Page<TradeBoard> tradeBoardList(Pageable pageable);

	Page<Community> communityBoardList(Pageable pageable);

	boolean toggleLike(Long communityNo, String memberId);

	Page<Community> getBoardByTitle(String search, Pageable pageable);

	Page<Community> getBoardByAuthor(String search, Pageable pageable);

	Page<TradeBoard> getTradeBoardByAuthor(String search, Pageable pageable);

	Page<TradeBoard> getTradeBoardByTitle(String search, Pageable pageable);
}