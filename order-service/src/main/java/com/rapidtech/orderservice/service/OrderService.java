package com.rapidtech.orderservice.service;

import com.rapidtech.orderservice.dto.OrderLineItemsDto;
import com.rapidtech.orderservice.dto.OrderRequest;
import com.rapidtech.orderservice.model.Order;
import com.rapidtech.orderservice.model.OrderLineItems;
import com.rapidtech.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto).toList();
        order.setOrderLineItemsList(orderLineItems);

        //cek di bagian inventory
        Boolean result = webClient.get().uri("http://localhost:7003/api/inventory")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if(result){
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Stok Product tidak mencukupi....");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
