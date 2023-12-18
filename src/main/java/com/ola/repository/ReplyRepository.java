package com.ola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ola.entity.Community;
import com.ola.entity.Reply;


@Transactional
public interface ReplyRepository extends JpaRepository<Reply, Long> {
	List<Reply> findByCommunity_CommunityNo(Long communityNo);
	
	/* 관리자페이지 게시글 상세보기에서 댓글 출력 하기*/
	List<Reply> findByCommunityOrderByRegDateAsc(Community community);
	
	@Modifying
	@Query("DELETE FROM Reply r WHERE r.member.memberId LIKE %:memberId%")
	void deleteByMember(@Param("memberId") String memberId);
}