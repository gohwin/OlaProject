package com.ola.service;

import java.util.List;

import com.ola.entity.Community;
import com.ola.entity.Reply;

public interface CommunityService {

	/* 커뮤니티게시판 리스트 출력*/
	List<Community> getAllCommunities();
	
	/* 커뮤니티 게시판 게시글 상세보기*/
	Community getCommunityById(Long communityId);

	
}
