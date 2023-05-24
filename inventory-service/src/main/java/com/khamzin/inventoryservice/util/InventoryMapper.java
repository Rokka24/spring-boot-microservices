package com.khamzin.inventoryservice.util;

import com.khamzin.inventoryservice.dto.InventoryRequestDto;
import com.khamzin.inventoryservice.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryMapper {

    private final ModelMapper modelMapper;

    public Inventory convertToInventory(InventoryRequestDto inventoryRequestDto) {
        return modelMapper.map(inventoryRequestDto, Inventory.class);
    }

    public InventoryRequestDto convertToRequestDto(Inventory inventory) {
        return modelMapper.map(inventory, InventoryRequestDto.class);
    }
}
