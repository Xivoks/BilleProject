package com.example.demo.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ProductFilter {
    @Size(min = 2, max = 15)
    private String name;
    private String category;
    private Double minPrice;
    private Double maxPrice;
}
