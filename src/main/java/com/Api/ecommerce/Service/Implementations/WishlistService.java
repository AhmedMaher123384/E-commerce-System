package com.Api.ecommerce.Service.Implementations;

import com.Api.ecommerce.Exception.Ecommerce.CustomerNotFoundException;
import com.Api.ecommerce.Exception.Ecommerce.ProductNotFoundException;
import com.Api.ecommerce.Exception.Ecommerce.WishlistNotFoundException;
import com.Api.ecommerce.Model.Dto.Response.WishlistResponseDto;
import com.Api.ecommerce.Model.Entity.Security.Customer;
import com.Api.ecommerce.Model.Entity.Product;
import com.Api.ecommerce.Model.Entity.Wishlist;
import com.Api.ecommerce.Model.Mapper.ProductMapper;
import com.Api.ecommerce.Repository.Security.CustomerRepository;
import com.Api.ecommerce.Repository.ProductRepository;
import com.Api.ecommerce.Repository.WishlistRepository;
import com.Api.ecommerce.Service.Interfaces.WishlistServiceI;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService implements WishlistServiceI {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;



    @Transactional
    @Override
    public WishlistResponseDto addProductToWishlist(Long customerId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Optional<Wishlist> optionalWishlist = wishlistRepository.findByCustomerId(customerId);
        Wishlist wishlist;

        if (optionalWishlist.isPresent()) {
            wishlist = optionalWishlist.get();
        } else {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
            wishlist = new Wishlist();
            wishlist.setCustomer(customer);
            wishlist = wishlistRepository.save(wishlist);
        }

        if (wishlist.getProducts() == null) {
            wishlist.setProducts(new ArrayList<>());
        }

        if (!wishlist.getProducts().contains(product)) {
            wishlist.getProducts().add(product);
            wishlist = wishlistRepository.save(wishlist);
        }

        return productMapper.toWishlistResponseDto(wishlist);
    }

    @Override
    public WishlistResponseDto removeProductFromWishlist(Long customerId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist not found"));

        wishlist.getProducts().remove(product);
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return productMapper.toWishlistResponseDto(savedWishlist);
    }

    @Override
    public WishlistResponseDto getWishlistByCustomerId(Long customerId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist not found"));

        return productMapper.toWishlistResponseDto(wishlist);
    }
}
