package com.Lab4.ProductManagementSystem.controller;

import com.Lab4.ProductManagementSystem.entity.ProductReview;
import com.Lab4.ProductManagementSystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<ProductReview> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ProductReview getReviewById(@PathVariable String id) {
        return reviewService.getReviewById(id);
    }

    @PostMapping
    public ProductReview createReview(@RequestBody ProductReview review) {
        return reviewService.saveReview(review);
    }

    @PutMapping("/{id}")
    public ProductReview updateReview(@PathVariable String id, @RequestBody ProductReview review) {
        review.setId(id);
        return reviewService.saveReview(review);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
    }
}