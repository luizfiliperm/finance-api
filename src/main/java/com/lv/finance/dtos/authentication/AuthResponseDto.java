package com.lv.finance.dtos.authentication;

import com.lv.finance.dtos.user.PersonalInformationDto;
import com.lv.finance.dtos.user.UserDto;
import com.lv.finance.entities.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthResponseDto {

    private UserDto user;

    private String token;

    public AuthResponseDto(User user, String token){
        this.user = new UserDto(user);
        this.token = token;
    }
}
