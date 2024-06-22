package com.Api.ecommerce.Service.Interfaces;

import com.Api.ecommerce.Model.Dto.Request.PaymentRequestDto;
import com.Api.ecommerce.Model.Dto.Response.PaymentResponseDto;


public interface PaymentServiceI {

     PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto);

}
