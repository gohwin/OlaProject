package com.ola.service.community;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ola.entity.Community;
import com.ola.repository.CommunityRepository;

@Service
public class CommunityServiceImpl implements CommunityService {

	@Autowired
    private CommunityRepository communityRepository;

	/* 커뮤니티 게시글 목록 최신등록 날짜 순으로 출력*/
    @Override
    public List<Community> getAllCommunities() {
    	return communityRepository.findAll(Sort.by(Sort.Direction.DESC, "regDate"));
    }

    
    /* 게시글 상세보기*/
    @Override
    public Community getCommunityById(Long communityId) {
        return communityRepository.findById(communityId).orElse(null);
    }

    /* 항목별 게시글 나열*/
	@Override
	public Page<Community> getSortedCommunities(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
