package com.ola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ola.entity.Community;
import com.ola.entity.Reply;
import com.ola.entity.TradeBoard;


@Transactional
public interface ReplyRepository extends JpaRepository<Reply, Long> {
	List<Reply> findByCommunity_CommunityNo(Long communityNo);
	
	/* 관리자페이지 게시글 상세보기에서 댓글 출력 하기*/
	List<Reply> findByCommunityOrderByRegDateAsc(Community community);
	
	@Modifying
	@Query("DELETE FROM Reply r WHERE r.member.memberId LIKE %:memberId%")
	void deleteByMember(@Param("memberId") String memberId);
	
	@Query("SELECT r FROM Reply r WHERE r.parent.replyNo = :parentReplyNo ORDER BY r.regDate ASC")
    List<Reply> findByParentReplyNo(@Param("parentReplyNo") Long parentReplyNo);

	@Query("SELECT r FROM Reply r WHERE r.community = :community AND r.parent IS NULL ORDER BY r.regDate DESC")
	List<Reply> findByCommunityAndParentIsNull(@Param("community") Community community);

    @Query("SELECT r FROM Reply r WHERE r.tradeBoard = :tradeBoard AND r.parent IS NULL ORDER BY r.regDate DESC")
    List<Reply> findByTradeBoardAndParentIsNull(@Param("tradeBoard") TradeBoard tradeBoard);

    @Query("SELECT r FROM Reply r WHERE r.community = :community")
    List<Reply> findByCommunity(@Param("community") Community community);

    @Query("SELECT r FROM Reply r WHERE r.tradeBoard = :tradeBoard")
    List<Reply> findByTradeBoard(@Param("tradeBoard") TradeBoard tradeBoard);
    
    @Query("SELECT r FROM Reply r WHERE r.parent.replyNo = :parentReplyNo ORDER BY r.regDate DESC")
    List<Reply> findChildrenByParentReplyNo(@Param("parentReplyNo") Long parentReplyNo);
}