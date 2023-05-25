package com.khamzin.orderservice.util.mapper;

import com.khamzin.orderservice.dto.order.OrderLineItemsDto;
import com.khamzin.orderservice.model.OrderLineItems;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderLineItemsMapper {

    private final ModelMapper modelMapper;

    public OrderLineItemsDto convertOrderLineItemsToDto(OrderLineItems orderLineItems) {
        return modelMapper.map(orderLineItems, OrderLineItemsDto.class);
    }

    public OrderLineItems convertDtoToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        return modelMapper.map(orderLineItemsDto, OrderLineItems.class);
    }
}
