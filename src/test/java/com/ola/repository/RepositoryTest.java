package com.ola.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ola.entity.Member;
import com.ola.entity.Role;


@SpringBootTest
public class RepositoryTest {
	
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Disabled
	@Test
	public void testDataInsert() {
		Member member =
				Member.builder()
				.name("홍길동")
				.email("gdhong@email.com")
				.phoneNumber("010-1111-1111")
				.address("서울시 신림동")
				.detailedAddress("자이아파트 101동 101호")
				.role(Role.ROLE_ADMIN)
				.memberId("admin")
				.password(encoder.encode("1111"))
				.build();
		  
		memberRepo.save(member);
		
		Member member1 =
				Member.builder()
				.name("이순신")
				.email("sslee@email.com")
				.phoneNumber("010-1111-1111")
				.address("서울시 강남구")
				.detailedAddress("롯데캐슬 101동 101호")
				.role(Role.ROLE_MEMBER)
				.memberId("member")
				.password(encoder.encode("1111"))
				.build();
		  
		memberRepo.save(member1);

	}
}
