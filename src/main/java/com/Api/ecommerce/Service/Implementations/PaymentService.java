package com.Api.ecommerce.Service.Implementations;

import com.Api.ecommerce.Exception.Ecommerce.OrderNotFoundException;
import com.Api.ecommerce.Model.Dto.Request.PaymentRequestDto;
import com.Api.ecommerce.Model.Dto.Response.PaymentResponseDto;
import com.Api.ecommerce.Model.Entity.Order;
import com.Api.ecommerce.Model.Entity.Payment;
import com.Api.ecommerce.Repository.OrderRepository;
import com.Api.ecommerce.Repository.PaymentRepository;
import com.Api.ecommerce.Service.Interfaces.PaymentServiceI;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class PaymentService implements PaymentServiceI {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${payment.success.url}")
    private String successUrl;

    @Value("${payment.cancel.url}")
    private String cancelUrl;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }


    @Override
    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto) {
        Order order = orderRepository.findById(paymentRequestDto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod("stripe");
        payment.setPaymentStatus("PROCESSING");
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentDate(new Date());

        String checkoutUrl = processStripePayment(order, payment);

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentResponseDto.builder()
                .id(savedPayment.getId())
                .orderId(savedPayment.getOrder().getId())
                .paymentMethod(savedPayment.getPaymentMethod())
                .paymentStatus(savedPayment.getPaymentStatus())
                .amount(savedPayment.getAmount())
                .paymentDate(savedPayment.getPaymentDate())
                .paymentLink(checkoutUrl)
                .build();
    }


    private String processStripePayment(Order order, Payment payment) {
        Map<String, Object> params = new HashMap<>();
        params.put("success_url", successUrl + "?session_id={CHECKOUT_SESSION_ID}");
        params.put("cancel_url", cancelUrl);
        params.put("payment_method_types", Arrays.asList("card"));
        params.put("line_items", Arrays.asList(
                Map.of(
                        "price_data", Map.of(
                                "currency", "usd",
                                "product_data", Map.of(
                                        "name", "Order #" + order.getId()
                                ),
                                "unit_amount", (long) (order.getTotalPrice() * 100)
                        ),
                        "quantity", 1
                )
        ));
        params.put("mode", "payment");

        try {
            Session session = Session.create(params);
            payment.setPaymentStatus("PENDING");
            return session.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException("Stripe payment failed", e);
        }
    }
}
