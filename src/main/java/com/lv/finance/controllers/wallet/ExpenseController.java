package com.lv.finance.controllers.wallet;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.ExpenseDto;
import com.lv.finance.dtos.wallet.ExpenseReceiveDto;
import com.lv.finance.services.AuthService;
import com.lv.finance.services.ExpenseService;
import com.lv.finance.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finances/wallet/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    private final AuthService authService;

    public ExpenseController(ExpenseService expenseService, AuthService authService) {
        this.expenseService = expenseService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<ExpenseDto>> getAllExpenses(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_WALLET_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_WALLET_SORT_DIRECTION, required = false) String sortDir,
            Authentication authentication
    ){
        return ResponseEntity.ok(expenseService.findAllExpenses(authService.extractUserId(authentication), page, size, sortBy, sortDir));
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@Valid @RequestBody ExpenseReceiveDto expenseReceiveDto, Authentication authentication){
        return ResponseEntity.ok(expenseService.addExpense(expenseReceiveDto, authService.extractUserId(authentication)));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseDto> updateExpense(@Valid @RequestBody ExpenseReceiveDto expenseReceiveDto,@PathVariable Long expenseId , Authentication authentication){
        return ResponseEntity.ok(expenseService.updateExpense(expenseReceiveDto, authService.extractUserId(authentication), expenseId));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId, Authentication authentication){
        expenseService.deleteExpense(expenseId, authService.extractUserId(authentication));
        return ResponseEntity.noContent().build();
    }
}
