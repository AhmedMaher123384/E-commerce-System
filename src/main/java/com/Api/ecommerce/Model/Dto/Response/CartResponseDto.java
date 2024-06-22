package com.Api.ecommerce.Model.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartResponseDto {
    private Long id;
    private Long customerId;
    private List<CartItemResponseDto> cartItems;
    private double totalPrice;
}