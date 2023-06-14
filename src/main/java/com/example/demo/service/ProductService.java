package com.example.demo.service;

import com.example.demo.exception.CustomException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Product not found", 404, HttpStatus.NOT_FOUND));
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        if (productRepository.existsById(id)) {
            updatedProduct.setId(id);
            return productRepository.saveAndFlush(updatedProduct);
        } else {
            throw new CustomException("Product not found", 404, HttpStatus.NOT_FOUND);
        }
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            throw new CustomException("Product not found", 404, HttpStatus.NOT_FOUND);
        }
    }
}
