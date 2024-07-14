package com.Lab4.ProductManagementSystem.util;

import com.Lab4.ProductManagementSystem.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BinaryTree<T extends Comparable<T>> {

    public Node<T> root;

    public BinaryTree() {
        this.root = null;
    }

    public void add(T value) {
        root = addRecursive(root, value);
    }

    private Node<T> addRecursive(Node<T> current, T value) {
        if (current == null) {
            return new Node<>(value);
        }

        int cmp = value.compareTo(current.value);
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

        int cmp = value.compareTo(current.value);
        if (cmp == 0) {
            return true;
        }
        return cmp < 0
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
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

        int cmp = value.compareTo(current.value);
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
                current.value = smallestValue;
                current.right = deleteRecursive(current.right, smallestValue);
            }
        }
        return current;
    }

    private T findSmallestValue(Node<T> root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }

    public static class Node<T> {
        T value;
        public Node<T> left;
        public Node<T> right;

        Node(T value) {
            this.value = value;
            left = right = null;
        }
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
