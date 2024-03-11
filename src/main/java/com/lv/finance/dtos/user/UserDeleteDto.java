package com.lv.finance.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDeleteDto {

    @NotBlank(message = "The password is required")
    private String password;

    @NotBlank(message = "The confirm password is required")
    private String confirmPassword;
}
