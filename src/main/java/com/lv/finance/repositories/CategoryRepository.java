package com.lv.finance.repositories;

import com.lv.finance.entities.wallet.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByIdAndWalletId(Long id, Long walletId);

    Page<Category> findAllByWalletId(Pageable pageable, Long walletId);

    void deleteByIdAndWalletId(Long id, Long userId);
}
