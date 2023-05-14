package com.example.itemComponents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supermarkets")
public class Supermarket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String locationX;

    @Column(nullable = false)
    private String locationY;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "supermarkets_products",
            joinColumns = @JoinColumn(name = "supermarket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<> ();
}
