package com.ola.service;

import java.util.Optional;

//MemberServiceImpl.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ola.entity.Member;
import com.ola.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
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

	  @Override
	    public boolean validateUser(String memberId, String name, String email) {
	        // 데이터베이스에서 사용자 조회
	        Optional<Member> member = memberRepository.findByMemberIdAndNameAndEmail(memberId, name, email);

	        // 사용자가 존재하면 true, 그렇지 않으면 false 반환
	        return member.isPresent();
	    }
	  
	  @Override
	    public boolean updatePassword(String memberId, String newPassword) {
	        Member member = memberRepository.findByMemberId(memberId);
	        if (member == null) {
	            return false;
	        }

	        // 비밀번호 해시 처리
	        String hashedPassword = passwordEncoder.encode(newPassword);
	        member.setPassword(hashedPassword);
	        memberRepository.save(member);
	        return true;
	    }
}
	
