package com.Lab4.ProductManagementSystem.dto;

import lombok.Data;

import java.util.Set;
@Data
public class CategoryUpdateRequest {
    private String name;
    private Set<Long> productIds;

    // getters and setters
}
