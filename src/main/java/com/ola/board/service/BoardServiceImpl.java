package com.ola.board.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;
import com.ola.entity.Search;
import com.ola.entity.TradeBoard;
import com.ola.repository.MemberRepository;
import com.ola.repository.TradeBoardRepository;

import jakarta.transaction.Transactional;


@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private TradeBoardRepository boardRepo;
	@Autowired
	private MemberRepository memberRepo;

	@Override
	@Transactional
	public void insertBoard(TradeBoard board) {

		boardRepo.save(board);
	}

	@Override
	public void updateBoard(TradeBoard board) {
		TradeBoard newBoard = boardRepo.findById(board.getTradeBoardNo()).get();

		newBoard.setTitle(board.getTitle());
		newBoard.setContent(board.getContent());

		boardRepo.save(newBoard);
	}

	@Override
	public void deleteBoard(TradeBoard board) {
		boardRepo.deleteById(board.getTradeBoardNo());
	}

	@Override
	public TradeBoard getBoard(TradeBoard board) {

		return boardRepo.findById(board.getTradeBoardNo()).get();
	}

	@Override
	public Page<TradeBoard> tradeBoardList(Pageable pageable) {
		
		return boardRepo.findAll(pageable);
	}

//	@Override
//	public Page<TradeBoard> getBoardList(Pageable pageable, Search search) {
//		BooleanBuilder builder = new BooleanBuilder();
//
//		QBoard qboard = QBoard.board;
//
//		if (search.getSearchCondition().equals("TITLE")) {
//			builder.and(qboard.title.like("%" + search.getSearchKeyword() + "%"));
//		} else if (search.getSearchCondition().equals("CONTENT")) {
//			builder.and(qboard.content.like("%" + search.getSearchKeyword() + "%"));
//		}
//
//		return boardRepo.findAll(builder, pageable);
//	}
}
