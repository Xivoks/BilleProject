package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = mapToProduct(productDto);
        Product createdProduct = productService.createProduct(product);
        ProductDto createdProductDto = mapToProductDto(createdProduct);
        return ResponseEntity.ok(createdProductDto);
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> productList = productService.getAllProducts();
        List<ProductDto> productDtoList = productList.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
        model.addAttribute("products", productDtoList);
        return "products";
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            ProductDto productDto = mapToProductDto(product);
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto updatedProductDto) {
        Product updatedProduct = mapToProduct(updatedProductDto);
        Product product = productService.updateProduct(id, updatedProduct);
        if (product != null) {
            ProductDto productDto = mapToProductDto(product);
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    private Product mapToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return product;
    }
}
