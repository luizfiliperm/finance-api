package com.lv.finance.controllers.wallet;

import com.lv.finance.dtos.wallet.WalletDto;
import com.lv.finance.services.AuthService;
import com.lv.finance.services.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("finances/wallet")
public class WalletController {

    private final WalletService walletService;

    private final AuthService authService;

    public WalletController(WalletService walletService, AuthService authService) {
        this.walletService = walletService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<WalletDto> findById(Authentication authentication){
        return ResponseEntity.ok(walletService.getWallet(authService.extractUserId(authentication)));
    }

}
