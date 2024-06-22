package com.Api.ecommerce.Controller;

import com.Api.ecommerce.Model.Dto.Response.WishlistResponseDto;
import com.Api.ecommerce.Service.Interfaces.WishlistServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wishlist")
@Tag(name = "Wishlist", description = "APIs for managing customer wishlists")
public class WishlistController {

    private final WishlistServiceI wishlistService;


    @Operation(summary = "Add product to wishlist")
    @PostMapping("/{customerId}/add/{productId}")
    public ResponseEntity<WishlistResponseDto> addProductToWishlist(@PathVariable Long customerId,
                                                                    @PathVariable Long productId) {
        return ResponseEntity.ok(wishlistService.addProductToWishlist(customerId, productId));
    }

    @Operation(summary = "Remove product from wishlist")
    @DeleteMapping("/{customerId}/remove/{productId}")
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable Long customerId,
                                                          @PathVariable Long productId) {
        wishlistService.removeProductFromWishlist(customerId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get wishlist by customer ID")
    @GetMapping("/{customerId}")
    public ResponseEntity<WishlistResponseDto> getWishlistByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(wishlistService.getWishlistByCustomerId(customerId));
    }
}