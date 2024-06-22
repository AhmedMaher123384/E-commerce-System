package com.Api.ecommerce.Service.Interfaces;

import com.Api.ecommerce.Model.Dto.Response.WishlistResponseDto;


public interface WishlistServiceI {

    WishlistResponseDto addProductToWishlist(Long customerId, Long productId);
    WishlistResponseDto removeProductFromWishlist(Long customerId, Long productId);
    WishlistResponseDto getWishlistByCustomerId(Long customerId);

}
