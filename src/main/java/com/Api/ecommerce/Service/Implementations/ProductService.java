package com.Api.ecommerce.Service.Implementations;

import com.Api.ecommerce.Exception.Ecommerce.CategoryNotFoundException;
import com.Api.ecommerce.Exception.Ecommerce.ProductNotFoundException;
import com.Api.ecommerce.Model.Dto.Request.ProductRequestDto;
import com.Api.ecommerce.Model.Dto.Response.ProductResponseDto;
import com.Api.ecommerce.Model.Entity.Category;
import com.Api.ecommerce.Model.Entity.Product;
import com.Api.ecommerce.Model.Mapper.ProductMapper;
import com.Api.ecommerce.Repository.CategoryRepository;
import com.Api.ecommerce.Repository.ProductRepository;
import com.Api.ecommerce.Service.Interfaces.ProductServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceI {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;


    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Category category = categoryRepository.findById(productRequestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        Product product = productMapper.toProductEntity(productRequestDto, category);
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponseDto(savedProduct);
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return productMapper.toProductResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public ProductResponseDto updateProductQuantity(Long productId, int newQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setQuantity(newQuantity);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductResponseDto(updatedProduct);
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Category category = categoryRepository.findById(productRequestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setDescription(productRequestDto.getDescription());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductResponseDto(updatedProduct);
    }
}
