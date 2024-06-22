package com.Api.ecommerce.Model.Mapper;

import com.Api.ecommerce.Model.Dto.Request.*;
import com.Api.ecommerce.Model.Dto.Response.*;
import com.Api.ecommerce.Model.Entity.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ProductMapper {

    public ProductResponseDto toProductResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .quantity(product.getQuantity())
                .build();
    }

    public Product toProductEntity(ProductRequestDto productRequestDto, Category category) {
        return Product.builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .description(productRequestDto.getDescription())
                .category(category)
                .quantity(productRequestDto.getQuantity())
                .build();
    }
public WishlistResponseDto toWishlistResponseDto(Wishlist wishlist) {
        List<ProductResponseDto> productResponseDtos = wishlist.getProducts().stream()
                .map(this::toProductResponseDto)
                .collect(Collectors.toList());

        return WishlistResponseDto.builder()
                .id(wishlist.getId())
                .customerId(wishlist.getCustomer().getId())
                .products(productResponseDtos)
                .build();
    }
}
