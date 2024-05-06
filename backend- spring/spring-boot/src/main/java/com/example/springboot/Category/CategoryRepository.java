package com.example.springboot.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends MongoRepository<Category, String> {

    // CRUD - Create & Update
    Category save(Category category);


    // CRUD - Read
    List<Category> findAll();
    Optional<Category> findById(int id);
    @Query("{'name': ?0}")
    Optional<Category> getCategoryByName(String name);
    @Query("{'isParentCategory': true}")
    List<Category> getAllParentCategories();
    @Query("{'parentId': ?0}")
    List<Category> getAllSubCategoriesOfParentCategory(Integer parentId);
    @Query("{'id': {'$in': ?0}}")
    List<Category> getCategoriesFromList(@Param("ids") List<Integer> ids);


    // CRUD - Delete
    void deleteById(int id);
}
