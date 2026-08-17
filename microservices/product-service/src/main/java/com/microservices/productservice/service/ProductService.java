package com.microservices.productservice.service;

import com.microservices.productservice.client.CategoryClient;
import com.microservices.productservice.dto.CategoryResponse;
import com.microservices.productservice.entity.Product;
import com.microservices.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final CategoryClient categoryClient;
    private final ProductRepository productRepository;

    public ProductService(CategoryClient categoryClient, ProductRepository productRepository) {
        this.categoryClient = categoryClient;
        this.productRepository = productRepository;
    }

    // Create product
    public Product createProduct(Product product) {
        CategoryResponse category =
                categoryClient.getCategoryById(
                        product.getCategoryId()
                );

        if (category == null) {
            throw new RuntimeException(
                    "Category not found: "
                            + product.getCategoryId()
            );
        }
        return productRepository.save(product);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with id: " + id
                        ));
    }

    // Get products by category
    public List<Product> getProductsByCategory(Long categoryId) {

        return productRepository.findByCategoryId(categoryId);
    }

    // Search products by name
    public List<Product> searchProducts(String name) {

        return productRepository
                .findByNameContainingIgnoreCase(name);
    }

    // Update product
    public Product updateProduct(
            Long id,
            Product product) {

        Product existingProduct =
                getProductById(id);

        existingProduct.setName(product.getName());
        existingProduct.setDescription(
                product.getDescription()
        );
        existingProduct.setPrice(
                product.getPrice()
        );
        existingProduct.setQuantity(
                product.getQuantity()
        );
        existingProduct.setCategoryId(
                product.getCategoryId()
        );

        return productRepository.save(existingProduct);
    }

    // Delete product
    public void deleteProduct(Long id) {

        Product existingProduct =
                getProductById(id);

        productRepository.delete(existingProduct);
    }
}