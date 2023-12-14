package com.ola.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ola.entity.Product;

public interface ProductService {
	String uploadImage(MultipartFile imageFile, int prodCategory) throws IOException;
	
    void addProduct(String productName, int prodCategory, Long price, String prodSize, Long salesQuantity, int inventory, String imageUrl);

    List<Product> getAllProducts();
    
    void reduceInventory(Long productNo, int quantity);
    
    /* 상품 수정페이지 찾는 서비스*/
    Product getProductById(Long productNo);
    
    /* 상품 수정 */
    Product updateProduct(Product product);
    
    /* 상품 삭제*/
    void deleteProduct(Long productId);
}
