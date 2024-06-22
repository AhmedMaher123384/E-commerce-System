package com.Api.ecommerce.Model.Dto.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductRequestDto {
    @NotEmpty(message = "Name cannot be empty")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;

    @Min(value = 0, message = "Price must be at least 0")
    private double price;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    @Min(value = 0, message = "Quantity must be at least 0")
    private int quantity;
}