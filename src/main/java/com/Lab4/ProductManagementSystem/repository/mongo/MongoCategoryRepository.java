package com.Lab4.ProductManagementSystem.repository.mongo;

import com.Lab4.ProductManagementSystem.entity.mongo.MongoCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoCategoryRepository extends MongoRepository<MongoCategory, String> {
}