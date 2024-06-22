package com.Api.ecommerce.Controller;

import com.Api.ecommerce.Model.Dto.Request.PaymentRequestDto;
import com.Api.ecommerce.Model.Dto.Response.PaymentResponseDto;
import com.Api.ecommerce.Service.Implementations.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment", description = "APIs for managing payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Process a payment", description = "Processes a payment for an order using Stripe.")
    public ResponseEntity<PaymentResponseDto> processPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        return ResponseEntity.ok(paymentService.processPayment(paymentRequestDto));
    }

}
