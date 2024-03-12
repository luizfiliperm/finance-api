package com.lv.finance.services;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.ExpenseDto;
import com.lv.finance.dtos.wallet.ExpenseReceiveDto;

public interface ExpenseService {

    ExpenseDto addExpense(ExpenseReceiveDto expenseReceiveDto, Long userId);

    PageResponse<ExpenseDto> findAllExpenses(Long userId,  int pageNo, int pageSize, String sortBy, String sortDir);

    void deleteExpense(Long id, Long userId);

    ExpenseDto updateExpense(ExpenseReceiveDto expenseReceiveDto, Long userId, Long expenseId);

}
