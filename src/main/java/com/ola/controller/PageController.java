package com.ola.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Community;
import com.ola.entity.Product;
import com.ola.repository.ProductRepository;

@Controller
@RequestMapping
public class PageController {
	
	@Autowired
	private ProductRepository prodRepo;

	@GetMapping({ "/main", "/" })
	public void mainView() {

	}

	@GetMapping("/item/all")
	public String showAllPage(Model model) {
		List<Product> prodList = prodRepo.findAll();
		
		model.addAttribute("prodList", prodList);
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
	public String showItemDetails(@RequestParam Long productNo, Model model) {
		Product product= prodRepo.findById(productNo).orElse(null);
//		model.addAttribute("imageUrl", imageUrl);
		
		model.addAttribute("product", product);
//		model.addAttribute("productPrice", productPrice);

		return "item/details";
	}
}
