package com.khamzin.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khamzin.productservice.util.TestContainersStarterUtil;
import com.khamzin.productservice.dto.ProductRequestDto;
import com.khamzin.productservice.dto.ProductResponseDto;
import com.khamzin.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ProductControllerTest {

    @BeforeAll
    static void beforeAll() {
        TestContainersStarterUtil.start();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequestDto productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnProducts() throws Exception {
        ProductResponseDto productResponse = getProduct();

        when(productService.getAllProducts()).thenReturn(List.of(productResponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", containsStringIgnoringCase("some id")))
                .andExpect(jsonPath("$[0].description").value("sasung"));
    }

    private ProductResponseDto getProduct() {
        return ProductResponseDto.builder()
                .id("Some id")
                .name("Samsung Galaxy SXVS")
                .description("sasung")
                .price(BigDecimal.valueOf(500))
                .build();
    }

    private ProductRequestDto getProductRequest() {
        return ProductRequestDto.builder()
                .name("Iphone 13")
                .description("iphone 13")
                .price(BigDecimal.valueOf(1200))
                .build();
    }
}