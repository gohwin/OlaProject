package com.ola.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
