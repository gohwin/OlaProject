package com.ola.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ola.entity.Product;
import com.ola.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
    private ProductRepository productRepository;

    private String uploadDirectory = "/images/shoes";

    @Override
    public String uploadImage(MultipartFile imageFile) throws IOException {
        // 파일 이름 생성
        String fileName = imageFile.getOriginalFilename();

        // 이미지 파일 저장 (절대 경로 사용)
        Path uploadPath = Paths.get(uploadDirectory);
        
        // 디렉토리가 없는 경우 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
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

}
