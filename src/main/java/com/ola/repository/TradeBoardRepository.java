package com.ola.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ola.entity.TradeBoard;

public interface TradeBoardRepository extends JpaRepository<TradeBoard, Long> {
}