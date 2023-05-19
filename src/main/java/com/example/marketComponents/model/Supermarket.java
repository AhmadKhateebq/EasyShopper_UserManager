package com.example.marketComponents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "supermarkets_products",
//            joinColumns = @JoinColumn(name = "supermarket_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id"))
//    @JsonIgnoreProperties("supermarket")
//    private Set<SupermarketProduct> products = new HashSet<> ();
}
