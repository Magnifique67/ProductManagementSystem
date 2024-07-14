package com.Lab4.ProductManagementSystem.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;

@Document(collection = "product_reviews")
@Data
public class ProductReview {
    @Id
    private String id;
    private Long productId;
    private Long userId;
    private int rating;
    private String review;
    private LocalDateTime timestamp;
}
