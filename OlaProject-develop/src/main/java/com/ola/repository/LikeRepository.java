package com.ola.repository;

import com.ola.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LikeRepository extends JpaRepository<Community, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM community_likes WHERE member_id = :memberId", nativeQuery = true)
    void deleteByMember(@Param("memberId") String memberId);
}
