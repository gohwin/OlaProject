package com.ola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ola.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
	boolean existsByEmail(String email);
	
	boolean existsByMemberId(String memberId);
	
	Member findByNameAndEmail(String name, String email);

	Member findByEmailVerificationToken(String token);
	
	
}