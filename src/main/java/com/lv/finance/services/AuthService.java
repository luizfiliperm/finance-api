package com.lv.finance.services;

import com.lv.finance.dtos.authentication.AuthResponseDto;
import com.lv.finance.dtos.authentication.LoginDto;
import com.lv.finance.dtos.authentication.RegisterDto;

public interface AuthService {

    AuthResponseDto register(RegisterDto registerDto);

    AuthResponseDto login(LoginDto loginDto);

}
