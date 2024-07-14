package com.Lab4.ProductManagementSystem.service;

import com.Lab4.ProductManagementSystem.entity.ProductReview;
import com.Lab4.ProductManagementSystem.repository.ProductMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ProductMongoRepository productMongoRepository;

    public List<ProductReview> getAllReviews() {
        return productMongoRepository.findAll();
    }

    public ProductReview getReviewById(String id) {
        return productMongoRepository.findById(id).orElse(null);
    }

    public ProductReview saveReview(ProductReview review) {
        return productMongoRepository.save(review);
    }

    public void deleteReview(String id) {
        productMongoRepository.deleteById(id);
    }
}
