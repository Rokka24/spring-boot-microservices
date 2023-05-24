package com.khamzin.inventoryservice.controller;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
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

        when(inventoryService.isInStock(skuCode)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/" + skuCode))
                .andExpect(status().isOk())
                .andReturn();
        String expectedResponse = mvcResult.getResponse().getContentAsString();
        assertThat(expectedResponse).isEqualTo("true");
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
