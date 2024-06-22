package com.Api.ecommerce.Controller;

import com.Api.ecommerce.Model.Dto.Request.CartItemRequestDto;
import com.Api.ecommerce.Model.Dto.Response.CartResponseDto;
import com.Api.ecommerce.Service.Implementations.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart", description = "APIs for managing shopping carts")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Add product to cart")
    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addToCart(@RequestBody CartItemRequestDto cartItemRequestDto) {
        return new ResponseEntity<>(cartService.addProductToCart( cartItemRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update cart item quantity")
    @PutMapping("/update/{cartId}/{itemId}/{quantity}")
    public ResponseEntity<CartResponseDto> updateCartItem(@PathVariable Long cartId, @PathVariable Long itemId, @PathVariable int quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(cartId, itemId, quantity));
    }

    @Operation(summary = "Delete cart item")
    @DeleteMapping("/delete/{cartId}/{itemId}")
    public ResponseEntity<CartResponseDto> deleteCartItem(@PathVariable Long cartId, @PathVariable Long itemId) {
        return new ResponseEntity<>(cartService.deleteCartItem(cartId, itemId), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get cart by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDto> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @Operation(summary = "Get all carts")
    @GetMapping("/all")
    public ResponseEntity<List<CartResponseDto>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }
}
