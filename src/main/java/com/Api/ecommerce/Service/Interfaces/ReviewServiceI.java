package com.Api.ecommerce.Service.Interfaces;

import com.Api.ecommerce.Model.Dto.Request.ReviewRequestDto;
import com.Api.ecommerce.Model.Dto.Response.ReviewResponseDto;
import java.util.List;

public interface ReviewServiceI {

     ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto);
     ReviewResponseDto getReviewById(Long id);
     List<ReviewResponseDto> getAllReviews();
     List<ReviewResponseDto> getReviewsByProduct(Long productId);

}
