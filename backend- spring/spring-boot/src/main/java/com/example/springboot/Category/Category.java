package com.example.springboot.Category;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.util.Pair;


import java.util.List;

@Document(collection = "Category")
public class Category {

    @MongoId
    private Integer id;
    private String name;
    private boolean isParentCategory;
    private List<Pair<Integer, String>> subcategories;
    private Integer parentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isParentCategory() {
        return isParentCategory;
    }

    public void setParentCategory(boolean parentCategory) {
        isParentCategory = parentCategory;
    }

    public List<Pair<Integer, String>> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Pair<Integer, String>> subcategories) {
        this.subcategories = subcategories;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
