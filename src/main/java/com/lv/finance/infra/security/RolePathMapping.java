package com.lv.finance.infra.security;

public class RolePathMapping {

    public static final String[] ANY_REQUEST_WHITELIST = {
            "/finances/auth/login",
            "/finances/auth/register"
    };

    public static final String[] MANAGER_REQUEST_WHITELIST = {
            "/finances/admin/**"
    };

    public static final String[] LOGGED_REQUEST_WHITELIST = {
            "/finances/users",
            "/finances/wallet",
            "/finances/wallet/incomes",
            "/finances/wallet/incomes/**"
    };

}
