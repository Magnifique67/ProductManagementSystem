package com.Lab4.ProductManagementSystem.controller;

import com.Lab4.ProductManagementSystem.dto.CategoryWithProductsDTO;
import com.Lab4.ProductManagementSystem.entity.Product;
import com.Lab4.ProductManagementSystem.service.ProductTreeService;
import com.Lab4.ProductManagementSystem.util.BinaryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/product-tree")
public class ProductTreeController {

    private static final Logger LOGGER = Logger.getLogger(ProductTreeController.class.getName());

    private final ProductTreeService productTreeService;

    @Autowired
    public ProductTreeController(ProductTreeService productTreeService) {
        this.productTreeService = productTreeService;
    }

    @GetMapping("/contains/{categoryId}/{productId}")
    public ResponseEntity<Boolean> containsProduct(@PathVariable Long categoryId, @PathVariable Long productId) {
        Product product = new Product();
        product.setId(productId);
        boolean contains = productTreeService.containsProduct(categoryId, product);
        return ResponseEntity.ok(contains);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProductToTree(@RequestBody Product product) {
        LOGGER.info("Received request to add product: " + product);
        try {
            productTreeService.addProductToTree(product);
            LOGGER.info("Product added successfully: " + product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully.");
        } catch (RuntimeException e) {
            LOGGER.severe("Error adding product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{categoryId}/{productId}")
    public ResponseEntity<String> deleteProductFromTree(@PathVariable Long categoryId, @PathVariable Long productId) {
        Product product = new Product();
        product.setId(productId);
        try {
            boolean deleted = productTreeService.deleteProductFromTree(categoryId, product);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in tree.");
            }
        } catch (RuntimeException e) {
            LOGGER.severe("Error deleting product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<BinaryTree<Product>> getProductTree(@PathVariable Long categoryId) {
        BinaryTree<Product> tree = productTreeService.getProductTree(categoryId);
        if (tree != null) {
            return ResponseEntity.ok(tree);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all-categories")
    public ResponseEntity<List<CategoryWithProductsDTO>> getAllCategoryTrees() {
        List<CategoryWithProductsDTO> categoryDTOs = productTreeService.getAllCategoriesWithProducts();
        return ResponseEntity.ok(categoryDTOs);
    }
}
