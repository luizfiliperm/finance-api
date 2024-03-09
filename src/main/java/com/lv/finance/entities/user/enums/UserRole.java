package com.lv.finance.entities.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    ROLE_MANAGER("manager"),
    ROLE_USER("user");

    private String role;
}
