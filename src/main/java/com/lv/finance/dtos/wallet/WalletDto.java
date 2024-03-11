package com.lv.finance.dtos.wallet;

import com.lv.finance.entities.wallet.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WalletDto {

    private BigDecimal balance;

    public WalletDto(Wallet wallet){
        this.balance = wallet.getBalance();
    }
}
