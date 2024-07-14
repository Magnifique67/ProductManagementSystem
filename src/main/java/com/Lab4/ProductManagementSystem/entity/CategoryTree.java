package com.Lab4.ProductManagementSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "category_tree")
public class CategoryTree {

    @Id
    private String id;

    private Long categoryId;

    private List<Long> products;
}
