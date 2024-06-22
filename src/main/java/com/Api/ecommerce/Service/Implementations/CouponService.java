package com.Api.ecommerce.Service.Implementations;

import com.Api.ecommerce.Exception.Ecommerce.CouponNotFoundException;
import com.Api.ecommerce.Exception.Ecommerce.InvalidCouponException;
import com.Api.ecommerce.Model.Dto.Request.CouponRequestDto;
import com.Api.ecommerce.Model.Entity.Coupon;
import com.Api.ecommerce.Repository.CouponRepository;
import com.Api.ecommerce.Service.Interfaces.CouponServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class CouponService implements CouponServiceI {

    private final CouponRepository couponRepository;

    @Override
    public double getDiscountForCoupon(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found"));

        if (!coupon.isActive() || coupon.getExpiryDate().before(new Date())) {
            throw new InvalidCouponException("Coupon is not valid");
        }

        return coupon.getDiscountPercentage();
    }


    @Override
    public Coupon createCoupon(CouponRequestDto couponRequestDto) {

        Date expireationDate = new Date(System.currentTimeMillis()+86400000);
        Coupon coupon = Coupon.builder()
                .code(couponRequestDto.getCode())
                .discountPercentage(couponRequestDto.getDiscountPercentage())
                .expiryDate(expireationDate)
                .isActive(couponRequestDto.isActive())
                .build();

        return couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }
}