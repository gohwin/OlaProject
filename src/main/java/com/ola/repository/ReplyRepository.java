package com.ola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ola.entity.Community;
import com.ola.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	List<Reply> findByCommunity_CommunityNo(Long communityNo);
	
	/* 관리자페이지 게시글 상세보기에서 댓글 출력 하기*/
	List<Reply> findByCommunityOrderByRegDateAsc(Community community);
}