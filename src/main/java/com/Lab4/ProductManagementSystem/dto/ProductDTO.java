package com.Lab4.ProductManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
}
