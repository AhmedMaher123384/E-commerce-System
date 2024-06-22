package com.Api.ecommerce.Model.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CouponRequestDto {
    @NotEmpty(message = "Code cannot be empty")
    @Size(max = 50, message = "Code must be less than 10 characters")
    private String code;

    @Min(value = 0, message = "Discount percentage must be at least 0")
    @Max(value = 100, message = "Discount percentage must be less than or equal to 100")
    private double discountPercentage;

    @NotNull(message = "Expiry date cannot be null")
    private Date expiryDate;

    private boolean isActive;
}