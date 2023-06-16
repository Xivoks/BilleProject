package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.exception.CustomException;
import com.example.demo.model.Product;
import com.example.demo.model.ProductFilter;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        logger.info("Creating a new product");
        Product product = modelMapper.map(productDto, Product.class);
        Product createdProduct = productService.createProduct(product);
        ProductDto createdProductDto = modelMapper.map(createdProduct, ProductDto.class);
        return ResponseEntity.ok(createdProductDto);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ProductDto>> createProducts(@RequestBody List<ProductDto> productDtoList) {
        logger.info("Creating multiple products");
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
    public String getAllProducts(Model model, Pageable pageable, @ModelAttribute @Valid ProductFilter filter) {
        logger.warn("Fetching all products");

        Page<Product> productPage = productService.getFilteredProducts(pageable, filter.getName(), filter.getCategory(),
                filter.getMinPrice(), filter.getMaxPrice());
        List<ProductDto> productDtoList = productPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

        model.addAttribute("products", productDtoList);
        model.addAttribute("page", productPage);

        boolean hasNextPage = productPage.hasNext();
        model.addAttribute("hasNextPage", hasNextPage);

        boolean exceededPageSizeLimit = productPage.getSize() > 50;
        model.addAttribute("exceededPageSizeLimit", exceededPageSizeLimit);

        return "products";
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        logger.info("Fetching product with ID: {}", id);

        Product product = productService.getProductById(id);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto updatedProductDto) {
        logger.info("Updating product with ID: {}", id);

        Product updatedProduct = modelMapper.map(updatedProductDto, Product.class);
        Product product = productService.updateProduct(id, updatedProduct);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);

        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new CustomException("Product not found", 404, HttpStatus.NOT_FOUND);
        }
    }
}
