package com.ola.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
	String uploadImage(MultipartFile imageFile) throws IOException;
    void addProduct(String productName, int prodCategory, Long price, String prodSize, Long salesQuantity, int inventory, String imageUrl);
}
