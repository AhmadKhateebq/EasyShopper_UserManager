package com.example.itemComponents.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Double price;
    private Integer stock;
}
