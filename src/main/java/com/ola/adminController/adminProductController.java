package com.ola.adminController;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	/* 상품 추가 하는 메소드 */
	@PostMapping("/admin/addProduct")
	public String addProductAction(@RequestParam("productName") String productName,
			@RequestParam("prodCategory") int prodCategory, @RequestParam("price") Long price,
			@RequestParam("prodSize") String prodSize, @RequestParam("salesQuantity") Long salesQuantity,
			@RequestParam("inventory") int inventory, @RequestParam("image") MultipartFile imageFile) {
		try {
			String imageUrl = productService.uploadImage(imageFile, prodCategory);
			productService.addProduct(productName, prodCategory, price, prodSize, salesQuantity, inventory, imageUrl);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return "redirect:/admin/products"; // 다시 제품 추가 폼으로 리다이렉트
	}

	/* 제품 수정페이지 이동 */
	@GetMapping("/admin/editProduct/{productId}")
	public String editProduct(@PathVariable Long productId, Model model) {
		Product product = productService.getProductById(productId);
		if (product == null) {
			// 상품이 없을 경우 처리
		}
		String category = getCategoryName(product.getProdCategory());
		model.addAttribute("category", category);
		model.addAttribute("product", product);
		return "/admin/editProduct"; // 상품을 편집하기 위한 editProduct.html 템플릿을 생성하세요.
	}

	private String getCategoryName(int category) {
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
		case 6:
			return "soldout";
		default:
			return "unknown"; // 기본값, 범위 밖의 카테고리 번호 처리
		}
	}

	@PostMapping("/admin/updateProduct")
	public String updateProduct(@RequestParam("productNo") Long productNo,
			@RequestParam("productName") String productName, @RequestParam("prodCategory") int prodCategory,
			@RequestParam("price") Long price, @RequestParam("prodSize") String prodSize,
			@RequestParam("salesQuantity") Long salesQuantity, @RequestParam("inventory") int inventory,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

		// 기존 상품 정보를 가져옴
		Product existingProduct = productService.getProductById(productNo);

		if (existingProduct != null) {
			// 업데이트할 필드 설정
			existingProduct.setProductName(productName);
			existingProduct.setProdCategory(prodCategory);
			existingProduct.setPrice(price);
			existingProduct.setProdSize(prodSize);
			existingProduct.setSalesQuantity(salesQuantity);
			existingProduct.setInventory(inventory);

			if (imageFile != null && !imageFile.isEmpty()) {
				// 이미지 파일이 전송되었을 경우에만 처리
				String imagePath = productService.uploadImage(imageFile, prodCategory);
				existingProduct.setImage(imagePath);
			}

			productService.updateProduct(existingProduct);
		}

		return "redirect:/admin/products";
	}

	/* 상품 삭제 */
	@GetMapping("/admin/deleteProduct/{productId}")
	public String deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);

		return "redirect:/admin/products"; // 상품 목록으로 다시 리다이렉션
	}

}