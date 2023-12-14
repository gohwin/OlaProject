package com.ola.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ola.entity.Product;
import com.ola.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
    private ProductRepository productRepository;

    private String uploadDirectory = "/images/";

    @Override
    public String uploadImage(MultipartFile imageFile, int prodCategory) throws IOException {
        // 파일 이름 생성
        String fileName = imageFile.getOriginalFilename();
        
        String category = convertCategoryToName(prodCategory);

        // 이미지 파일 저장 (절대 경로 사용)
        Path uploadPath = Paths.get(uploadDirectory + category);
        
        // 디렉토리가 없는 경우 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
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



    @Override
    public void addProduct(String productName, int prodCategory, Long price, String prodSize, Long salesQuantity, int inventory, String imageUrl) {
        Product product = new Product();
        product.setProductName(productName);
        product.setProdCategory(prodCategory);
        product.setPrice(price);
        product.setProdSize(prodSize);
        product.setSalesQuantity(salesQuantity);
        product.setInventory(inventory);
        product.setImage(imageUrl);

        productRepository.save(product);
    }



    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @Override
    public void reduceInventory(Long productNo, int quantity) {
        Product product = productRepository.findById(productNo)
            .orElseThrow(() -> new IllegalStateException("Product not found"));
        product.reduceInventory(quantity);
        productRepository.save(product);
    }

    /*상품 수정 페이지*/
    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    /* 상품 수정*/
    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    /* 상품 삭제 */
    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

}
