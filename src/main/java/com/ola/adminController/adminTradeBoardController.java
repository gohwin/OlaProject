package com.ola.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ola.entity.TradeBoard;
import com.ola.service.TradeBoardService;

@Controller
public class adminTradeBoardController {

	@Autowired
	private TradeBoardService tradeBoardService;

	@GetMapping("/admin/tradeBoard")
	public String showTradeBoardList(Model model) {
		model.addAttribute("tradeBoards", tradeBoardService.getAllTradeBoards());
		return "/admin/tradeBoard_list";
	}
	
	@GetMapping("/admin/tradeBoard/detail/{tradeBoardNo}")
    public String showTradeBoardDetail(@PathVariable("tradeBoardNo") Long tradeBoardNo, Model model) {
        TradeBoard tradeBoard = tradeBoardService.getTradeBoardById(tradeBoardNo);
        model.addAttribute("tradeBoard", tradeBoard);
        return "/admin/tradeBoardDetail"; // 상세 페이지 템플릿
    }
}
