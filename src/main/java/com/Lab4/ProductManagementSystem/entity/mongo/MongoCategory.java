package com.Lab4.ProductManagementSystem.entity.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "categories")
@Data
public class MongoCategory {

    @Id
    private String id;
    private String name;
    private List<MongoProduct> products;
}