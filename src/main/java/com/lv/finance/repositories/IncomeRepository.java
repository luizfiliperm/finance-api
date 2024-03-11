package com.lv.finance.repositories;

import com.lv.finance.entities.wallet.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    Income findByIdAndWalletId(Long id, Long walletId);

    Page<Income> findAllByWalletId(Long walletId, Pageable pageable);
}
