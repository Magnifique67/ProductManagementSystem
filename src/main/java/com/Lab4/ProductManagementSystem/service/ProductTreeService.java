package com.Lab4.ProductManagementSystem.service;

import com.Lab4.ProductManagementSystem.dto.CategoryWithProductsDTO;
import com.Lab4.ProductManagementSystem.entity.Category;
import com.Lab4.ProductManagementSystem.entity.Product;
import com.Lab4.ProductManagementSystem.repository.CategoryRepository;
import com.Lab4.ProductManagementSystem.repository.ProductRepository;
import com.Lab4.ProductManagementSystem.util.BinaryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class ProductTreeService {

    private static final Logger LOGGER = Logger.getLogger(ProductTreeService.class.getName());

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Map<Long, BinaryTree<Product>> categoryTrees = new HashMap<>();

    @PostConstruct
    public void init() {
        // Load existing data from the database into the categoryTrees map
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            categoryTrees.computeIfAbsent(product.getCategory().getId(), k -> new BinaryTree<>()).add(product);
        }
        LOGGER.info("Initialized category trees with products: " + categoryTrees);
    }

    @Transactional
    public void addProductToTree(Product product) {
        Category category = product.getCategory();

        // Save the category if it doesn't exist
        if (category.getId() == null || !categoryRepository.existsById(category.getId())) {
            categoryRepository.save(category);
        }

        // Save the product to ensure it gets an ID assigned
        productRepository.save(product);

        // Retrieve or create the binary tree for the category
        BinaryTree<Product> tree = categoryTrees.computeIfAbsent(product.getCategory().getId(), k -> new BinaryTree<>());
        tree.add(product);

        // Update the categories_tree_json field for the category (if needed)
        category.setCategoriesTreeJson(tree.toJson());
        categoryRepository.save(category);

        LOGGER.info("Added product to tree: " + product);
    }

    @Transactional
    public boolean deleteProductFromTree(Long categoryId, Product product) {
        BinaryTree<Product> tree = categoryTrees.get(categoryId);
        if (tree != null) {
            boolean deleted = tree.delete(product);
            if (deleted) {
                productRepository.delete(product);
                LOGGER.info("Deleted product from tree and repository: " + product);
                return true;
            } else {
                LOGGER.warning("Product not found in tree: " + product);
                return false;
            }
        } else {
            LOGGER.warning("No category tree found for categoryId: " + categoryId);
            return false;
        }
    }

    public BinaryTree<Product> getProductTree(Long categoryId) {
        return categoryTrees.get(categoryId);
    }

    public boolean containsProduct(Long categoryId, Product product) {
        BinaryTree<Product> tree = categoryTrees.get(categoryId);
        return tree != null && tree.containsNode(product);
    }

    @Transactional
    public List<CategoryWithProductsDTO> getAllCategoriesWithProducts() {
        List<CategoryWithProductsDTO> categoryDTOs = new ArrayList<>();
        try {
            List<Category> categories = categoryRepository.findAllWithProducts();
            for (Category category : categories) {
                CategoryWithProductsDTO dto = new CategoryWithProductsDTO();
                dto.setId(category.getId());
                dto.setName(category.getName());
                dto.setProducts(new ArrayList<>(category.getProducts())); // Ensure products are loaded eagerly
                categoryDTOs.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving categories with products", e);
        }
        return categoryDTOs;
    }
}
