package com.example.marketComponents.service;

import com.example.marketComponents.model.SupermarketProduct;
import com.example.marketComponents.repository.SupermarketProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupermarketProductService {

    private final SupermarketProductRepository supermarketProductRepository;

    public SupermarketProductService(SupermarketProductRepository supermarketProductRepository) {
        this.supermarketProductRepository = supermarketProductRepository;
    }

    public List<SupermarketProduct> getAll() {
        return supermarketProductRepository.findAll();
    }

    public Optional<SupermarketProduct> getById(Long id) {
        return supermarketProductRepository.findById(id);
    }

    public SupermarketProduct save(SupermarketProduct supermarketProduct) {
        return supermarketProductRepository.save(supermarketProduct);
    }

    public void deleteById(Long id) {
        supermarketProductRepository.deleteById(id);
    }
    public void deleteById(SupermarketProduct supermarketProduct) {
        supermarketProductRepository.delete (supermarketProduct);
    }
}