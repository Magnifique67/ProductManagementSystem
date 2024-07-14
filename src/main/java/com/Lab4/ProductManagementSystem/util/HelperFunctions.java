// HelperFunctions.java
package com.Lab4.ProductManagementSystem.util;

import com.Lab4.ProductManagementSystem.repository.CategoryRepository;
import com.Lab4.ProductManagementSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelperFunctions {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public HelperFunctions(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public String getCategoryName(Long categoryId) {
        return categoryRepository.findNameById(categoryId);
    }

    public String getProductName(Long productId) {
        return productRepository.findNameById(productId);
    }

    public double getProductPrice(Long productId) {
        return productRepository.findPriceById(productId);
    }
}
