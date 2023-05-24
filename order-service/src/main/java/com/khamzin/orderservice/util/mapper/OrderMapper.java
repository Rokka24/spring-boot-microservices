package com.khamzin.orderservice.util.mapper;

import com.khamzin.orderservice.dto.OrderRequestDto;
import com.khamzin.orderservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderRequestDto convertOrderToOrderRequest(Order order) {
        return modelMapper.map(order, OrderRequestDto.class);
    }

    public Order convertOrderRequestToOrder(OrderRequestDto orderRequestDto) {
        return modelMapper.map(orderRequestDto, Order.class);
    }

}
