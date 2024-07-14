package com.Lab4.ProductManagementSystem.util;

import com.Lab4.ProductManagementSystem.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree<T extends Comparable<T>> {

    private Node<T> root;

    private static class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public void add(T value) {
        root = addRecursive(root, value);
    }

    private Node<T> addRecursive(Node<T> current, T value) {
        if (current == null) {
            return new Node<>(value);
        }

        int cmp = value.compareTo(current.data);
        if (cmp < 0) {
            current.left = addRecursive(current.left, value);
        } else if (cmp > 0) {
            current.right = addRecursive(current.right, value);
        }
        // If value is equal, we don't add it again
        return current;
    }

    public boolean containsNode(T value) {
        return containsNodeRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node<T> current, T value) {
        if (current == null) {
            return false;
        }

        int cmp = value.compareTo(current.data);
        if (cmp == 0) {
            return true;
        }
        return cmp < 0
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }

    public List<Long> toListOfIds() {
        List<Long> ids = new ArrayList<>();
        traverseInOrder(root, ids);
        return ids;
    }

    private void traverseInOrder(Node<T> node, List<Long> ids) {
        if (node != null) {
            traverseInOrder(node.left, ids);
            ids.add(((Product) node.data).getId()); // Assuming Product has a getId() method returning Long
            traverseInOrder(node.right, ids);
        }
    }

    public boolean delete(T value) {
        if (containsNode(value)) {
            root = deleteRecursive(root, value);
            return true;
        }
        return false;
    }

    private Node<T> deleteRecursive(Node<T> current, T value) {
        if (current == null) {
            return null;
        }

        int cmp = value.compareTo(current.data);
        if (cmp < 0) {
            current.left = deleteRecursive(current.left, value);
        } else if (cmp > 0) {
            current.right = deleteRecursive(current.right, value);
        } else {
            // Node to delete found
            if (current.left == null && current.right == null) {
                return null; // Leaf node
            } else if (current.right == null) {
                return current.left; // One child
            } else if (current.left == null) {
                return current.right; // One child
            } else {
                // Node with two children
                T smallestValue = findSmallestValue(current.right);
                current.data = smallestValue;
                current.right = deleteRecursive(current.right, smallestValue);
            }
        }
        return current;
    }

    private T findSmallestValue(Node<T> root) {
        return root.left == null ? root.data : findSmallestValue(root.left);
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
