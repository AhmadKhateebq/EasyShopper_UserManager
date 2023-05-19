package com.example.marketComponents.repository;

import com.example.marketComponents.model.SupermarketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupermarketProductRepository extends JpaRepository<SupermarketProduct, Long> {
    List<SupermarketProduct> getSupermarketProductBySupermarketId(Long id);
}