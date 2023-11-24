package com.ola.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ola.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}