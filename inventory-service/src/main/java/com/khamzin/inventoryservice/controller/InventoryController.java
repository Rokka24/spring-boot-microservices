package com.khamzin.inventoryservice.controller;

import com.khamzin.inventoryservice.dto.InventoryRequestDto;
import com.khamzin.inventoryservice.dto.InventoryResponseDto;
import com.khamzin.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto> isInStock(@RequestParam List<String> skuCode) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryService.isInStock(skuCode);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String addInventory(@RequestBody InventoryRequestDto inventoryRequestDto) {
        log.info("Received inventory add request: {}", inventoryRequestDto);
        inventoryService.addInventory(inventoryRequestDto);
        return "New inventory added";
    }
}
