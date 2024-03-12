package com.lv.finance.controllers.wallet;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.IncomeDto;
import com.lv.finance.services.AuthService;
import com.lv.finance.services.WalletService;
import com.lv.finance.util.AppConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finances/wallet/incomes")
public class IncomeController {

    private final WalletService walletService;

    private final AuthService authService;

    public IncomeController(WalletService walletService, AuthService authService) {
        this.walletService = walletService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<IncomeDto>> getWalletWithIncomes(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_WALLET_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_WALLET_SORT_DIRECTION, required = false) String sortDir,
            Authentication authentication) {
        return ResponseEntity.ok(walletService.findAllIncomes(authService.extractUserId(authentication), page, size, sortBy, sortDir));
    }

    @PostMapping
    public ResponseEntity<IncomeDto> addIncome(@RequestBody IncomeDto incomeDto, Authentication authentication) {
        return ResponseEntity.ok(walletService.addIncome(incomeDto, authService.extractUserId(authentication)));
    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<IncomeDto> updateIncome(@RequestBody IncomeDto incomeDto, @PathVariable Long incomeId, Authentication authentication) {
        return ResponseEntity.ok(walletService.updateIncome(incomeDto, authService.extractUserId(authentication), incomeId));
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long incomeId, Authentication authentication) {
        walletService.deleteIncome(incomeId, authService.extractUserId(authentication));
        return ResponseEntity.noContent().build();
    }



}
