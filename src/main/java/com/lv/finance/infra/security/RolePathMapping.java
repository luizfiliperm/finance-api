package com.lv.finance.infra.security;

public class RolePathMapping {

    public static final String[] ANY_REQUEST_WHITELIST = {
            "/finances/auth/login",
            "/finances/auth/register"
    };

}