package com.rapidtech.inventoryservice.controller;

import com.rapidtech.inventoryservice.dto.InventoryRequest;
import com.rapidtech.inventoryservice.dto.InventoryResponse;
import com.rapidtech.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String insertInventory(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.insertInventory(inventoryRequest);
        return "Data inventory added";
    }
}
