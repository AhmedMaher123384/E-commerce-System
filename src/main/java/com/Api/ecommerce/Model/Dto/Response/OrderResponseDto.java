package com.Api.ecommerce.Model.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderResponseDto {
    private Long id;
    private Date orderDate;
    private Long customerId;
    private List<OrderItemResponseDto> orderItems;
    private double totalPrice;
    private double originalPrice;
    private double discountPercentage;
}