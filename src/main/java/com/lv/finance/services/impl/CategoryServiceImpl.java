package com.lv.finance.services.impl;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.CategoryDto;
import com.lv.finance.entities.wallet.Category;
import com.lv.finance.entities.wallet.Wallet;
import com.lv.finance.exceptions.FinanceException;
import com.lv.finance.repositories.CategoryRepository;
import com.lv.finance.repositories.WalletRepository;
import com.lv.finance.services.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final WalletRepository walletRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, WalletRepository walletRepository) {
        this.categoryRepository = categoryRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public CategoryDto findById(Long categoryId, Long userId) {

        Category category = categoryRepository.findByIdAndWalletId(categoryId, userId)
                .orElseThrow(() -> new FinanceException("Category not found", HttpStatus.NOT_FOUND));

        return new CategoryDto(category);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto, Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId);

        Category category = categoryDto.convertToCategory();
        category.setWallet(wallet);

        return new CategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteById(Long id, Long userId) {
        categoryRepository.deleteByIdAndWalletId(id, userId);
    }

    @Override
    @Transactional
    public CategoryDto update(Long categoryId, CategoryDto categoryDto, Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId);

        Category category = categoryRepository.findByIdAndWalletId(categoryId, userId)
                .orElseThrow(() -> new FinanceException("Category not found", HttpStatus.NOT_FOUND));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setWallet(wallet);

        return new CategoryDto(categoryRepository.save(category));
    }

    @Override
    public PageResponse<CategoryDto> findAll(int page, int size, String sortBy, String sortDir, Long userId) {

        Wallet wallet = walletRepository.findByUserId(userId);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categoriesPage = categoryRepository.findAllByWalletId(pageable, wallet.getId());

        List<Category> categoryList = categoriesPage.getContent();

        List<CategoryDto> content = categoryList.stream().map(CategoryDto::new).toList();

        return new PageResponse<>(content,
                categoriesPage.getNumber(),
                categoriesPage.getSize(),
                categoriesPage.getTotalElements(),
                categoriesPage.getTotalPages(),
                categoriesPage.isLast());
    }
}
