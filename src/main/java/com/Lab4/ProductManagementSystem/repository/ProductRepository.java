
package com.Lab4.ProductManagementSystem.repository;

import com.Lab4.ProductManagementSystem.entity.Category;
import com.Lab4.ProductManagementSystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface ProductRepository extends JpaRepository<Product, Long> {
        List<Product> findByCategory(Category category);
    }

