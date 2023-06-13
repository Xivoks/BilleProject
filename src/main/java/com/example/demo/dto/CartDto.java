package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private UserDto user;
    private List<ProductDto> products;
    private String activePromoCode;
    private LocalDate expirationDate;
}
