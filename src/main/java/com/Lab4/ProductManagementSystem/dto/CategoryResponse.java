package com.Lab4.ProductManagementSystem.dto;

import com.Lab4.ProductManagementSystem.entity.Category;
import java.util.List;

public class CategoryResponse {

    private boolean success;
    private String message;
    private Category category;
    private List<Category> categories;

    // Constructor for a single Category
    public CategoryResponse(boolean success, String message, Category category) {
        this.success = success;
        this.message = message;
        this.category = category;
    }

    // Constructor for a list of Categories
    public CategoryResponse(boolean success, String message, List<Category> categories) {
        this.success = success;
        this.message = message;
        this.categories = categories;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
