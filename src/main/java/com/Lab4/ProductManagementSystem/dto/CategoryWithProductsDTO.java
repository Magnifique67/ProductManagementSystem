package com.Lab4.ProductManagementSystem.dto;

import com.Lab4.ProductManagementSystem.entity.Product;
import com.Lab4.ProductManagementSystem.util.BinaryTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWithProductsDTO {
    private Long id;
    private String name;
    private List<Product> products;

    // Getters and Setters
    // Constructors
    // Other necessary methods
}
