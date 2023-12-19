package com.ola.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ola.entity.Community;

public interface CommunityService {

	/* 커뮤니티게시판 리스트 출력*/
	List<Community> getAllCommunities();
	
	/* 커뮤니티 게시판 게시글 상세보기*/
	Community getCommunityById(Long communityId);

	/* 항목별 게시글 나열*/
	Page<Community> getSortedCommunities(Pageable pageable);
}
