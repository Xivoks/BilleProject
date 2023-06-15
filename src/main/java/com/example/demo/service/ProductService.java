package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
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
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        if (productRepository.existsById(id)) {
            updatedProduct.setId(id);
            return productRepository.save(updatedProduct);
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
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
