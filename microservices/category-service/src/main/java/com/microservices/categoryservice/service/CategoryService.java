package com.microservices.categoryservice.service;

import com.microservices.categoryservice.entity.Category;
import com.microservices.categoryservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Create category
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found with id: " + id));
    }

    // Get category by name
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() ->
                        new RuntimeException("Category not found with name: " + name));
    }

    // Update category
    public Category updateCategory(Long id, Category category) {

        Category existingCategory = getCategoryById(id);

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        return categoryRepository.save(existingCategory);
    }

    // Delete category
    public void deleteCategory(Long id) {

        Category existingCategory = getCategoryById(id);

        categoryRepository.delete(existingCategory);
    }
}