package com.khamzin.inventoryservice.controller;

import com.khamzin.inventoryservice.dto.InventoryRequestDto;
import com.khamzin.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String addInventory(@RequestBody InventoryRequestDto inventoryRequestDto) {
        inventoryService.addInventory(inventoryRequestDto);
        return "New inventory added";
    }
}
