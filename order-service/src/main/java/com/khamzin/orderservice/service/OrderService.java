package com.khamzin.orderservice.service;

import com.khamzin.orderservice.dto.OrderRequestDto;
import com.khamzin.orderservice.model.Order;
import com.khamzin.orderservice.model.OrderLineItems;
import com.khamzin.orderservice.repository.OrderRepository;
import com.khamzin.orderservice.util.mapper.OrderLineItemsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderLineItemsMapper orderLineItemsMapper;
    private final OrderRepository orderRepository;

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

        orderLineItems.forEach(item -> item.setOrder(order));

        orderRepository.save(order);
    }
}
