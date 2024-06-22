package com.Api.ecommerce.Model.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentResponseDto {
    private Long id;
    private Long orderId;
    private String paymentMethod;
    private String paymentStatus;
    private double amount;
    private Date paymentDate;
    private String paymentLink;

}