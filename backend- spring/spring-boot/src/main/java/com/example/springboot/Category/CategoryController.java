package com.example.springboot.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;


    // CRUD - Create
    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        Optional<Category> existingCategory = categoryRepository.getCategoryByName(category.getName());
        if (existingCategory.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Category with the same name already exists");
        }
        category.setParentCategory(true);
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }
    @PostMapping("/addSubCategory")
    public ResponseEntity<?> addSubCategory(@RequestBody Category subcategory) {
        Optional<Category> existingCategory = categoryRepository.findById(subcategory.getParentId());
        if (existingCategory.isPresent()) {
            Optional<Category> existingCategory2 = categoryRepository.getCategoryByName(subcategory.getName());
            if (existingCategory2.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Category with the same name already exists");
            }
            Category parentCategory = existingCategory.get();
            List<Pair<Integer, String>> tempList = parentCategory.getSubcategories();
            Pair<Integer, String> tempPair = Pair.of(subcategory.getId(), subcategory.getName());
            tempList.add(tempPair);
            parentCategory.setSubcategories(tempList);
            categoryRepository.save(parentCategory);

            Category savedCategory = categoryRepository.save(subcategory);
            return ResponseEntity.ok(savedCategory);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Cannot find parent category of such id");
    }


    // CRUD - Read
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/categories/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name) {
        Optional<Category> existingCategory = categoryRepository.getCategoryByName(name);
        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            return ResponseEntity.ok(category);
        }
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body("Cannot find category with given name");
    }
    @GetMapping("/categories/list")
    public List<Category> getCategoriesFromList(List<Integer> ids) {
        return categoryRepository.getCategoriesFromList(ids);
    }
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable int categoryId) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isPresent()) {
            return ResponseEntity.ok(existingCategory.get());
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Cannot find category with given id");
    }
    @GetMapping("/unauthorized/categories/parentCategories")
    public ResponseEntity<List<Category>> getParentCategories() {
        return ResponseEntity.ok(categoryRepository.getAllParentCategories());
    }
    @GetMapping("categories/{categoryId}/subcategories")
    public ResponseEntity<List<Category>> getSubcategories(@PathVariable int categoryId) {
        List<Category> x = categoryRepository.getAllSubCategoriesOfParentCategory(categoryId);
        return ResponseEntity.ok(x);
    }


    // CRUD - Update
    @PutMapping("/categories/{categoryId}")
    public Category updateCategory(@PathVariable int categoryId, @RequestBody Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isPresent()) {
            Category tempCategory = existingCategory.get();
            tempCategory.setName(category.getName());
            tempCategory.setParentCategory(category.isParentCategory());
            tempCategory.setSubcategories(category.getSubcategories());
            tempCategory.setParentId(category.getParentId());

            return categoryRepository.save(tempCategory);
        }
        return null;
    }


    // CRUD - Delete
    @DeleteMapping("/categories/{categoryId}")
    public boolean deleteUser(@PathVariable int categoryId) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isPresent()) {
            categoryRepository.deleteById(categoryId);
            return true;
        }
        return false;
    }
}
