package com.example.marketComponents.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPrice {
    private Long id;
    private Product product;
    private Double price;
    private Integer stock;
}
