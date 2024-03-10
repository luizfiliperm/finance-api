package com.lv.finance.dtos.authentication;

import com.lv.finance.dtos.user.PersonalInformationDto;
import com.lv.finance.entities.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterDto {

    @NotNull(message = "The name is required")
    private String name;

    @NotNull(message = "The email is required")
    private String email;

    @NotNull(message = "The password is required")
    private String password;

    @Valid
    @NotNull(message = "The personal information is required")
    private PersonalInformationDto personalInformation;

    public User convertToUser(){
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .personalInformation(this.personalInformation.convertToPersonalInformation())
                .build();
    }
}
