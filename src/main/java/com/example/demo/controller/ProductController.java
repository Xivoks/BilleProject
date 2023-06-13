package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product createdProduct = productService.createProduct(product);
        ProductDto createdProductDto = modelMapper.map(createdProduct, ProductDto.class);
        return ResponseEntity.ok(createdProductDto);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ProductDto>> createProducts(@RequestBody List<ProductDto> productDtoList) {
        List<Product> productList = productDtoList.stream()
                .map(productDto -> modelMapper.map(productDto, Product.class))
                .collect(Collectors.toList());

        List<Product> createdProducts = productService.createProducts(productList);

        List<ProductDto> createdProductDtoList = createdProducts.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(createdProductDtoList);
    }


    @GetMapping
    public String getAllProducts(Model model, Pageable pageable) {
        Page<Product> productPage = productService.getAllProducts(pageable);
        List<ProductDto> productDtoList = productPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        model.addAttribute("products", productDtoList);
        model.addAttribute("page", productPage);
        return "products";
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto updatedProductDto) {
        Product updatedProduct = modelMapper.map(updatedProductDto, Product.class);
        Product product = productService.updateProduct(id, updatedProduct);
        if (product != null) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
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
}
