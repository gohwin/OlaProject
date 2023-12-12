package com.ola.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ola.entity.Community;
import com.ola.entity.Reply;

public interface CommunityRepository extends JpaRepository<Community, Long> {
	@Query("SELECT c FROM Community c WHERE c.member.role = 'ROLE_MEMBER'")
	Page<Community> findByMemberWrite(Pageable pageable);

	@Query("SELECT c FROM Community c WHERE c.member.role = 'ROLE_ADMIN'")
	List<Community> findByAdminWrite();

	List<Reply> findByCommunityNo(Long communityNo);

	@Query("SELECT c FROM Community c WHERE c.member.name LIKE %:memberName% AND c.member.role = 'ROLE_MEMBER'")
	Page<Community> findByMemberNameContaining(@Param("memberName") String MemberName, Pageable pageable);

	@Query("SELECT c FROM Community c WHERE c.title LIKE %:search% AND c.member.role = 'ROLE_MEMBER'")
	Page<Community> findByTitleContaining(@Param("search") String title, Pageable pageable);

}
