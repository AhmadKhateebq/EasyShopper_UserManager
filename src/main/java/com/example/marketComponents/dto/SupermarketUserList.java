package com.example.marketComponents.dto;

import com.example.marketComponents.model.Product;
import com.example.marketComponents.model.Supermarket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupermarketUserList {
    Supermarket supermarket;
    List<Product> contains;
    List<Product> dontContains;
    double containPercentage;
    int originalItemsSize;
    int containingSize;
}
