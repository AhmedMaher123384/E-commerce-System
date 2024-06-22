package com.Api.ecommerce.Service.Implementations;

import com.Api.ecommerce.Exception.Ecommerce.CartNotFoundException;
import com.Api.ecommerce.Exception.Ecommerce.OrderNotFoundException;
import com.Api.ecommerce.Model.Dto.Request.OrderRequestDto;
import com.Api.ecommerce.Model.Dto.Response.OrderResponseDto;
import com.Api.ecommerce.Model.Entity.*;
import com.Api.ecommerce.Model.Entity.Security.Customer;
import com.Api.ecommerce.Model.Mapper.OrderMapper;
import com.Api.ecommerce.Repository.CartRepository;
import com.Api.ecommerce.Repository.OrderRepository;
import com.Api.ecommerce.Service.Interfaces.OrderServiceI;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceI {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final CouponService couponService;
    private final EmailService emailService;



    @Transactional
    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Cart cart = cartRepository.findById(orderRequestDto.getCartId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        Customer customer = cart.getCustomer();

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);
        order.setTotalPrice(cart.getTotalPrice());

        double totalPrice = cart.getTotalPrice();
        double discountPercentage = 0;

        if (orderRequestDto.getCouponCode() != null && !orderRequestDto.getCouponCode().isEmpty()) {
            discountPercentage = couponService.getDiscountForCoupon(orderRequestDto.getCouponCode());
            double discount = totalPrice * (discountPercentage / 100);
            totalPrice -= discount;
        }
        order.setTotalPrice(totalPrice);

        emailService.sendEmail(
                customer.getEmail(),
                "Order Confirmation",
                "Dear " + customer.getName() + ",\n\nYour order #" + order.getId() + " has been placed successfully.\n\nThank you for shopping with us!\n\nBest regards,\nE-Commerce Team"
        );

        Order savedOrder = orderRepository.save(order);
        OrderResponseDto responseDto = orderMapper.toOrderResponseDto(savedOrder);
        responseDto.setOriginalPrice(cart.getTotalPrice());
        responseDto.setDiscountPercentage(discountPercentage);
        return responseDto;
    }


    @Override
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return orderMapper.toOrderResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toOrderResponseDto)
                .collect(Collectors.toList());
    }
}