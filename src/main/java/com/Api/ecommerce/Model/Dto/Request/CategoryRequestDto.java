package com.Api.ecommerce.Model.Dto.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryRequestDto {
    @NotEmpty(message = "Name cannot be empty")
    @Size(max = 20, message = "Name must be less than 20 characters")
    private String name;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
}