package com.ola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PageController {

	@GetMapping("/main")
	public void mainView() {

	}

	@GetMapping("/item/clothes")
	public String showClothesPage() {
		return "item/clothes";
	}

	@GetMapping("/item/pants")
	public String showPantsPage() {
		return "item/pants";
	}

	@GetMapping("/item/shoes")
	public String showShoesPage() {
		return "item/shoes";
	}

	@GetMapping("/item/accessories")
	public String showAccessoriesPage() {
		return "item/accessories";
	}

	@GetMapping("/item/sales")
	public String showsalesPage() {
		return "item/sales";
	}
}
