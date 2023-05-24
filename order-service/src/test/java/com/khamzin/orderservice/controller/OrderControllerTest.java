package com.khamzin.orderservice.controller;

import com.khamzin.orderservice.service.OrderService;
import com.khamzin.orderservice.util.TestContainerStarter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@MockBean(OrderService.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        TestContainerStarter.start();
    }

    @Test
    void shouldPlaceOrder() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "orderLineItemsDtoList":[
                                        {
                                        "skuCode": "iphone_12",
                                        "price": 800,
                                        "quantity": 15
                                        }
                                    ]
                                }"""))
                .andExpect(status().isCreated());
    }
}