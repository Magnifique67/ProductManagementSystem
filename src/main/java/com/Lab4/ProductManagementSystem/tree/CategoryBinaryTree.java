package com.Lab4.ProductManagementSystem.tree;

import com.Lab4.ProductManagementSystem.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoryBinaryTree {

    private Map<String, BinaryTree> categoryTrees = new HashMap<>();

    public static class BinaryTree {
        private TreeNode root;

        public static class TreeNode {
            private Product product;
            private TreeNode left;
            private TreeNode right;

            public TreeNode(Product product) {
                this.product = product;
            }
        }

        public void addProduct(Product product) {
            root = addRecursive(root, product);
        }

        private TreeNode addRecursive(TreeNode node, Product product) {
            if (node == null) {
                return new TreeNode(product);
            }

            if (product.getId() < node.product.getId()) {
                node.left = addRecursive(node.left, product);
            } else if (product.getId() > node.product.getId()) {
                node.right = addRecursive(node.right, product);
            }

            return node;
        }

        public boolean deleteProduct(Long id) {
            TreeNode[] deletedNode = {null};
            root = deleteRecursive(root, id, deletedNode);
            return deletedNode[0] != null;
        }

        private TreeNode deleteRecursive(TreeNode node, Long id, TreeNode[] deletedNode) {
            if (node == null) {
                return null;
            }

            if (id.equals(node.product.getId())) {
                deletedNode[0] = node;

                if (node.left == null && node.right == null) {
                    return null;
                }
                if (node.left == null) {
                    return node.right;
                }
                if (node.right == null) {
                    return node.left;
                }
                Long smallestValue = findSmallestValue(node.right);
                node.product.setId(smallestValue);
                node.right = deleteRecursive(node.right, smallestValue, deletedNode);
                return node;
            }
            if (id < node.product.getId()) {
                node.left = deleteRecursive(node.left, id, deletedNode);
                return node;
            }
            node.right = deleteRecursive(node.right, id, deletedNode);
            return node;
        }

        private Long findSmallestValue(TreeNode root) {
            return root.left == null ? root.product.getId() : findSmallestValue(root.left);
        }

        public Product searchProduct(Long id) {
            return searchRecursive(root, id);
        }

        private Product searchRecursive(TreeNode node, Long id) {
            if (node == null) {
                return null;
            }

            if (id.equals(node.product.getId())) {
                return node.product;
            }
            return id < node.product.getId() ? searchRecursive(node.left, id) : searchRecursive(node.right, id);
        }

        public List<Product> getAllProducts(TreeNode node) {
            if (node == null) {
                return new ArrayList<>();
            }
            List<Product> leftProducts = getAllProducts(node.left);
            List<Product> rightProducts = getAllProducts(node.right);
            leftProducts.add(node.product);
            leftProducts.addAll(rightProducts);
            return leftProducts;
        }

        public List<Product> getAllProducts() {
            return getAllProducts(root);
        }
    }

    public void addProductToCategory(String category, Product product) {
        categoryTrees.computeIfAbsent(category, k -> new BinaryTree()).addProduct(product);
    }

    public boolean removeProductFromCategory(String category, Long productId) {
        BinaryTree tree = categoryTrees.get(category);
        if (tree != null) {
            return tree.deleteProduct(productId);
        }
        return false;
    }

    public Product findProductInCategory(String category, Long productId) {
        BinaryTree tree = categoryTrees.get(category);
        if (tree != null) {
            return tree.searchProduct(productId);
        }
        return null;
    }

    public List<Product> getAllProductsInCategory(String categoryName) {
        List<Product> allProducts = new ArrayList<>();
        categoryTrees.forEach((key, value) -> {
            if (key.equals(categoryName)) {
                allProducts.addAll(value.getAllProducts());
            }
        });
        return allProducts;
    }
}
