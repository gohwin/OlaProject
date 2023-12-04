package com.ola.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ola.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query("SELECT c FROM Community c WHERE c.member.memberId = 'member'")
    Page<Community> findByMemberWrite(Pageable pageable);

    @Query("SELECT c FROM Community c WHERE c.member.memberId = 'admin'")
    List<Community> findByAdminWrite();
}

