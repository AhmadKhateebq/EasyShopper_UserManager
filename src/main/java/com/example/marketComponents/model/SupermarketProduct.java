package com.example.marketComponents.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supermarkets_products")
public class SupermarketProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnoreProperties("products")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supermarket_id", nullable = false)
    private Supermarket supermarket;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;

}
