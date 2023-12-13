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
    
}
