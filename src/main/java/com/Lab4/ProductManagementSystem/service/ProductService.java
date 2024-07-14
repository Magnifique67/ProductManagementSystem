package com.Lab4.ProductManagementSystem.service;

import com.Lab4.ProductManagementSystem.entity.Category;
import com.Lab4.ProductManagementSystem.entity.Product;
import com.Lab4.ProductManagementSystem.exception.ResourceNotFoundException;
import com.Lab4.ProductManagementSystem.repository.CategoryRepository;
import com.Lab4.ProductManagementSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final String DEFAULT_SORT_FIELD = "id";
    private static final String DEFAULT_SORT_DIRECTION = "asc";
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Product> getProducts(Integer page, Integer size, String sortField, String sortDirection) {
        int actualPage = (page == null) ? DEFAULT_PAGE : page;
        int actualSize = (size == null) ? DEFAULT_SIZE : size;
        String actualSortField = (sortField == null) ? DEFAULT_SORT_FIELD : sortField;
        String actualSortDirection = (sortDirection == null) ? DEFAULT_SORT_DIRECTION : sortDirection;

        Sort sort = actualSortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(actualSortField).ascending() : Sort.by(actualSortField).descending();
        Pageable pageable = PageRequest.of(actualPage, actualSize, sort);
        return productRepository.findAll(pageable);
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

    public Product updateProduct(Long productId, Product updatedProduct) {
        // Find the existing product by ID
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        // Update product details
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());

        // Ensure the product's category is updated if it has changed
        Category category = existingProduct.getCategory();
        if (!category.getProducts().contains(existingProduct)) {
            category.getProducts().add(existingProduct);
            categoryRepository.save(category);
        }

        // Save the updated product
        return productRepository.save(existingProduct);
    }
}