package com.example.marketComponents.service;

import com.example.marketComponents.controller.util.ProductDto;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Product;
import com.example.marketComponents.model.Supermarket;
import com.example.marketComponents.model.SupermarketProduct;
import com.example.marketComponents.repository.ProductRepository;
import com.example.marketComponents.repository.SupermarketRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SupermarketService {
    final
    SupermarketRepository supermarketRepository;
    final
    SupermarketProductService supermarketProductService;
    final
    ProductRepository productRepository;

    public SupermarketService(SupermarketRepository supermarketRepository, SupermarketProductService supermarketProductService, ProductRepository productRepository) {
        this.supermarketRepository = supermarketRepository;
        this.supermarketProductService = supermarketProductService;
        this.productRepository = productRepository;
    }

    public Supermarket createSupermarket(Supermarket supermarket) {
        return supermarketRepository.save (supermarket);
    }

    public List<Supermarket> getAllSupermarkets() {
        return supermarketRepository.findAll ();
    }
    public List<SupermarketProduct> getAllSupermarketspr() {
        return supermarketProductService.getAll ();
    }

    public List<Supermarket> getAllSupermarketsWithProducts() {
        return supermarketRepository.findAll ();
    }

    public Supermarket getSupermarketById(Long id) throws ResourceNotFoundException {
        return supermarketRepository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException ("Supermarket", "id", id));
    }

    public Supermarket updateSupermarket(Long id, Supermarket supermarketDetails) throws ResourceNotFoundException {
        Supermarket supermarket = getSupermarketById (id);
        supermarket.setName (supermarketDetails.getName ());
        supermarket.setLocationX (supermarketDetails.getLocationX ());
        supermarket.setLocationY (supermarketDetails.getLocationY ());
        return supermarketRepository.save (supermarket);
    }

    public void deleteSupermarket(Long id) throws ResourceNotFoundException {
        Supermarket supermarket = getSupermarketById (id);
        supermarketRepository.delete (supermarket);
    }

    public void removeProductFromSupermarket(Long productId, Long supermarketId) throws EntityNotFoundException {
        Supermarket supermarket = supermarketRepository.findById (supermarketId)
                .orElseThrow (() -> new EntityNotFoundException ("Supermarket not found with id: " + supermarketId));

        SupermarketProduct product = supermarketProductService.getById (productId)
                .orElseThrow (() -> new EntityNotFoundException ("Product not found with id: " + productId));

        supermarketRepository.save (supermarket);
    }

    public void addProductFromSupermarket(Long productId, Long supermarketId, ProductDto productDto) throws EntityNotFoundException {
        Supermarket supermarket = supermarketRepository.findById (supermarketId)
                .orElseThrow (() -> new EntityNotFoundException ("Supermarket not found with id: " + supermarketId));

        Product product = productRepository.findById (productId)
                .orElseThrow (() -> new EntityNotFoundException ("Product not found with id: " + productId));
        SupermarketProduct supermarketProduct = new SupermarketProduct ();
        supermarketProduct.setProduct (product);
        supermarketProduct.setStock (productDto.getStock ());
        supermarketProduct.setPrice (productDto.getPrice ());
        supermarketProduct.setSupermarket (supermarket);
        supermarketProductService.save (supermarketProduct);
    }
}
