package com.ola.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ola.entity.Product;
import com.ola.repository.ProductRepository;
import com.ola.service.ProductService;

@Controller
@RequestMapping
public class ItemController {
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/item/all")
	public String showAllPage(Model model) {
	    List<Product> prodList = prodRepo.findAll();
	    List<String> category = new ArrayList<>();

	    // 카테고리 번호에 따른 문자열 매핑
	    Map<Integer, String> categoryMap = new HashMap<>();
	    categoryMap.put(1, "top");
	    categoryMap.put(2, "bottom");
	    categoryMap.put(3, "shoes");
	    categoryMap.put(4, "etc");
	    categoryMap.put(5, "sales");

	    // 모든 상품의 카테고리 값을 문자열로 변환하여 리스트에 저장
	    for (Product product : prodList) {
	        int categoryNumber = product.getProdCategory(); // getCategory()는 카테고리 번호를 반환
	        String categoryString = categoryMap.getOrDefault(categoryNumber, "unknown"); // 매핑된 문자열 얻기
	        category.add(categoryString);
	    }

	    model.addAttribute("prodList", prodList);
	    model.addAttribute("category", category);
	    return "item/all";
	}


	@GetMapping("/item/top")
	public String showTopPage(Model model) {
		List<Product> top = prodRepo.findByProdCategory(1);

	    model.addAttribute("top", top);
	    return "item/top";
	}

	@GetMapping("/item/bottom")
	public String showBottomPage(Model model) {
		List<Product> bottom = prodRepo.findByProdCategory(2);

	    model.addAttribute("bottom", bottom);
		return "item/bottom";
	}

	@GetMapping("/item/shoes")
	public String showShoesPage(Model model) {
		List<Product> shoes= prodRepo.findByProdCategory(3);

	    model.addAttribute("shoes", shoes);
		return "item/shoes";
	}

	@GetMapping("/item/etc")
	public String showEtcPage(Model model) {
		List<Product> etc= prodRepo.findByProdCategory(4);

	    model.addAttribute("etc", etc);
		return "item/etc";
	}

	@GetMapping("/item/sales")
	public String showSalesPage(Model model) {
		List<Product> sales= prodRepo.findByProdCategory(5);

	    model.addAttribute("sales", sales);
		return "item/sales";
	}

	@GetMapping("/item/details")
	public String showItemDetails(@RequestParam Long productNo, Model model) {
	    Product product = prodRepo.findById(productNo).orElse(null);
	    if (product != null) {
	        String categoryName = convertCategoryToName(product.getProdCategory()); // Assuming getCategory() returns the category number
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

	
	@GetMapping("/item/add")
	public String addProductView() {
		return "admin/prodRegister";
	}
	
	@PostMapping("/item/add")
    public String addProductAction(
            @RequestParam("productName") String productName,
            @RequestParam("prodCategory") int prodCategory,
            @RequestParam("price") Long price,
            @RequestParam("prodSize") String prodSize,
            @RequestParam("salesQuantity") Long salesQuantity,
            @RequestParam("inventory") int inventory,
            @RequestParam("image") MultipartFile imageFile,
            Model model
    ) {
        try {
            String imageUrl = productService.uploadImage(imageFile);
            productService.addProduct(productName, prodCategory, price, prodSize, salesQuantity, inventory, imageUrl);
            model.addAttribute("message", "Product added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error adding product.");
        }
        return "redirect:/adminMain"; // 다시 제품 추가 폼으로 리다이렉트
    }
}
