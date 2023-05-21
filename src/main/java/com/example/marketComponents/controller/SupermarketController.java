package com.example.marketComponents.controller;

import com.example.annotation.AdminSecured;
import com.example.marketComponents.controller.util.ProductDto;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Supermarket;
import com.example.marketComponents.model.SupermarketProduct;
import com.example.marketComponents.repository.SupermarketProductRepository;
import com.example.marketComponents.service.SupermarketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supermarkets")
public class SupermarketController {

    private final SupermarketService supermarketService;
    private final SupermarketProductRepository supermarketProductRepository;

    public SupermarketController(SupermarketService supermarketService, SupermarketProductRepository supermarketProductRepository) {
        this.supermarketService = supermarketService;
        this.supermarketProductRepository = supermarketProductRepository;
    }

    @AdminSecured
    @GetMapping
    public ResponseEntity<List<Supermarket>> getAllSupermarkets() {
        List<Supermarket> supermarkets = supermarketService.getAllSupermarkets ();
        return new ResponseEntity<> (supermarkets, HttpStatus.OK);
    }

    @AdminSecured
    @GetMapping("/{id}")
    public ResponseEntity<Supermarket> getSupermarketById(@PathVariable Long id) {
        Supermarket supermarket;
        try {
            supermarket = supermarketService.getSupermarketById (id);
            return new ResponseEntity<> (supermarket, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status (418).build ();
        }
    }

    @AdminSecured
    @GetMapping("/{id}/products")
    public ResponseEntity<List<SupermarketProduct>> getSupermarketProductsById(@PathVariable Long id) {
        Supermarket supermarket;
        try {
            supermarket = supermarketService.getSupermarketById (id);
            return new ResponseEntity<> (supermarketProductRepository.getSupermarketProductBySupermarketId (supermarket.getId ()), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status (418).build ();
        }
    }

    @AdminSecured
    @PostMapping
    public ResponseEntity<Supermarket> createSupermarket(@RequestBody Supermarket supermarket) {
        Supermarket createdSupermarket = supermarketService.createSupermarket (supermarket);
        return new ResponseEntity<> (createdSupermarket, HttpStatus.CREATED);
    }

    @AdminSecured
    @PutMapping("/{id}")
    public ResponseEntity<Supermarket> updateSupermarket(@PathVariable Long id, @RequestBody Supermarket supermarket) {
        Supermarket updatedSupermarket;
        try {
            updatedSupermarket = supermarketService.updateSupermarket (id, supermarket);
            return new ResponseEntity<> (updatedSupermarket, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status (418).build ();
        }

    }

    @AdminSecured
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupermarket(@PathVariable Long id) {
        try {
            supermarketService.deleteSupermarket (id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException (e);
        }
        return new ResponseEntity<> (HttpStatus.NO_CONTENT);
    }

    @AdminSecured
    @DeleteMapping("/{supermarketId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromSupermarket(@PathVariable Long supermarketId, @PathVariable Long productId) {
        try {
            supermarketService.removeProductFromSupermarket (productId, supermarketId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status (418).build ();
        }
        return new ResponseEntity<> (HttpStatus.NO_CONTENT);
    }

    @AdminSecured
    @PostMapping("/{supermarketId}/products/{productId}")
    public ResponseEntity<Void> addProductFromSupermarket(@PathVariable Long supermarketId, @PathVariable Long productId,@RequestBody ProductDto productDto) {
        supermarketService.addProductFromSupermarket (productId, supermarketId,productDto);
        return new ResponseEntity<> (HttpStatus.NO_CONTENT);
    }
}
