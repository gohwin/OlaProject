package com.ola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class PageController {

	@GetMapping({ "/main", "/" })
	public void mainView() {

	}

	@GetMapping("/item/all")
	public String showAllPage() {
		return "item/all";
	}

	@GetMapping("/item/top")
	public String showTopPage() {
		return "item/top";
	}

	@GetMapping("/item/bottom")
	public String showBottomPage() {
		return "item/bottom";
	}

	@GetMapping("/item/shoes")
	public String showShoesPage() {
		return "item/shoes";
	}

	@GetMapping("/item/etc")
	public String showEtcPage() {
		return "item/etc";
	}

	@GetMapping("/item/sales")
	public String showSalesPage() {
		return "item/sales";
	}

	@GetMapping("/item/details")
	public String showItemDetails(@RequestParam String imageUrl, @RequestParam String productName,
			@RequestParam String productPrice, Model model) {
		model.addAttribute("imageUrl", imageUrl);
		model.addAttribute("productName", productName);
		model.addAttribute("productPrice", productPrice);

		return "item/details";
	}
}
