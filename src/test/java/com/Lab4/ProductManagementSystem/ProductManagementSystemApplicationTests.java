package com.Lab4.ProductManagementSystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductManagementSystemApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		// Verify that the application context loads successfully
		assertNotNull(context);

	}
}
