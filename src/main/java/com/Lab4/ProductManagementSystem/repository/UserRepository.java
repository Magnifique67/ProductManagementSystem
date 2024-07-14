package com.Lab4.ProductManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Lab4.ProductManagementSystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}