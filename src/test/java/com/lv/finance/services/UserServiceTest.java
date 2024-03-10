package com.lv.finance.services;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.user.UserDto;
import com.lv.finance.entities.user.PersonalInformation;
import com.lv.finance.entities.user.User;
import com.lv.finance.entities.user.enums.UserRole;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {


    @Autowired
    UserService userService;


    @Test
    @Order(1)
    public void testSaveUser(){
        User user = User.builder()
                .name("Test User")
                .email("test@example.com")
                .password("securePassword123")
                .role(UserRole.ROLE_USER)
                .personalInformation(PersonalInformation.builder()
                        .birthDate(LocalDate.of(1995, 1, 1))
                        .phoneNumber("999999999")
                        .nationality("Brazilian")
                        .build())
                .build();

        userService.save(user);

        assertNotNull(user.getId());

    }

    @Test
    public void testLoadUserByEmail() {
        User user = (User) userService.loadUserByEmail("test@example.com");
        assertNotNull(user);
    }

    @Test
    public void testFindAll(){
        User user = User.builder()
                .name("Test User")
                .email("test2@example.com")
                .password("securePassword123")
                .role(UserRole.ROLE_USER)
                .personalInformation(PersonalInformation.builder()
                        .birthDate(LocalDate.of(1995, 1, 1))
                        .phoneNumber("999999999")
                        .nationality("Brazilian")
                        .build())
                .build();

        userService.save(user);


        PageResponse<UserDto> response = userService.findAll(0, 10, "name", "asc");

        assertNotNull(response);
        assertNotNull(response.getContent());
        assertNotNull(response.getContent().get(0));

        assertEquals(2, response.getTotalElements());

        assertEquals("Test User", response.getContent().get(0).getName());
    }
}
