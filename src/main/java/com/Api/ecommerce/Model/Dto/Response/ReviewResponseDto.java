package com.Api.ecommerce.Model.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewResponseDto {
    private Long id;
    private String content;
    private Integer rating;
    private Long productId;
    private Long customerId;
}