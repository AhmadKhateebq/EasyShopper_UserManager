package com.example.itemComponents.service;

import com.example.itemComponents.exception.ResourceNotFoundException;
import com.example.itemComponents.model.Product;
import com.example.itemComponents.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id)throws ResourceNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException ("Product", "id", id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct)throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        product.setName(updatedProduct.getName());
        product.setBrand(updatedProduct.getBrand());
        product.setCategory(updatedProduct.getCategory());
        product.setDescription(updatedProduct.getDescription());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id)throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        productRepository.delete(product);
    }
}
