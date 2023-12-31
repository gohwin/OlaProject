package com.ola.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Product;
import com.ola.repository.ProductRepository;
import com.ola.service.product.ProductService;

@Controller
@RequestMapping
public class ProductController {

	@Autowired
	private ProductRepository prodRepo;

	@Autowired
	private ProductService productService;

	@GetMapping("/item/all")
	public String showAllPage(Model model, @RequestParam(name = "search", required = false) String search,
			@PageableDefault(size = Integer.MAX_VALUE, sort = "productNo", direction = Direction.ASC) Pageable pageable) {

		Page<Product> prodPage;
		if (search != null && !search.isEmpty()) {
			prodPage = productService.searchProductsByName(search, pageable);
		} else {
			prodPage = prodRepo.findAll(pageable);
		}

		List<String> category = new ArrayList<>();

		// 카테고리 번호에 따른 문자열 매핑
		Map<Integer, String> categoryMap = new HashMap<>();
		categoryMap.put(1, "top");
		categoryMap.put(2, "bottom");
		categoryMap.put(3, "shoes");
		categoryMap.put(4, "etc");
		categoryMap.put(5, "sales");
		categoryMap.put(6, "soldout");

		// 모든 상품의 카테고리 값을 문자열로 변환하여 리스트에 저장
		for (Product product : prodPage) {
			int categoryNumber = product.getProdCategory(); // getCategory()는 카테고리 번호를 반환
			String categoryString = categoryMap.getOrDefault(categoryNumber, "unknown"); // 매핑된 문자열 얻기
			category.add(categoryString);
		}
		model.addAttribute("prodPage", prodPage);
		model.addAttribute("prodList", prodPage);
		model.addAttribute("category", category);
		return "item/all";
	}

	@GetMapping("/item/top")
	public String showTopPage(Model model) {
		List<Product> top = prodRepo.findByProdCategory(1);
		List<String> category = new ArrayList<>();

		// 카테고리 번호에 따른 문자열 매핑
		Map<Integer, String> categoryMap = new HashMap<>();
		categoryMap.put(1, "top");
		categoryMap.put(2, "bottom");
		categoryMap.put(3, "shoes");
		categoryMap.put(4, "etc");
		categoryMap.put(5, "sales");

		// 모든 상품의 카테고리 값을 문자열로 변환하여 리스트에 저장
		for (Product product : top) {
			int categoryNumber = product.getProdCategory(); // getCategory()는 카테고리 번호를 반환
			String categoryString = categoryMap.getOrDefault(categoryNumber, "unknown"); // 매핑된 문자열 얻기
			category.add(categoryString);
		}
		model.addAttribute("category", category);

		model.addAttribute("top", top);
		return "item/top";
	}

	@GetMapping("/item/bottom")
	public String showBottomPage(Model model) {
		List<Product> bottom = prodRepo.findByProdCategory(2);
		List<String> category = new ArrayList<>();

		// 카테고리 번호에 따른 문자열 매핑
		Map<Integer, String> categoryMap = new HashMap<>();
		categoryMap.put(1, "top");
		categoryMap.put(2, "bottom");
		categoryMap.put(3, "shoes");
		categoryMap.put(4, "etc");
		categoryMap.put(5, "sales");

		// 모든 상품의 카테고리 값을 문자열로 변환하여 리스트에 저장
		for (Product product : bottom) {
			int categoryNumber = product.getProdCategory(); // getCategory()는 카테고리 번호를 반환
			String categoryString = categoryMap.getOrDefault(categoryNumber, "unknown"); // 매핑된 문자열 얻기
			category.add(categoryString);
		}

		model.addAttribute("bottom", bottom);
		model.addAttribute("category", category);
		return "item/bottom";
	}

	@GetMapping("/item/shoes")
	public String showShoesPage(Model model) {
		List<Product> shoes = prodRepo.findByProdCategory(3);
		List<String> category = new ArrayList<>();

		// 카테고리 번호에 따른 문자열 매핑
		Map<Integer, String> categoryMap = new HashMap<>();
		categoryMap.put(1, "top");
		categoryMap.put(2, "bottom");
		categoryMap.put(3, "shoes");
		categoryMap.put(4, "etc");
		categoryMap.put(5, "sales");

		// 모든 상품의 카테고리 값을 문자열로 변환하여 리스트에 저장
		for (Product product : shoes) {
			int categoryNumber = product.getProdCategory(); // getCategory()는 카테고리 번호를 반환
			String categoryString = categoryMap.getOrDefault(categoryNumber, "unknown"); // 매핑된 문자열 얻기
			category.add(categoryString);
		}
		model.addAttribute("category", category);

		model.addAttribute("shoes", shoes);
		return "item/shoes";
	}

	@GetMapping("/item/etc")
	public String showEtcPage(Model model) {
		List<Product> etc = prodRepo.findByProdCategory(4);
		List<String> category = new ArrayList<>();

		// 카테고리 번호에 따른 문자열 매핑
		Map<Integer, String> categoryMap = new HashMap<>();
		categoryMap.put(1, "top");
		categoryMap.put(2, "bottom");
		categoryMap.put(3, "shoes");
		categoryMap.put(4, "etc");
		categoryMap.put(5, "sales");

		// 모든 상품의 카테고리 값을 문자열로 변환하여 리스트에 저장
		for (Product product : etc) {
			int categoryNumber = product.getProdCategory(); // getCategory()는 카테고리 번호를 반환
			String categoryString = categoryMap.getOrDefault(categoryNumber, "unknown"); // 매핑된 문자열 얻기
			category.add(categoryString);
		}
		model.addAttribute("category", category);

		model.addAttribute("etc", etc);
		return "item/etc";
	}

	@GetMapping("/item/sales")
	public String showSalesPage(Model model) {
		List<Product> sales = prodRepo.findByProdCategory(5);
		List<String> category = new ArrayList<>();

		// 카테고리 번호에 따른 문자열 매핑
		Map<Integer, String> categoryMap = new HashMap<>();
		categoryMap.put(1, "top");
		categoryMap.put(2, "bottom");
		categoryMap.put(3, "shoes");
		categoryMap.put(4, "etc");
		categoryMap.put(5, "sales");

		// 모든 상품의 카테고리 값을 문자열로 변환하여 리스트에 저장
		for (Product product : sales) {
			int categoryNumber = product.getProdCategory(); // getCategory()는 카테고리 번호를 반환
			String categoryString = categoryMap.getOrDefault(categoryNumber, "unknown"); // 매핑된 문자열 얻기
			category.add(categoryString);
		}
		model.addAttribute("category", category);

		model.addAttribute("sales", sales);
		return "item/sales";
	}

	@GetMapping("/item/details")
	public String showItemDetails(@RequestParam Long productNo, Model model) {
		Product product = prodRepo.findById(productNo).orElse(null);
		if (product != null) {
			String categoryName = convertCategoryToName(product.getProdCategory()); // Assuming getCategory() returns
																					// the category number
			model.addAttribute("product", product);
			model.addAttribute("category", categoryName); // Add the converted category name to the model
		}
		return "item/details";
	}

	private String convertCategoryToName(int category) {
		switch (category) {
		case 1:
			return "top";
		case 2:
			return "bottom";
		case 3:
			return "shoes";
		case 4:
			return "etc";
		case 5:
			return "sales";
		default:
			return "unknown"; // Default case if category does not match
		}
	}
}
