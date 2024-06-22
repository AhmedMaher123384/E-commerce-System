package com.Api.ecommerce.Service.Implementations;

import com.Api.ecommerce.Exception.Ecommerce.CartNotFoundException;
import com.Api.ecommerce.Exception.Ecommerce.CustomerNotFoundException;
import com.Api.ecommerce.Exception.Ecommerce.InsufficientProductQuantityException;
import com.Api.ecommerce.Exception.Ecommerce.ProductNotFoundException;
import com.Api.ecommerce.Model.Dto.Request.CartItemRequestDto;
import com.Api.ecommerce.Model.Dto.Response.CartResponseDto;
import com.Api.ecommerce.Model.Entity.Cart;
import com.Api.ecommerce.Model.Entity.CartItem;
import com.Api.ecommerce.Model.Entity.Security.Customer;
import com.Api.ecommerce.Model.Entity.Product;
import com.Api.ecommerce.Model.Mapper.CartMapper;
import com.Api.ecommerce.Repository.CartRepository;
import com.Api.ecommerce.Repository.Security.CustomerRepository;
import com.Api.ecommerce.Repository.ProductRepository;
import com.Api.ecommerce.Service.Interfaces.CartServiceI;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CartService implements CartServiceI {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;


    @Transactional
    @Override
    public CartResponseDto addProductToCart(CartItemRequestDto cartItemRequestDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Optional<Cart> optionalCart = cartRepository.findByCustomerId(customer.getId());
        Cart cart;

        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = Cart.builder()
                    .customer(customer)
                    .cartItems(new ArrayList<>())
                    .totalPrice(0)
                    .build();
            cartRepository.save(cart);
        }

        Product product = productRepository.findById(cartItemRequestDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (product.getQuantity() < cartItemRequestDto.getQuantity()) {
            throw new InsufficientProductQuantityException("Insufficient product quantity available");
        }

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItemRequestDto.getProductId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + cartItemRequestDto.getQuantity();

            if (product.getQuantity() < newQuantity) {
                throw new InsufficientProductQuantityException("Insufficient product quantity available");
            }

            cartItem.setQuantity(newQuantity);
            cartItem.setPrice(product.getPrice() * newQuantity);
        } else {
            CartItem cartItem = CartItem.builder()
                    .product(product)
                    .quantity(cartItemRequestDto.getQuantity())
                    .price(product.getPrice() * cartItemRequestDto.getQuantity())
                    .cart(cart)
                    .build();
            cart.getCartItems().add(cartItem);
        }

        cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getPrice).sum());

        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toCartResponseDto(updatedCart);
    }

    @Override
    public CartResponseDto updateCartItem(Long cartId, Long itemId, int quantityChange) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new CartNotFoundException("Cart item not found"));

        Product product = cartItem.getProduct();
        int newQuantity = cartItem.getQuantity() + quantityChange;

        if (newQuantity > 0 && product.getQuantity() < newQuantity) {
            throw new InsufficientProductQuantityException("Insufficient product quantity available");
        }

        if (newQuantity <= 0) {
            cart.getCartItems().remove(cartItem);
        } else {
            cartItem.setQuantity(newQuantity);
            cartItem.setPrice(cartItem.getProduct().getPrice() * newQuantity);
        }

        cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getPrice).sum());

        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toCartResponseDto(updatedCart);
    }


    @Override
    public CartResponseDto deleteCartItem(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        cart.getCartItems().removeIf(item -> item.getId().equals(itemId));

        cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getPrice).sum());

        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toCartResponseDto(updatedCart);
    }


    @Override
    public CartResponseDto getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        return cartMapper.toCartResponseDto(cart);
    }



    @Override
    public List<CartResponseDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream()
                .map(cartMapper::toCartResponseDto)
                .collect(Collectors.toList());
    }
}
