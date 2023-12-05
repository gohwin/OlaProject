package com.ola.member.service;

//MemberServiceImpl.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ola.entity.Member;
import com.ola.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public boolean isMemberIdExists(String memberId) {
		return memberRepository.existsByMemberId(memberId);
	}
	
	@Override
    public String findMemberIdByNameAndEmail(String name, String email) {
        // 이름과 이메일을 사용하여 멤버 조회
        Member member = memberRepository.findByNameAndEmail(name, email);

        if (member != null) {
            return member.getMemberId(); // 또는 사용자 정의 ID 필드
        } else {
            return null;
        }
    }
}
	
