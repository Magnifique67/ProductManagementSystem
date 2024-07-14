package com.Lab4.ProductManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Lab4.ProductManagementSystem.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
