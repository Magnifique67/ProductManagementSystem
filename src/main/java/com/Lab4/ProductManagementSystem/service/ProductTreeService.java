package com.Lab4.ProductManagementSystem.service;

import com.Lab4.ProductManagementSystem.dto.CategoryWithProductsDTO;
import com.Lab4.ProductManagementSystem.dto.ProductDTO;
import com.Lab4.ProductManagementSystem.entity.Category;
import com.Lab4.ProductManagementSystem.entity.Product;
import com.Lab4.ProductManagementSystem.entity.CategoryTree;
import com.Lab4.ProductManagementSystem.repository.CategoryRepository;
import com.Lab4.ProductManagementSystem.repository.ProductRepository;
import com.Lab4.ProductManagementSystem.repository.CategoryTreeRepository;
import com.Lab4.ProductManagementSystem.util.BinaryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProductTreeService {

    private static final Logger LOGGER = Logger.getLogger(ProductTreeService.class.getName());

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryTreeRepository categoryTreeRepository; // Inject the MongoDB repository for CategoryTree

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

        // Update the CategoryTree in MongoDB
        updateCategoryTreeInMongo(product.getCategory().getId(), tree);

        LOGGER.info("Added product to tree: " + product);
    }

    @Transactional
    public boolean deleteProductFromTree(Long categoryId, Product product) {
        BinaryTree<Product> tree = categoryTrees.get(categoryId);
        if (tree != null) {
            boolean deleted = tree.delete(product);
            if (deleted) {
                productRepository.delete(product);

                // Update the CategoryTree in MongoDB
                updateCategoryTreeInMongo(categoryId, tree);

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
    @Transactional(readOnly = true)
    public List<CategoryWithProductsDTO> getAllCategoriesWithProducts() {
        List<CategoryTree> categoryTrees = categoryTreeRepository.findAll();

        return categoryTrees.stream()
                .map(categoryTree -> {
                    // Fetch the category details using categoryId
                    Long categoryId = categoryTree.getCategoryId();
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

                    // Map products to ProductDTOs
                    List<Long> productIds = categoryTree.getProducts();
                    List<ProductDTO> productDTOs = getProductDTOs(productIds);

                    // Create CategoryWithProductsDTO
                    return new CategoryWithProductsDTO(category.getId(), category.getName(), productDTOs);
                })
                .collect(Collectors.toList());
    }

    private List<ProductDTO> getProductDTOs(List<Long> productIds) {
        return productIds.stream()
                .map(productId -> {
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
                    return new ProductDTO(product.getId(), product.getName(), product.getPrice());
                })
                .collect(Collectors.toList());
    }


        private void updateCategoryTreeInMongo(Long categoryId, BinaryTree<Product> tree) {
        CategoryTree categoryTree = categoryTreeRepository.findByCategoryId(categoryId)
                .orElse(new CategoryTree());

        categoryTree.setCategoryId(categoryId);
        categoryTree.setProducts(tree.toListOfIds()); // Assuming toListOfIds() converts products to List<Long>

        categoryTreeRepository.save(categoryTree);
    }
}


