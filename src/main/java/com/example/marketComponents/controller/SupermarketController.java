package com.example.marketComponents.controller;

import com.example.annotation.AdminSecured;
import com.example.marketComponents.controller.util.ProductDto;
import com.example.marketComponents.dto.Coordinates;
import com.example.marketComponents.dto.SupermarketUserList;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Product;
import com.example.marketComponents.model.Supermarket;
import com.example.marketComponents.model.SupermarketProduct;
import com.example.marketComponents.repository.SupermarketProductRepository;
import com.example.marketComponents.service.SupermarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/supermarkets")
public class SupermarketController {
    private final SupermarketService supermarketService;
    private final SupermarketProductRepository supermarketProductRepository;
    private static final Logger log = LoggerFactory.getLogger (SupermarketController.class);

    public SupermarketController(SupermarketService supermarketService, SupermarketProductRepository supermarketProductRepository) {
        this.supermarketService = supermarketService;
        this.supermarketProductRepository = supermarketProductRepository;
    }

    @GetMapping
    public ResponseEntity<List<Supermarket>> getAllSupermarkets() {
        List<Supermarket> supermarkets = supermarketService.getAllSupermarkets ();
        return new ResponseEntity<> (supermarkets, HttpStatus.OK);
    }

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
    public ResponseEntity<Void> addProductFromSupermarket(@PathVariable Long supermarketId, @PathVariable Long productId, @RequestBody ProductDto productDto) {
        supermarketService.addProductFromSupermarket (productId, supermarketId, productDto);
        return new ResponseEntity<> (HttpStatus.NO_CONTENT);
    }

    @PostMapping("/near/{radius}")
    public ResponseEntity<List<Supermarket>> getNearSupermarkets(@RequestBody Coordinates userCo, @PathVariable double radius) {
        return ResponseEntity.ok (supermarketService.searchInTheArea (userCo, radius));
    }

    @PostMapping("/near-with-items/{radius}")
    public ResponseEntity<List<SupermarketUserList>> getNearSupermarketsContainingList(
                                                                                       @PathVariable double radius,
                                                                                       @RequestBody Map<String, Object> requestBody) {
        return ResponseEntity.ok (supermarketService.searchInAreaContainingProducts (radius, requestBody));
    }
}
