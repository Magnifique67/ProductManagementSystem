package com.Lab4.ProductManagementSystem.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.Map;

@Document(collection = "product_attributes")
@Data
public class ProductAttributes {
    @Id
    private String id;
    private Long productId;
    private Map<String, Object> attributes;
}