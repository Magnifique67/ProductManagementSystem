package com.Lab4.ProductManagementSystem.entity.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Data
public class MongoProduct {

    @Id
    private String id;
    private String name;
    private double price;
    private String categoryId;

}