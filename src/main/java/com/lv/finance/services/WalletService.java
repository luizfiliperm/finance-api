package com.lv.finance.services;

import com.lv.finance.dtos.wallet.WalletDto;
import com.lv.finance.entities.wallet.Wallet;

import java.math.BigDecimal;

public interface WalletService {

    WalletDto getWallet(Long userId);

    void decreaseWalletBalance(Wallet wallet, BigDecimal amount);

    void increaseWalletBalance(Wallet wallet, BigDecimal amount);
}
