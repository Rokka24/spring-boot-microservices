package com.khamzin.inventoryservice.service;

import com.khamzin.inventoryservice.dto.InventoryRequestDto;
import com.khamzin.inventoryservice.dto.InventoryResponseDto;
import com.khamzin.inventoryservice.model.Inventory;
import com.khamzin.inventoryservice.repository.InventoryRepository;
import com.khamzin.inventoryservice.util.exception.InventoryNotFoundException;
import com.khamzin.inventoryservice.util.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Transactional
    public void addInventory(InventoryRequestDto inventoryRequestDto) {
        log.info("Adding inventory");
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
    public List<InventoryResponseDto> isInStock(List<String> skuCodes) {
        log.info("Checking inventory");
        List<InventoryResponseDto> inventoryResponses = inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .map(inventoryMapper::convertToResponseDto)
                .toList();
        if (skuCodes.size() == inventoryResponses.size()) {
            return inventoryResponses;
        } else {
            throw new InventoryNotFoundException("Request has item that doesn't exist in inventory");
        }
    }
}