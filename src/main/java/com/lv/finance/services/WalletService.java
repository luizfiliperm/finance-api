package com.lv.finance.services;

import com.lv.finance.dtos.wallet.WalletDto;

public interface WalletService {

    WalletDto getWallet(Long userId);

}
