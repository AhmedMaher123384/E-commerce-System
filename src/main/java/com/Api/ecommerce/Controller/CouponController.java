package com.Api.ecommerce.Controller;

import com.Api.ecommerce.Model.Dto.Request.CouponRequestDto;
import com.Api.ecommerce.Model.Entity.Coupon;
import com.Api.ecommerce.Service.Interfaces.CouponServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/coupons")
@Tag(name = "Coupon", description = "APIs for managing coupons")
public class CouponController {

    private final CouponServiceI couponService;


    @Operation(summary = "Create a new coupon")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Coupon> createCoupon(@RequestBody CouponRequestDto couponRequestDto) {
        return new ResponseEntity<>(couponService.createCoupon(couponRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete coupon by ID")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}
