package com.ola.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ola.entity.TradeBoard;

public interface TradeBoardRepository extends JpaRepository<TradeBoard, Long> {
	@Query("SELECT c FROM TradeBoard c WHERE c.member.role = 'ROLE_MEMBER'")
	Page<TradeBoard> findByMemberWrite(Pageable pageable);

	@Query("SELECT c FROM TradeBoard c WHERE c.member.role = 'ROLE_ADMIN'")
	List<TradeBoard> findByAdminWrite();

	@Query("SELECT c FROM TradeBoard c WHERE c.member.name LIKE %:memberName% AND c.member.role = 'ROLE_MEMBER'")
	Page<TradeBoard> findByMemberNameContaining(@Param("memberName") String MemberName, Pageable pageable);

	@Query("SELECT c FROM TradeBoard c WHERE c.title LIKE %:search% AND c.member.role = 'ROLE_MEMBER'")
	Page<TradeBoard> findByTitleContaining(@Param("search") String title, Pageable pageable);

}