package com.lv.finance.services.impl;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.IncomeDto;

import com.lv.finance.entities.wallet.Income;
import com.lv.finance.entities.wallet.Wallet;
import com.lv.finance.exceptions.FinanceException;
import com.lv.finance.repositories.IncomeRepository;
import com.lv.finance.repositories.WalletRepository;
import com.lv.finance.services.IncomeService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final WalletRepository walletRepository;

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(WalletRepository walletRepository, IncomeRepository incomeRepository) {
        this.walletRepository = walletRepository;
        this.incomeRepository = incomeRepository;
    }

    @Override
    public IncomeDto addIncome(IncomeDto incomeDto, Long userId) {
        Income income = incomeDto.convertToIncome();

        income.setId(null);
        income.setWallet(getWallet(userId));
        incomeRepository.save(income);

        increaseWalletBalance(income.getWallet(), income.getAmount());

        return new IncomeDto(income);
    }

    @Override
    public PageResponse<IncomeDto> findAllIncomes(Long userId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Income> incomesPage = incomeRepository.findAllByWalletId(getWallet(userId).getId(), pageable);

        List<Income> incomeList = incomesPage.getContent();
        List<IncomeDto> content = incomeList.stream().map(IncomeDto::new).toList();


        return new PageResponse<>(
                content,
                incomesPage.getNumber(),
                incomesPage.getSize(),
                incomesPage.getTotalElements(),
                incomesPage.getTotalPages(),
                incomesPage.isLast());
    }

    @Override
    public void deleteIncome(Long id, Long userId) {
        Income income = incomeRepository.findByIdAndWalletId(id, getWallet(userId).getId());
        decreaseWalletBalance(income.getWallet(), income.getAmount());
        incomeRepository.delete(income);
    }

    @Override
    @Transactional
    public IncomeDto updateIncome(IncomeDto incomeDto, Long userId, Long incomeId) {
        try {
            Income income = incomeRepository.findByIdAndWalletId(incomeId, getWallet(userId).getId());
            
            Income updatedIncome = incomeDto.convertToIncome();
            Wallet wallet = getWallet(userId);

            updatedIncome.setId(incomeId);
            updatedIncome.setWallet(wallet);

            updateWalletBalance(updatedIncome, income, wallet);
            incomeRepository.save(updatedIncome);


            return new IncomeDto(updatedIncome);
        } catch (Exception e) {
            throw new FinanceException("Income not found!", HttpStatus.BAD_REQUEST);
        }

    }



    private Wallet getWallet(Long userId){
        return walletRepository.findByUserId(userId);
    }

    private void increaseWalletBalance(Wallet wallet, BigDecimal amount){
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
    }

    private void decreaseWalletBalance(Wallet wallet, BigDecimal amount){
        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
    }

    private void updateWalletBalance(Income updatedIncome, Income income, Wallet wallet) {
        if(!updatedIncome.getAmount().equals(income.getAmount())){
            BigDecimal difference = updatedIncome.getAmount().subtract(income.getAmount());
            if(difference.compareTo(BigDecimal.ZERO) > 0){
                increaseWalletBalance(wallet, difference);
            } else {
                decreaseWalletBalance(wallet, difference.abs());
            }
        }
    }
    

}
