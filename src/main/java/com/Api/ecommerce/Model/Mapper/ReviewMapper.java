package com.Api.ecommerce.Model.Mapper;

import com.Api.ecommerce.Model.Dto.Request.ReviewRequestDto;
import com.Api.ecommerce.Model.Dto.Response.ReviewResponseDto;
import com.Api.ecommerce.Model.Entity.Security.Customer;
import com.Api.ecommerce.Model.Entity.Product;
import com.Api.ecommerce.Model.Entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponseDto toReviewResponseDto(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .productId(review.getProduct().getId())
                .customerId(review.getCustomer().getId())
                .build();
    }

    public Review toReviewEntity(ReviewRequestDto reviewRequestDto, Product product, Customer customer) {
        return Review.builder()
                .content(reviewRequestDto.getContent())
                .rating(reviewRequestDto.getRating())
                .product(product)
                .customer(customer)
                .build();
    }



}
