package com.example.itemComponents.controller;

import com.example.itemComponents.exception.ResourceNotFoundException;
import com.example.itemComponents.model.Product;
import com.example.itemComponents.service.ProductService;
import com.example.annotation.AdminSecured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @AdminSecured
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @AdminSecured
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok (productService.getProductById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status (418).build ();
        }
    }
    @AdminSecured
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
    @AdminSecured
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok (productService.updateProduct(id, product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status (418).build ();
        }
    }
    @AdminSecured
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException (e);
        }
    }
}
