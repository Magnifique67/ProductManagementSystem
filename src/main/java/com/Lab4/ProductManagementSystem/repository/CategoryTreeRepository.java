package com.Lab4.ProductManagementSystem.repository;
import com.Lab4.ProductManagementSystem.entity.CategoryTree;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface CategoryTreeRepository extends MongoRepository<CategoryTree, String> {
    Optional<CategoryTree> findByCategoryId(Long categoryId);
    void deleteByCategoryId(Long categoryId);
}