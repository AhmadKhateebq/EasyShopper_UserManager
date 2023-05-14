package com.example.itemComponents.service;

import com.example.itemComponents.controller.util.SupermarketDto;
import com.example.itemComponents.exception.ResourceNotFoundException;
import com.example.itemComponents.model.Product;
import com.example.itemComponents.model.Supermarket;
import com.example.itemComponents.repository.ProductRepository;
import com.example.itemComponents.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupermarketService {
    @Autowired
    SupermarketRepository supermarketRepository;
    @Autowired
    ProductRepository productRepository;

    public Supermarket createSupermarket(Supermarket supermarket) {
        return supermarketRepository.save (supermarket);
    }

    public List<SupermarketDto> getAllSupermarkets() {
        return supermarketRepository.findAll ().stream ().map (SupermarketDto::mapToDto).collect(Collectors.toList());
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
        supermarket.setProducts (supermarketDetails.getProducts ());
        return supermarketRepository.save (supermarket);
    }

    public void deleteSupermarket(Long id) throws ResourceNotFoundException {
        Supermarket supermarket = getSupermarketById (id);
        supermarketRepository.delete (supermarket);
    }
    public void removeProductFromSupermarket(Long productId, Long supermarketId)throws EntityNotFoundException {
        Supermarket supermarket = supermarketRepository.findById(supermarketId)
                .orElseThrow(() -> new EntityNotFoundException ("Supermarket not found with id: " + supermarketId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        if (!supermarket.getProducts().contains(product)) {
            throw new EntityNotFoundException("Product not found in the specified supermarket");
        }

        supermarket.getProducts().remove(product);
        supermarketRepository.save(supermarket);
    }
    public void addProductFromSupermarket(Long productId, Long supermarketId)throws EntityNotFoundException {
        Supermarket supermarket = supermarketRepository.findById(supermarketId)
                .orElseThrow(() -> new EntityNotFoundException ("Supermarket not found with id: " + supermarketId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        if (supermarket.getProducts().contains(product)) {
            throw new EntityNotFoundException("Product already found in the specified supermarket");
        }

        supermarket.getProducts().add (product);
        supermarketRepository.save(supermarket);
    }
}
