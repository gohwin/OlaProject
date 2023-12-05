package com.ola.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ola.entity.Community;
import com.ola.entity.TradeBoard;

public interface TradeBoardRepository extends JpaRepository<TradeBoard, Long> {
	@Query("SELECT c FROM Community c WHERE c.member.memberId = 'member'")
	Page<TradeBoard> findByMemberWrite(Pageable pageable);

	@Query("SELECT c FROM Community c WHERE c.member.memberId = 'admin'")
	List<TradeBoard> findByAdminWrite();
}