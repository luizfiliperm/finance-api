package com.lv.finance.entities.wallet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IncomeType {
    MONTHLY("monthly"),
    SPORADIC("sporadic");

    private String type;
}
