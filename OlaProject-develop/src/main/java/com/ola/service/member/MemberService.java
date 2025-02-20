package com.ola.service.member;

import java.util.List;
import java.util.Optional;

import com.ola.entity.Member;

public interface MemberService {

	boolean isMemberIdExists(String memberId);

	String findMemberIdByNameAndEmail(String name, String email);

	boolean validateUser(String memberId, String name, String email);

	boolean updatePassword(String memberId, String newPassword);

	List<Member> getAllMembers();

	Optional<Member> findById(String memberId);

	void deleteMemberById(String memberId);
}