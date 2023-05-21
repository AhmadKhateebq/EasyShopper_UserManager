package com.example.marketComponents.service;

import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Product;
import com.example.marketComponents.repository.ProductRepository;
import com.example.marketComponents.repository.SupermarketProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final SupermarketProductRepository supermarketProductRepository;

    public ProductService(ProductRepository productRepository, SupermarketProductRepository supermarketProductRepository) {
        this.productRepository = productRepository;
        this.supermarketProductRepository = supermarketProductRepository;
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
        supermarketProductRepository.findAll ().stream ()
                        .filter (s->s.getProduct ().equals (product)).toList ()
                                .forEach (s->supermarketProductRepository.deleteById (s.getId ()));
        productRepository.delete(product);
    }
}
