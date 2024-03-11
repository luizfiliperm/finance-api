package com.lv.finance.services;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.user.PersonalInformationDto;
import com.lv.finance.dtos.user.UserDeleteDto;
import com.lv.finance.dtos.user.UserDto;
import com.lv.finance.dtos.user.UserReceiveDto;
import com.lv.finance.entities.user.PersonalInformation;
import com.lv.finance.entities.user.User;
import com.lv.finance.entities.user.enums.UserRole;
import com.lv.finance.util.DateUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {


    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    public void testSaveUser(){
        User user = getUser("test@example.com");

        assertNotNull(user.getId());

    }

    @Test
    @Order(2)
    public void testFindAll(){
        getUser("test2@example.com");


        PageResponse<UserDto> response = userService.findAll(0, 10, "name", "asc");

        assertNotNull(response);
        assertNotNull(response.getContent());
        assertNotNull(response.getContent().get(0));

        assertEquals(2, response.getTotalElements());

        assertEquals("Test User", response.getContent().get(0).getName());
    }

    @Test
    public void testLoadUserByEmail() {
        User user = (User) userService.loadUserByEmail("test@example.com");
        assertNotNull(user);
    }

    @Test
    public void testUpdateUser(){
        User user = getUser("updateUser@example.com");

        UserReceiveDto userReceiveDto = UserReceiveDto.builder()
                .name("Updated User")
                .currentPassword("securePassword123")
                .newPassword("otherPassword")
                .personalInformation(new PersonalInformationDto(
                        PersonalInformation.builder()
                        .phoneNumber("888888888")
                        .birthDate(LocalDate.of(1997, 1, 1))
                        .nationality("American").build()))
                .build();

        UserDto updatedUser = userService.update(userReceiveDto, user.getId());

        assertNotEquals(user.getName(), updatedUser.getName());
        assertNotEquals(user.getPersonalInformation().getPhoneNumber(), updatedUser.getPersonalInformation().getPhoneNumber());
        assertNotEquals(user.getPersonalInformation().getNationality(), updatedUser.getPersonalInformation().getNationality());
        assertNotEquals(user.getPersonalInformation().getBirthDate(), DateUtil.formatStringToLocalDate(updatedUser.getPersonalInformation().getBirthDate()));
    }

    @Test
    public void testDeleteUser(){
        User user = getUser("TestDelete@example.com");

        UserDeleteDto userDeleteDto =  UserDeleteDto.builder()
                .password("securePassword123")
                .confirmPassword("securePassword123")
                .build();


        userService.delete(userDeleteDto, user.getId());

        try {
            User deletedUser = (User) userService.loadUserByEmail("TestDelete@example.com");
        } catch (Exception e) {
            assertEquals("User not found", e.getMessage());
        }

    }

    @Test
    public void testDeleteWithInvalidPassword(){
        User user = getUser("testDeleteWrongPassword@example.com");

        UserDeleteDto userDeleteDto =  UserDeleteDto.builder()
                .password("wrongPassword")
                .confirmPassword("wrongPassword")
                .build();

        try {
            userService.delete(userDeleteDto, user.getId());
        } catch (Exception e) {
            assertEquals("Invalid password", e.getMessage());
        }
    }

    @Test
    public void testDeleteWithDifferentPasswords() {
        User user = getUser("testDeleteDifferentPasswords@example.com");

        UserDeleteDto userDeleteDto = UserDeleteDto.builder()
                .password("securePassword123")
                .confirmPassword("differentPassword")
                .build();

        try {
            userService.delete(userDeleteDto, user.getId());
        } catch (Exception e) {
            assertEquals("Passwords don't match", e.getMessage());

        }
    }

    private User getUser(String email) {
        User user = User.builder()
                .name("Test User")
                .email(email)
                .password("securePassword123")
                .role(UserRole.ROLE_USER)
                .personalInformation(PersonalInformation.builder()
                        .birthDate(LocalDate.of(1995, 1, 1))
                        .phoneNumber("999999999")
                        .nationality("Brazilian")
                        .build())
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

}
