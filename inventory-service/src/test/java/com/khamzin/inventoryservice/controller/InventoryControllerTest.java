package com.khamzin.inventoryservice.controller;

import com.khamzin.inventoryservice.dto.InventoryResponseDto;
import com.khamzin.inventoryservice.service.InventoryService;
import com.khamzin.inventoryservice.util.TestContainerStarter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class InventoryControllerTest {

    @BeforeAll
    static void beforeAll() {
        TestContainerStarter.start();
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Test
    void shouldCheckAvailability() throws Exception {
        String skuCode = "dummy";
        InventoryResponseDto expectedValueInServiceMock = InventoryResponseDto.builder()
                .skuCode(skuCode)
                .isInStock(true)
                .build();

        when(inventoryService.isInStock(List.of(skuCode))).thenReturn(List.of(expectedValueInServiceMock));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory?skuCode=" + skuCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].skuCode", containsStringIgnoringCase(expectedValueInServiceMock.getSkuCode())))
                .andExpect(jsonPath("$[0].inStock").value(true));
    }

    @Test
    void shouldAddInventory() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "skuCode": "Laptop_Asus",
                                    "quantity": 100
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn();

        String expectedResponse = mvcResult.getResponse().getContentAsString();

        assertThat(expectedResponse).isEqualTo("New inventory added");
    }
}
