package com.ola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ola.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	List<Reply> findByCommunity_CommunityNo(Long communityNo);
}