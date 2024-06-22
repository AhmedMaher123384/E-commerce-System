package com.Api.ecommerce.Model.Mapper;

import com.Api.ecommerce.Model.Dto.Response.CartItemResponseDto;
import com.Api.ecommerce.Model.Dto.Response.CartResponseDto;
import com.Api.ecommerce.Model.Entity.Cart;
import com.Api.ecommerce.Model.Entity.CartItem;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponseDto toCartResponseDto(Cart cart) {
        List<CartItemResponseDto> cartItems = cart.getCartItems().stream()
                .map(this::toCartItemResponseDto)
                .collect(Collectors.toList());
        return CartResponseDto.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .cartItems(cartItems)
                .totalPrice(cart.getTotalPrice())
                .build();
    }


    public CartItemResponseDto toCartItemResponseDto(CartItem cartItem) {
        return CartItemResponseDto.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .build();
    }
}
