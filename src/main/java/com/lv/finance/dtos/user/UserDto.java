package com.lv.finance.dtos.user;

import com.lv.finance.dtos.wallet.WalletDto;
import com.lv.finance.entities.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String role;

    private PersonalInformationDto personalInformation;

    private WalletDto wallet;

    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().toString();
        this.personalInformation = new PersonalInformationDto(user.getPersonalInformation());
        this.wallet = new WalletDto(user.getWallet());
    }
}
