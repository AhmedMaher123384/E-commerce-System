package com.Api.ecommerce.Model.Dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequestDto {
    @NotNull(message = "Cart ID cannot be null")
    private Long cartId;

    private String couponCode;

}
