package com.lv.finance.controllers;


import com.lv.finance.dtos.user.UserDeleteDto;
import com.lv.finance.dtos.user.UserDto;
import com.lv.finance.dtos.user.UserReceiveDto;
import com.lv.finance.services.AuthService;
import com.lv.finance.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping
    public ResponseEntity<Void> delete(@Valid @RequestBody UserDeleteDto userDeleteDto, Authentication authentication){
        userService.delete(userDeleteDto ,authService.extractUserId(authentication));
        return ResponseEntity.noContent().build();
    }

}
