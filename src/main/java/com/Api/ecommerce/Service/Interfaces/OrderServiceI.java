package com.Api.ecommerce.Service.Interfaces;

import com.Api.ecommerce.Model.Dto.Request.OrderRequestDto;
import com.Api.ecommerce.Model.Dto.Response.OrderResponseDto;
import java.util.List;

public interface OrderServiceI {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto getOrderById(Long id);
    List<OrderResponseDto> getAllOrders();




}
