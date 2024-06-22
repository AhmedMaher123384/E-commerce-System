package com.Api.ecommerce.Model.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderItemResponseDto {
        private Long id;
        private Long productId;
        private int quantity;
        private double price;
    }
