package com.ola.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ola.entity.Community;
import com.ola.repository.CommunityRepository;

@Service
public class CommunityServiceImpl implements CommunityService {

	@Autowired
    private CommunityRepository communityRepository;

    @Override
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    // 기타 필요한 메소드 구현
}
