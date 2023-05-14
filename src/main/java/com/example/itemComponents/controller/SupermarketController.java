package com.example.itemComponents.controller;

import com.example.annotation.AdminSecured;
import com.example.itemComponents.controller.util.SupermarketDto;
import com.example.itemComponents.exception.ResourceNotFoundException;
import com.example.itemComponents.model.Product;
import com.example.itemComponents.model.Supermarket;
import com.example.itemComponents.service.SupermarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/supermarkets")
public class SupermarketController {

    @Autowired
    private SupermarketService supermarketService;

    @AdminSecured
    @GetMapping
    public ResponseEntity<List<SupermarketDto>> getAllSupermarkets() {
        List<SupermarketDto> supermarkets = supermarketService.getAllSupermarkets ();
        return new ResponseEntity<> (supermarkets, HttpStatus.OK);
    }

    @AdminSecured
    @GetMapping("/all")
    public ResponseEntity<List<Supermarket>> getAllSupermarketsWithProducts() {
        List<Supermarket> supermarkets = supermarketService.getAllSupermarketsWithProducts ();
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
    public ResponseEntity<List<Product>> getSupermarketProductsById(@PathVariable Long id) {
        Supermarket supermarket;
        try {
            supermarket = supermarketService.getSupermarketById (id);
            return new ResponseEntity<> (supermarket.getProducts ().stream ().toList (), HttpStatus.OK);
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
        supermarketService.removeProductFromSupermarket (productId, supermarketId);
        return new ResponseEntity<> (HttpStatus.NO_CONTENT);
    }

    @AdminSecured
    @PostMapping("/{supermarketId}/products/{productId}")
    public ResponseEntity<Void> addProductFromSupermarket(@PathVariable Long supermarketId, @PathVariable Long productId) {
        supermarketService.addProductFromSupermarket (productId, supermarketId);
        return new ResponseEntity<> (HttpStatus.NO_CONTENT);
    }
}
