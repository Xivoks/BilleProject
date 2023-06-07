package com.example.demo.dto;
import java.time.LocalDate;
import java.util.List;

public class CartDto {
    private Long id;
    private UserDto user;
    private List<ProductDto> products;
    private String activePromoCode;
    private LocalDate expirationDate;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public String getActivePromoCode() {
        return activePromoCode;
    }

    public void setActivePromoCode(String activePromoCode) {
        this.activePromoCode = activePromoCode;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}