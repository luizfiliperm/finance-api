package com.lv.finance.services;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.CategoryDto;


public interface CategoryService {

    CategoryDto findById(Long categoryId, Long userId);

    CategoryDto save(CategoryDto categoryDto, Long userId);

    void deleteById(Long id, Long userId);

    CategoryDto update(Long categoryId, CategoryDto categoryDto, Long userId);

    PageResponse<CategoryDto> findAll(int page, int size, String sortBy, String sortDir, Long userId);

}
