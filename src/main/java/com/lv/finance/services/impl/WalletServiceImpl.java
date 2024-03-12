package com.lv.finance.services.impl;

import com.lv.finance.dtos.wallet.WalletDto;
import com.lv.finance.entities.wallet.Wallet;
import com.lv.finance.repositories.WalletRepository;
import com.lv.finance.services.WalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletDto getWallet(Long userId) {
        return new WalletDto(walletRepository.findByUserId(userId));
    }

    @Override
    public void increaseWalletBalance(Wallet wallet, BigDecimal amount){
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
    }

    @Override
    public void decreaseWalletBalance(Wallet wallet, BigDecimal amount){
        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
    }
}
