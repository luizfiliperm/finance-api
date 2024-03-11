package com.lv.finance.dtos.user;

import com.lv.finance.entities.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserReceiveDto {

    @NotBlank(message = "The name is required")
    private String name;

    @NotBlank(message = "The email is required")
    private String email;

    @NotBlank(message = "The current password is required")
    private String currentPassword;

    @NotBlank(message = "The new password is required")
    private String newPassword;

    @Valid
    @NotNull(message = "The personal information is required")
    private PersonalInformationDto personalInformation;

    public User convertToUser(){
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.newPassword)
                .personalInformation(this.personalInformation.convertToPersonalInformation())
                .build();
    }
}
