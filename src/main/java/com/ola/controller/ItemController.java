package com.ola.controller;

import java.util.List;

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
		// 카테고리 변환 필요(1->shoes)
		model.addAttribute("product", product);
//		model.addAttribute("productPrice", productPrice);

		return "item/details";
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
