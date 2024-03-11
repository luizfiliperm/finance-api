package com.lv.finance.services;

import com.lv.finance.dtos.wallet.IncomeDto;
import com.lv.finance.dtos.wallet.WalletPageResponse;

public interface WalletService {

    IncomeDto addIncome(IncomeDto incomeDto, Long userId);

    WalletPageResponse<IncomeDto> getWalletWithIncomes(Long userId);

    void deleteIncome(Long id, Long userId);

    IncomeDto updateIncome(IncomeDto incomeDto, Long userId);

}
