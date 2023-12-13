package com.ola.service;

import java.util.List;

import com.ola.entity.TradeBoard;

public interface TradeBoardService {
	// 중고거래 게시글 조회
    List<TradeBoard> getAllTradeBoards();
    
}	