package com.Lab4.ProductManagementSystem.controller;

import com.Lab4.ProductManagementSystem.entity.Category;
import com.Lab4.ProductManagementSystem.entity.Product;
import com.Lab4.ProductManagementSystem.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Set<Product> productSet = new HashSet<>();
        Category category = new Category(1L, "Electronics", productSet);
        Product product1 = new Product(1L, "Phone", 699.99, category);
        Product product2 = new Product(2L, "Laptop", 999.99, category);

        List<Product> products = Arrays.asList(product1, product2);
        Mockito.when(productService.getAllProducts()).thenReturn(products);

        MvcResult result = mockMvc.perform(get("/api/products/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = "[{\"id\":1,\"name\":\"Phone\",\"price\":699.99,\"category\":{\"id\":1,\"name\":\"Electronics\"}},{\"id\":2,\"name\":\"Laptop\",\"price\":999.99,\"category\":{\"id\":1,\"name\":\"Electronics\"}}]";
        String actualJson = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
    }


    @Test
    public void testAddProduct() throws Exception {
        Product product = new Product(1L, "Phone", 699.99, new Category(1L, "Electronics", new HashSet<>()));
        Mockito.when(productService.saveProduct(Mockito.any(Product.class))).thenReturn(product);

        String productJson = "{\"id\":1,\"name\":\"Phone\",\"price\":699.99,\"category\":{\"id\":1,\"name\":\"Electronics\"}}";

        mockMvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andReturn();
    }



}
