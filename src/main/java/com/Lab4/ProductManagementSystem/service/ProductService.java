package com.Lab4.ProductManagementSystem.service;

import com.Lab4.ProductManagementSystem.entity.Category;
import com.Lab4.ProductManagementSystem.entity.Product;
import com.Lab4.ProductManagementSystem.repository.CategoryRepository;
import com.Lab4.ProductManagementSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
@Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
@Transactional
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
@Transactional

    public Product saveProduct(Product product) {
        // Check if the product has a category
        if (product.getCategory() != null) {
            Category category = product.getCategory();

            // Check if the category is new (not yet persisted)
            if (category.getId() == null) {
                // Save the category first
                category = categoryRepository.save(category); // Persist the category and get updated instance with ID
                product.setCategory(category); // Update product with persisted category
            }
        }

        // Now save the product (with updated category reference if necessary)
        return productRepository.save(product);
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}