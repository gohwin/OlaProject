package com.ola.repository;

import java.util.Date;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ola.entity.Community;
import com.ola.entity.Member;
import com.ola.entity.Role;


@SpringBootTest
public class RepositoryTest {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private CommunityRepository commuRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Disabled
	@Test
	public void testAdminInsert() {
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
				.zipNum("14178")
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
				.zipNum("14178")
				.build();
		  
		memberRepo.save(member1);

	}

	@Disabled
	@Test
	public void testCommuBoard() {
		
		Member member = memberRepo.findById("member").get();
		IntStream.rangeClosed(1, 30).forEach(i -> {
		Community comm = Community.builder()
				.commentCount(0)
				.content("게시글입니다." + i)
				.likeCount(0)
				.member(member)
				.regDate(new Date())
				.viewCount(0)
				.title("테스트 게시글" + i)
				.build();
		
		commuRepo.save(comm);
		});
	}
}
