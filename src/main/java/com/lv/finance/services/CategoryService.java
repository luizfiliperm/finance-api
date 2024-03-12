package com.lv.finance.services;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto findById(Long categoryId, Long userId);

    CategoryDto save(CategoryDto categoryDto, Long userId);

    void deleteById(Long id, Long userId);

    CategoryDto update(Long categoryId, CategoryDto categoryDto, Long userId);

    PageResponse<CategoryDto> findAll(Long userId);

}
