package com.Api.ecommerce.Service.Interfaces;

import com.Api.ecommerce.Model.Dto.Request.CategoryRequestDto;
import com.Api.ecommerce.Model.Dto.Response.CategoryResponseDto;
import java.util.List;

public interface CategoryServiceI {

     CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
     CategoryResponseDto getCategoryById(Long id);
     List<CategoryResponseDto> getAllCategories();
     CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto);
     void deleteCategory(Long id);



}
