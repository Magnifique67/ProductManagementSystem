package com.Lab4.ProductManagementSystem.util;


import org.springframework.core.convert.converter.Converter;
import com.Lab4.ProductManagementSystem.entity.Product;

public class StringToProductConverter implements Converter<String, Product> {

    @Override
    public Product convert(String source) {
        Product product = new Product();
        product.setName(source);
        return product;
    }
}