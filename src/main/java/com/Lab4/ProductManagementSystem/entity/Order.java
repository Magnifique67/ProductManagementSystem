package com.Lab4.ProductManagementSystem.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime orderDate;
    private String status;
}
