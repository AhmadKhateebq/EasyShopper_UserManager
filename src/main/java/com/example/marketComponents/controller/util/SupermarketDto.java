package com.example.marketComponents.controller.util;

import com.example.marketComponents.model.Supermarket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupermarketDto {
    private Long id;
    private String name;
    private String locationX;
    private String locationY;
    public static SupermarketDto mapToDto(Supermarket supermarket) {
        SupermarketDto dto = new SupermarketDto();
        dto.setId(supermarket.getId());
        dto.setName(supermarket.getName());
        dto.setLocationX(supermarket.getLocationX());
        dto.setLocationY(supermarket.getLocationY());
        return dto;
    }
}
