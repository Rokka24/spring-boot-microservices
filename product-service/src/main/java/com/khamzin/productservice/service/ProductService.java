package com.khamzin.productservice.service;

import com.khamzin.productservice.dto.ProductRequestDto;
import com.khamzin.productservice.dto.ProductResponseDto;
import com.khamzin.productservice.model.Product;
import com.khamzin.productservice.repository.ProductRepository;
import com.khamzin.productservice.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public void createProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.convertToProduct(productRequestDto);

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(productMapper::convertToProductResponseDto)
                .toList();
    }
}
