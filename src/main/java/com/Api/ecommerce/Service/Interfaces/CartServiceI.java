package com.Api.ecommerce.Service.Interfaces;

import com.Api.ecommerce.Model.Dto.Request.CartItemRequestDto;
import com.Api.ecommerce.Model.Dto.Response.CartResponseDto;
import java.util.List;

public interface CartServiceI {

     CartResponseDto addProductToCart(CartItemRequestDto cartItemRequestDto);
     CartResponseDto updateCartItem(Long cartId, Long itemId, int quantityChange);
     CartResponseDto deleteCartItem(Long cartId, Long itemId);
     CartResponseDto getCartById(Long id);
     List<CartResponseDto> getAllCarts();



}
