package com.khamzin.inventoryservice.service;

import com.khamzin.inventoryservice.dto.InventoryRequestDto;
import com.khamzin.inventoryservice.model.Inventory;
import com.khamzin.inventoryservice.repository.InventoryRepository;
import com.khamzin.inventoryservice.util.InventoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Transactional
    public void addInventory(InventoryRequestDto inventoryRequestDto) {
        String skuCode = inventoryRequestDto.getSkuCode();
        Optional<Inventory> maybeInventory = inventoryRepository.findBySkuCode(skuCode);
        if (maybeInventory.isPresent()) {
            Inventory inventory = maybeInventory.get();
            Integer quantityToAdd = inventoryRequestDto.getQuantity();
            Integer oldQuantity = inventory.getQuantity();
            inventory.setQuantity(oldQuantity + quantityToAdd);
        } else {
            Inventory inventory = inventoryMapper.convertToInventory(inventoryRequestDto);
            inventoryRepository.save(inventory);
        }
    }

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

}
