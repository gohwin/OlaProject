package com.ola.member.service;

public interface MemberService {
	
	boolean isMemberIdExists(String memberId);

	String findMemberIdByNameAndEmail(String name, String email);
	
}