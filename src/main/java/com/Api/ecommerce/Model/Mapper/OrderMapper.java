package com.Api.ecommerce.Model.Mapper;

import com.Api.ecommerce.Model.Dto.Response.OrderItemResponseDto;
import com.Api.ecommerce.Model.Dto.Response.OrderResponseDto;
import com.Api.ecommerce.Model.Entity.Order;
import com.Api.ecommerce.Model.Entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponseDto toOrderResponseDto(Order order) {
        List<OrderItemResponseDto> orderItems = order.getOrderItems().stream()
                .map(this::toOrderItemResponseDto)
                .collect(Collectors.toList());
        return OrderResponseDto.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .orderDate(order.getOrderDate())
                .orderItems(orderItems)
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }

}
