package com.Api.ecommerce.Model.Mapper;

import com.Api.ecommerce.Model.Dto.Request.CategoryRequestDto;
import com.Api.ecommerce.Model.Dto.Response.CategoryResponseDto;
import com.Api.ecommerce.Model.Entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponseDto toCategoryResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public Category toCategoryEntity(CategoryRequestDto categoryRequestDto) {
        return Category.builder()
                .name(categoryRequestDto.getName())
                .description(categoryRequestDto.getDescription())
                .build();
    }

}
