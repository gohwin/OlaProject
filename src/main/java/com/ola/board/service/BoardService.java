package com.ola.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ola.entity.Search;
import com.ola.entity.TradeBoard;



public interface BoardService {

	void insertBoard(TradeBoard board);

	void updateBoard(TradeBoard board);

	void deleteBoard(TradeBoard board);

	TradeBoard getBoard(TradeBoard board);

	Page<TradeBoard> tradeBoardList(Pageable pageable);

}