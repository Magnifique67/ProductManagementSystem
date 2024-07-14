package com.Lab4.ProductManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private boolean success;
    private String message;
    private Object data;

    // No need to manually define constructors, getters, setters, toString, etc.
}
