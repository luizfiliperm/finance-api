package com.lv.finance.services;

import com.lv.finance.dtos.authentication.AuthResponseDto;
import com.lv.finance.dtos.authentication.LoginDto;
import com.lv.finance.dtos.authentication.RegisterDto;
import com.lv.finance.dtos.user.PersonalInformationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Test
    public void testRegister(){
        RegisterDto registerDto = RegisterDto.builder()
                .name("Register name test")
                .email("register@example.com")
                .password("securePassword123")
                .personalInformation(new PersonalInformationDto(
                        "888888888",
                        "01/01/1995",
                        "Brazilian"))
                .build();
        AuthResponseDto response = authService.register(registerDto);

        assertNotNull(response);
        assertEquals(registerDto.getName(), response.getUser().getName());
        assertEquals(registerDto.getEmail(), response.getUser().getEmail());
        assertEquals(registerDto.getPersonalInformation().getPhoneNumber(), response.getUser().getPersonalInformation().getPhoneNumber());
        assertEquals(registerDto.getPersonalInformation().getBirthDate(), response.getUser().getPersonalInformation().getBirthDate());
        assertEquals(registerDto.getPersonalInformation().getNationality(), response.getUser().getPersonalInformation().getNationality());

        assertNotNull(response.getToken());
    }

    @Test
    public void testLogin(){
        RegisterDto registerDto = RegisterDto.builder()
                .name("Login name test")
                .email("login@example.com")
                .password("securePassword123")
                .personalInformation(new PersonalInformationDto(
                        "888888888",
                        "01/01/1995",
                        "Brazilian"))
                .build();
        authService.register(registerDto);

        LoginDto loginDto = LoginDto.builder()
                .email("login@example.com")
                .password("securePassword123")
                .build();

        AuthResponseDto response = authService.login(loginDto);

        assertNotNull(response);
        assertEquals(registerDto.getName(), response.getUser().getName());
        assertEquals(registerDto.getEmail(), response.getUser().getEmail());
        assertEquals(registerDto.getPersonalInformation().getPhoneNumber(), response.getUser().getPersonalInformation().getPhoneNumber());
        assertEquals(registerDto.getPersonalInformation().getBirthDate(), response.getUser().getPersonalInformation().getBirthDate());
        assertEquals(registerDto.getPersonalInformation().getNationality(), response.getUser().getPersonalInformation().getNationality());

        assertNotNull(response.getToken());
    }

    @Test
    public void testEmailAlreadyRegisteredException(){
        RegisterDto registerDto = RegisterDto.builder()
                .name("Login name test")
                .email("test@example.com")
                .password("securePassword123")
                .personalInformation(new PersonalInformationDto(
                        "888888888",
                        "01/01/1995",
                        "Brazilian"))
                .build();

        authService.register(registerDto);

        try {
            authService.register(registerDto);
        } catch (Exception e){
            assertEquals("Email already registered", e.getMessage());
        }
    }



}
