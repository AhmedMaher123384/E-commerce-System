package com.Api.ecommerce.Service.Interfaces;

import com.Api.ecommerce.Model.Dto.Request.CouponRequestDto;
import com.Api.ecommerce.Model.Entity.Coupon;


public interface CouponServiceI {

    double getDiscountForCoupon(String code);
    Coupon createCoupon(CouponRequestDto couponRequestDto);
    void deleteCoupon(Long id);




}
