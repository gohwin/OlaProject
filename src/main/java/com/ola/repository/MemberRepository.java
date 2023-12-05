package com.ola.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ola.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
	boolean existsByEmail(String email);
	
	boolean existsByMemberId(String memberId);
	
	Member findByNameAndEmail(String name, String email);

	Member findByEmailVerificationToken(String token);
	
	Optional<Member> findByMemberIdAndNameAndEmail(String memberId, String name, String email);
	
	Member findByMemberId(String memberId);
	
}