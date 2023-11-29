package com.ola.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.ola.entity.Member;



/*
 *  JPA에서 조회한 Member 정보를 스프링 시큐리티에서 사용할 UserDetails 타입으로 변환
 */
public class SecurityUser extends User{
	private Member member;
	
	// 생성자
	public SecurityUser(Member member) {
		// 조상의 생성자를 호출하여 스프링 시큐리티에 id, password, role, id 사용 유무
		// 암호화하지 않은 처리
//		super(member.getId(), "{noop}" + member.getPassword(),
//				AuthorityUtils.createAuthorityList(member.getRole().toString()));
		
		// 암호화 한 처리
		super(member.getMemberId(), member.getPassword(),
				AuthorityUtils.createAuthorityList(member.getRole().toString()));
		this.member = member;
	}
	
	public Member getMember() {
		return member;
	}
}
