package com.ola.adminController;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ola.entity.Product;
import com.ola.repository.ProductRepository;
import com.ola.service.ProductService;

@Controller
public class adminProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepo;
	

    /* 상품목록 페이지 이동 */
	@GetMapping("/admin/products")
	public String showProductList(Model model) {
		List<Product> productList = productService.getAllProducts();
		model.addAttribute("products", productList);
		return "/admin/product_list"; // Thymeleaf 템플릿 파일 이름
	}
	
	/* 상품등록 버튼 눌렀을때 상품추가(입력)페이지 이동 */
	 @GetMapping("/admin/addProductForm")
	    public String showAddProductForm(Model model) {
	        model.addAttribute("product", new Product());
	        return "/admin/add_product_form"; // 상품 추가 폼 페이지
	    }
	 
	 @PostMapping("/admin/addProduct")
	    public String addProductAction(
	            @RequestParam("productName") String productName,
	            @RequestParam("prodCategory") int prodCategory,
	            @RequestParam("price") Long price,
	            @RequestParam("prodSize") String prodSize,
	            @RequestParam("salesQuantity") Long salesQuantity,
	            @RequestParam("inventory") int inventory,
	            @RequestParam("image") MultipartFile imageFile
//	            Model model
	    ) {
	        try {
	            String imageUrl = productService.uploadImage(imageFile);
	            productService.addProduct(productName, prodCategory, price, prodSize, salesQuantity, inventory, imageUrl);
//	            model.addAttribute("message", "Product added successfully.");
	        } catch (Exception e) {
	            e.printStackTrace();
//	            model.addAttribute("error", "Error adding product.");
	        }
	        return "redirect:/admin/products"; // 다시 제품 추가 폼으로 리다이렉트
	    }
}