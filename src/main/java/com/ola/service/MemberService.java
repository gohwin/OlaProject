package com.ola.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ola.repository.MemberRepository;

@Service
public class MemberService {

    private MemberRepository memberRepo;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepo = memberRepository;
    }

    // 아이디 중복검사
    public boolean isMemberIdExists(String memberId) {
        return memberRepo.existsByMemberId(memberId);
    }
}