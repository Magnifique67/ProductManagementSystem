package com.Lab4.ProductManagementSystem.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.Lab4.ProductManagementSystem.entity.ProductReview;

public interface ProductMongoRepository extends MongoRepository<ProductReview, String> {
}