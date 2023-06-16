package com.example.demo.service;

import com.example.demo.exception.CustomException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> createProducts(List<Product> productList) {
        return productRepository.saveAll(productList);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
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

    public Page<Product> searchProducts(Specification<Product> spec, Pageable pageable) {
        return productRepository.findAll(spec, pageable);
    }

    public Page<Product> getFilteredProducts(Pageable pageable, String name, String category, Double minPrice, Double maxPrice) {
        Specification<Product> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and(ProductSpecifications.nameContains(name));
        }

        if (category != null) {
            spec = spec.and(ProductSpecifications.categoryEquals(category));
        }

        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterThanOrEqual(minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessThanOrEqual(maxPrice));
        }

        int pageSizeLimit = 50;
        if (pageable.getPageSize() > pageSizeLimit) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageSizeLimit, pageable.getSort());
        }

        return productRepository.findAll(spec, pageable);
    }
}
