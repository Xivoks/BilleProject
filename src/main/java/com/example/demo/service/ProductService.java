package com.example.demo.service;

import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private static List<Product> productList = new ArrayList<>();

    public Product createProduct(Product product) {
        productList.add(product);
        return product;
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public Product getProductById(Long id) {
        for (Product product : productList) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        for (Product product : productList) {
            if (product.getId().equals(id)) {
                product.setName(updatedProduct.getName());
                product.setPrice(updatedProduct.getPrice());
                return product;
            }
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        for (Product product : productList) {
            if (product.getId().equals(id)) {
                productList.remove(product);
                return true;
            }
        }
        return false;
    }
}
