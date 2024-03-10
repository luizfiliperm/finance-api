package com.lv.finance.dtos.user;

import com.lv.finance.entities.user.PersonalInformation;
import com.lv.finance.util.DateUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonalInformationDto {

    @NotBlank(message = "The phone number is required")
    @Pattern(regexp = "^[0-9]{9}$", message = "Invalid phone number, the correct format is 9 digits")
    private String phoneNumber;

    @NotBlank(message = "The birth date is required")
    @Pattern(regexp = DateUtil.DATE_REGEX, message = "Invalid birth date, the correct format is " + DateUtil.DATE_FORMAT)
    private String birthDate;

    @NotBlank(message = "The nationality is required")
    private String nationality;

    public PersonalInformationDto(PersonalInformation personalInformation){
        this.phoneNumber = personalInformation.getPhoneNumber();
        this.birthDate = DateUtil.formatLocalDateToString(personalInformation.getBirthDate());
        this.nationality = personalInformation.getNationality();
    }

    public PersonalInformation convertToPersonalInformation() {
        return PersonalInformation.builder()
                .phoneNumber(this.phoneNumber)
                .birthDate(DateUtil.formatStringToLocalDate(this.birthDate))
                .nationality(this.nationality)
                .build();
    }
}
