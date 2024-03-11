package com.lv.finance.entities.wallet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {

    DEBIT("debit"),
    CREDIT("credit");

    private String method;
}
