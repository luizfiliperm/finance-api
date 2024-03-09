package com.lv.finance.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "personal_information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_Date")
    private LocalDate birthDate;

    @Column(name = "nationality")
    private String nationality;

}
