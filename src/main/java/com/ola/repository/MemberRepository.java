package com.ola.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ola.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}