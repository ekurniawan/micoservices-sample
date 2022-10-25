package com.rapidtech.inventoryservice.service;

import com.rapidtech.inventoryservice.dto.InventoryRequest;
import com.rapidtech.inventoryservice.model.Inventory;
import com.rapidtech.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode){
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    public void insertInventory(InventoryRequest inventoryRequest){
        Inventory inventory = new Inventory();
        inventory.setSkuCode(inventoryRequest.getSkuCode());
        inventory.setQuantity(inventoryRequest.getQuantity());
        inventoryRepository.save(inventory);
    }
}
