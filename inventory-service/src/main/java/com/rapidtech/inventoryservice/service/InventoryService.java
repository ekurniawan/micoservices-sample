package com.rapidtech.inventoryservice.service;

import com.rapidtech.inventoryservice.dto.InventoryRequest;
import com.rapidtech.inventoryservice.dto.InventoryResponse;
import com.rapidtech.inventoryservice.model.Inventory;
import com.rapidtech.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode){
        log.info("Mulai menunggu");
        Thread.sleep(10000);
        log.info("Selesai menunggu");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0)
                            .build()).toList();
    }

    public void insertInventory(InventoryRequest inventoryRequest){
        Inventory inventory = new Inventory();
        inventory.setSkuCode(inventoryRequest.getSkuCode());
        inventory.setQuantity(inventoryRequest.getQuantity());
        inventoryRepository.save(inventory);
    }
}
