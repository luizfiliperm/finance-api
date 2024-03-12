package com.lv.finance.repositories;

import com.lv.finance.entities.wallet.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense findByIdAndWalletId(Long id, Long walletId);

    Page<Expense> findAllByWalletId(Long walletId, Pageable pageable);

}
