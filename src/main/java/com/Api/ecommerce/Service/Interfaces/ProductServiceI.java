package com.Api.ecommerce.Service.Interfaces;

import com.Api.ecommerce.Model.Dto.Request.ProductRequestDto;
import com.Api.ecommerce.Model.Dto.Response.ProductResponseDto;
import java.util.List;

public interface ProductServiceI {

     ProductResponseDto createProduct(ProductRequestDto productRequestDto);
     ProductResponseDto getProductById(Long id);
     List<ProductResponseDto> getAllProducts();
     List<ProductResponseDto> getProductsByCategory(Long categoryId);
     void deleteProduct(Long productId);
     ProductResponseDto updateProductQuantity(Long productId, int newQuantity);
     ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto);

}
