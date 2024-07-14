package com.Lab4.ProductManagementSystem.util;

import com.Lab4.ProductManagementSystem.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BinaryTreeTest {

    private BinaryTree<Product> tree;

    @BeforeEach
    public void setUp() {
        tree = new BinaryTree<>();
        tree.add(new Product(1L, "Product 1", 100.0, null));
        tree.add(new Product(2L, "Product 2", 200.0, null));
        tree.add(new Product(3L, "Product 3", 300.0, null));
    }

    @Test
    public void testAdd() {
        tree.add(new Product(4L, "Product 4", 400.0, null));
        assertTrue(tree.containsNode(new Product(4L, "Product 4", 400.0, null)));
    }

    @Test
    public void testContainsNode() {
        assertTrue(tree.containsNode(new Product(1L, "Product 1", 100.0, null)));
        assertFalse(tree.containsNode(new Product(5L, "Product 5", 500.0, null)));
    }

    @Test
    public void testDeleteLeafNode() {
        assertTrue(tree.delete(new Product(1L, "Product 1", 100.0, null)));
        assertFalse(tree.containsNode(new Product(1L, "Product 1", 100.0, null)));
    }

    @Test
    public void testDeleteNodeWithOneChild() {
        tree.add(new Product(4L, "Product 4", 400.0, null));
        tree.add(new Product(5L, "Product 5", 500.0, null));
        assertTrue(tree.delete(new Product(4L, "Product 4", 400.0, null)));
        assertFalse(tree.containsNode(new Product(4L, "Product 4", 400.0, null)));
        assertTrue(tree.containsNode(new Product(5L, "Product 5", 500.0, null)));
    }

    @Test
    public void testDeleteNodeWithTwoChildren() {
        assertTrue(tree.delete(new Product(2L, "Product 2", 200.0, null)));
        assertFalse(tree.containsNode(new Product(2L, "Product 2", 200.0, null)));
    }

    @Test
    public void testToListOfIds() {
        List<Long> ids = tree.toListOfIds();
        assertEquals(3, ids.size());
        assertTrue(ids.contains(1L));
        assertTrue(ids.contains(2L));
        assertTrue(ids.contains(3L));
    }

    @Test
    public void testToJson() {
        String json = tree.toJson();
        assertNotNull(json);
        assertTrue(json.contains("\"data\":{\"id\":1,\"name\":\"Product 1\"}"));
        assertTrue(json.contains("\"data\":{\"id\":2,\"name\":\"Product 2\"}"));
        assertTrue(json.contains("\"data\":{\"id\":3,\"name\":\"Product 3\"}"));
    }
}
