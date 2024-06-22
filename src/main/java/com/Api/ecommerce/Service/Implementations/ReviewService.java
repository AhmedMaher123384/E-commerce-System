package com.Api.ecommerce.Service.Implementations;

import com.Api.ecommerce.Exception.Ecommerce.CustomerNotFoundException;
import com.Api.ecommerce.Exception.Ecommerce.ProductNotFoundException;
import com.Api.ecommerce.Exception.Ecommerce.ReviewNotFoundException;
import com.Api.ecommerce.Model.Dto.Request.ReviewRequestDto;
import com.Api.ecommerce.Model.Dto.Response.ReviewResponseDto;
import com.Api.ecommerce.Model.Entity.Security.Customer;
import com.Api.ecommerce.Model.Entity.Product;
import com.Api.ecommerce.Model.Entity.Review;
import com.Api.ecommerce.Model.Mapper.ReviewMapper;
import com.Api.ecommerce.Repository.Security.CustomerRepository;
import com.Api.ecommerce.Repository.ProductRepository;
import com.Api.ecommerce.Repository.ReviewRepository;
import com.Api.ecommerce.Service.Interfaces.ReviewServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewServiceI {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;


    @Override
    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto) {
        Customer customer = customerRepository.findById(reviewRequestDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        Product product = productRepository.findById(reviewRequestDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Review review = reviewMapper.toReviewEntity(reviewRequestDto, product, customer);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toReviewResponseDto(savedReview);
    }

    @Override
    public ReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        return reviewMapper.toReviewResponseDto(review);
    }


    @Override
    public List<ReviewResponseDto> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(reviewMapper::toReviewResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDto> getReviewsByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        List<Review> reviews = reviewRepository.findByProduct(product);
        return reviews.stream()
                .map(reviewMapper::toReviewResponseDto)
                .collect(Collectors.toList());
    }
}