package com.ola.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ola.entity.TradeBoard;
import com.ola.repository.TradeBoardRepository;

@Service
public class TradeBoardServiceImpl implements TradeBoardService {

	@Autowired
    private TradeBoardRepository tradeBoardRepository;

	/* 중고거래 게시글 조회 출력 */
    @Override
    public List<TradeBoard> getAllTradeBoards() {
        return tradeBoardRepository.findAll();
    }
}
