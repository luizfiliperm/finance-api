package com.lv.finance.services.impl;

import com.lv.finance.dtos.authentication.AuthResponseDto;
import com.lv.finance.dtos.authentication.LoginDto;
import com.lv.finance.dtos.authentication.RegisterDto;
import com.lv.finance.entities.user.User;
import com.lv.finance.entities.user.enums.UserRole;
import com.lv.finance.exceptions.FinanceException;
import com.lv.finance.infra.security.services.JwtService;
import com.lv.finance.services.AuthService;
import com.lv.finance.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDto register(RegisterDto registerDto) {
        if(userService.userExistsByEmail(registerDto.getEmail())){
            throw new FinanceException("Email already registered", HttpStatus.BAD_REQUEST);
        }

        User user = registerDto.convertToUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.ROLE_USER);

        userService.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponseDto(user, token);
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new FinanceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        User user = (User) userService.loadUserByEmail(loginDto.getEmail());

        return new AuthResponseDto(user, jwtService.generateToken(user));
    }
}
