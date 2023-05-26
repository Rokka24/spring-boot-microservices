package com.khamzin.orderservice.service;

import com.khamzin.orderservice.dto.inventory.InventoryResponseDto;
import com.khamzin.orderservice.dto.order.OrderRequestDto;
import com.khamzin.orderservice.model.Order;
import com.khamzin.orderservice.model.OrderLineItems;
import com.khamzin.orderservice.repository.OrderRepository;
import com.khamzin.orderservice.util.exception.InventoryNotFoundException;
import com.khamzin.orderservice.util.mapper.OrderLineItemsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderLineItemsMapper orderLineItemsMapper;
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Transactional
    public void placeOrder(OrderRequestDto orderRequestDto) {
        List<OrderLineItems> orderLineItems = orderRequestDto.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsMapper::convertDtoToOrderLineItems)
                .toList();

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(orderLineItems)
                .build();

        orderLineItems.forEach(items -> items.setOrder(order));

        InventoryResponseDto[] inventoryResponseArray = getInventoryResponses(orderLineItems);

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponseDto::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new InventoryNotFoundException("Product run out.");
        }
    }

    private InventoryResponseDto[] getInventoryResponses(List<OrderLineItems> orderLineItems) {
        List<String> skuCodes = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        return webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new InventoryNotFoundException("Request has item that doesn't exist in inventory")))
                .bodyToMono(InventoryResponseDto[].class)
                .block();
    }
}
