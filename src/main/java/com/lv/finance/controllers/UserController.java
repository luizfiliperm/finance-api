package com.lv.finance.controllers;


import com.lv.finance.dtos.user.UserDto;
import com.lv.finance.dtos.user.UserReceiveDto;
import com.lv.finance.services.AuthService;
import com.lv.finance.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finances/users")
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    public UserController(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserReceiveDto userReceiveDto, Authentication authentication){
        return ResponseEntity.ok(userService.update(userReceiveDto, authService.extractUserId(authentication)));
    }

}
