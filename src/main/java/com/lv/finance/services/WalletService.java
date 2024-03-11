package com.lv.finance.services;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.IncomeDto;

public interface WalletService {

    IncomeDto addIncome(IncomeDto incomeDto, Long userId);

    PageResponse<IncomeDto> findAllIncomes(Long userId, int pageNo, int pageSize, String sortBy, String sortDir);

    void deleteIncome(Long id, Long userId);

    IncomeDto updateIncome(IncomeDto incomeDto, Long userId);

}
