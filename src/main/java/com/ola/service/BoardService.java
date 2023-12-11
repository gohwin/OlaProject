package com.ola.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ola.entity.Community;
import com.ola.entity.TradeBoard;

public interface BoardService {

	void insertBoard(Community commu);

	void saveCommunity(Community community);

	void deleteCommunity(Long communityNo);

	Community getCommunityById(Long communityNo);

	void insertBoard(TradeBoard board);

	void updateBoard(TradeBoard board);

	void deleteBoard(Long tradeBoardNo);

	Community getCommunityWithRepliesByNo(Long communityNo);

	TradeBoard getTradeBoardById(Long tradeBoardNo);

	Page<TradeBoard> tradeBoardList(Pageable pageable);

	Page<Community> communityBoardList(Pageable pageable);

	void likeCommunity(Long communityNo, String memberId);

	void unlikeCommunity(Long communityNo, String memberId);

	Page<Community> getCommunityBySearch(String search, Pageable pageable);

	Page<TradeBoard> getTradeBoardBySearch(String search, Pageable pageable);
}