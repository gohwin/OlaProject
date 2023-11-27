package com.ola.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ola.entity.Member;


@SpringBootTest
public class RepositoryTest {
	
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private PasswordEncoder encoder;
	
	@Test
	public void testDataInsert() {
		Member member =
				Member.builder()
				.name("홍길동")
				.email("gdhong@email.com")
				.phoneNumber("010-1111-1111")
				.address("서울시 신림동")
				.detailedAddress("자이아파트 101동 101호")
				.memberType("admin")
				.memberId("member")
				.password(encoder.encode("1111"))
				.build();
		  
		memberRepo.save(member);
	}
}
