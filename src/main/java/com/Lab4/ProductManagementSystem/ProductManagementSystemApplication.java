package com.Lab4.ProductManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ProductManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProductManagementSystemApplication.class, args);
	}
}
