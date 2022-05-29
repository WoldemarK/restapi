package com.example.restapi.mapper;

import com.example.restapi.dto.ProductDTO;
import com.example.restapi.model.Product;

public class ProductMapper {

    public static Product DtoToEntity(ProductDTO productDTO) {
        return new Product().setName(productDTO.getName())
                .setPrice(productDTO.getPrice());
    }
    public static ProductDTO EntityToDto(Product productDTO) {
        return new ProductDTO().setName(productDTO.getName())
                .setPrice(productDTO.getPrice());
    }
}
