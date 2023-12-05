package com.ola.member.service;

public interface MemberService {
	
	boolean isMemberIdExists(String memberId);

	String findMemberIdByNameAndEmail(String name, String email);

	boolean validateUser(String memberId, String name, String email);
	
	boolean updatePassword(String memberId, String newPassword);
	
	
}