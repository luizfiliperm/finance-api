package com.lv.finance.services.impl;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.ExpenseDto;
import com.lv.finance.dtos.wallet.ExpenseReceiveDto;
import com.lv.finance.entities.wallet.Category;
import com.lv.finance.entities.wallet.Expense;
import com.lv.finance.entities.wallet.Wallet;
import com.lv.finance.exceptions.FinanceException;
import com.lv.finance.repositories.CategoryRepository;
import com.lv.finance.repositories.ExpenseRepository;
import com.lv.finance.repositories.WalletRepository;
import com.lv.finance.services.ExpenseService;
import com.lv.finance.services.WalletService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final WalletService walletService;

    private final WalletRepository walletRepository;

    private final ExpenseRepository expenseRepository;

    private final CategoryRepository categoryRepository;

    public ExpenseServiceImpl(WalletRepository walletRepository,
                              ExpenseRepository expenseRepository,
                              CategoryRepository categoryRepository,
                              WalletService walletService) {
        this.walletRepository = walletRepository;
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.walletService = walletService;
    }

    @Override
    public ExpenseDto addExpense(ExpenseReceiveDto expenseReceiveDto, Long userId) {
        Expense expense = expenseReceiveDto.convertToExpense();
        Wallet wallet = getWallet(userId);

        Category category = getCategory(wallet.getId(), expenseReceiveDto.getCategoryId());

        expense.setWallet(wallet);
        expense.setCategory(category);

        expenseRepository.save(expense);
        walletService.decreaseWalletBalance(wallet, expense.getAmount());

        return new ExpenseDto(expense);
    }

    @Override
    public PageResponse<ExpenseDto> findAllExpenses(Long userId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Expense> expensesPage = expenseRepository.findAllByWalletId(getWallet(userId).getId(), pageable);

        List<Expense> expenseList = expensesPage.getContent();
        List<ExpenseDto> content = expenseList.stream().map(ExpenseDto::new).toList();

        return new PageResponse<>(
                content,
                expensesPage.getNumber(),
                expensesPage.getSize(),
                expensesPage.getTotalElements(),
                expensesPage.getTotalPages(),
                expensesPage.isLast());
    }

    @Override
    public void deleteExpense(Long id, Long userId) {
        Expense expense = expenseRepository.findByIdAndWalletId(id, getWallet(userId).getId());

        walletService.increaseWalletBalance(expense.getWallet(), expense.getAmount());
        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseDto updateExpense(ExpenseReceiveDto expenseReceiveDto, Long userId, Long expenseId) {
        try{
            Expense expense = expenseRepository.findByIdAndWalletId(expenseId, getWallet(userId).getId());
            Wallet wallet = getWallet(userId);
            Category category = getCategory(wallet.getId(), expenseReceiveDto.getCategoryId());

            Expense updatedExpense = expenseReceiveDto.convertToExpense();
            updatedExpense.setId(expense.getId());
            updatedExpense.setWallet(wallet);
            updatedExpense.setCategory(category);

            expenseRepository.save(updatedExpense);



            return new ExpenseDto(updatedExpense);
        }catch (Exception e){
            throw new FinanceException("Expense not found", HttpStatus.NOT_FOUND);
        }
    }

    private Wallet getWallet(Long userId){
        return walletRepository.findByUserId(userId);
    }

    private Category getCategory(Long walletId, Long categoryId){
        return categoryRepository.findByIdAndWalletId(categoryId, walletId)
                .orElseThrow(()-> new FinanceException("Category not found", HttpStatus.NOT_FOUND));
    }

    private void updateWalletBalance(Expense updatedExpense, Expense expense, Wallet wallet){
        if(!updatedExpense.getAmount().equals(expense.getAmount())){
            BigDecimal difference = updatedExpense.getAmount().subtract(expense.getAmount());
            if(difference.compareTo(BigDecimal.ZERO) > 0){
                walletService.increaseWalletBalance(wallet, difference);
            } else {
                walletService.decreaseWalletBalance(wallet, difference.abs());
            }
        }
    }

}
