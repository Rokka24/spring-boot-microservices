package com.khamzin.productservice.util;

import com.khamzin.productservice.dto.ProductRequestDto;
import com.khamzin.productservice.dto.ProductResponseDto;
import com.khamzin.productservice.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ModelMapper modelMapper;

    public Product convertToProduct(ProductResponseDto productResponseDto) {
        return modelMapper.map(productResponseDto, Product.class);
    }

    public Product convertToProduct(ProductRequestDto productRequestDto) {
        return modelMapper.map(productRequestDto, Product.class);
    }

    public ProductResponseDto convertToProductResponseDto(Product product) {
        return modelMapper.map(product, ProductResponseDto.class);
    }

    public ProductRequestDto convertToProductRequestDto(Product product) {
        return modelMapper.map(product, ProductRequestDto.class);
    }
}
