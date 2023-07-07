package com.example.marketComponents.dto;

import com.example.marketComponents.model.Product;
import com.example.marketComponents.model.ProductPrice;
import com.example.marketComponents.model.Supermarket;
import com.example.marketComponents.model.SupermarketProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupermarketUserList {
    Supermarket supermarket;
    List<ProductPrice> contains;
    List<Product> dontContains;
    double containPercentage;
    double total;
    int originalItemsSize;
    int containingSize;
}
