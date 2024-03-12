package com.lv.finance.controllers.wallet;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.CategoryDto;
import com.lv.finance.services.AuthService;
import com.lv.finance.services.CategoryService;
import com.lv.finance.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finances/wallet/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final AuthService authService;

    public CategoryController(CategoryService categoryService, AuthService authService) {
        this.categoryService = categoryService;
        this.authService = authService;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId, Authentication authentication) {
        return ResponseEntity.ok(categoryService.findById(categoryId, authService.extractUserId(authentication)));
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryDto>> findAll(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            Authentication authentication) {
        return ResponseEntity.ok(categoryService.findAll(page, size, sortBy, sortDir, authService.extractUserId(authentication)));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto, Authentication authentication) {
        return ResponseEntity.ok(categoryService.save(categoryDto, authService.extractUserId(authentication)));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId, Authentication authentication) {
        return ResponseEntity.ok(categoryService.update(categoryId, categoryDto, authService.extractUserId(authentication)));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId, Authentication authentication) {
        categoryService.deleteById(categoryId, authService.extractUserId(authentication));
        return ResponseEntity.noContent().build();
    }

}
