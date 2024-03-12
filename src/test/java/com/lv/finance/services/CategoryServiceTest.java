package com.lv.finance.services;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.CategoryDto;
import com.lv.finance.entities.user.PersonalInformation;
import com.lv.finance.entities.user.User;
import com.lv.finance.entities.user.enums.UserRole;
import com.lv.finance.entities.wallet.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Test
    public void testSaveCategory(){
        User user = getUser("testcategory@example.com");

        CategoryDto categoryDto = addCategory(user.getId(), "Test category");

        assertNotNull(categoryDto.getId());
    }

    @Test
    public void testFindAll(){
        User user = getUser("testfindallcategory@example.com");

        addCategory(user.getId(), "first category");
        addCategory(user.getId(), "second category");

        PageResponse<CategoryDto> response = categoryService.findAll(0, 10, "name", "asc", user.getId());

        assertNotNull(response);
        assertNotNull(response.getContent());
        assertNotNull(response.getContent().get(0));

        assertEquals(2, response.getTotalElements());

        assertEquals("first category", response.getContent().get(0).getName());

    }

    @Test
    public void testFindById(){
        User user = getUser("testfindbyid@example.com");

        CategoryDto categoryDto = addCategory(user.getId(), "Test category");

        CategoryDto response = categoryService.findById(categoryDto.getId(), user.getId());

        assertNotNull(response);
        assertEquals(categoryDto.getId(), response.getId());
    }

    @Test
    public void testUpdate(){
        User user = getUser("testupdate@example.com");

        CategoryDto categoryDto = addCategory(user.getId(), "Test category");

        CategoryDto updatedCategory = categoryService.update(categoryDto.getId(),
                CategoryDto.builder()
                        .name("Updated category")
                        .description("Updated category description")
                        .build(), user.getId());

        assertNotNull(updatedCategory);
        assertEquals("Updated category", updatedCategory.getName());
        assertEquals("Updated category description", updatedCategory.getDescription());
    }

    @Test
    public void testDelete(){
        User user = getUser("testdelete@example.com");

        CategoryDto categoryDto = addCategory(user.getId(), "Test category");

        categoryService.deleteById(categoryDto.getId(), user.getId());


        try {
            categoryService.findById(categoryDto.getId(), user.getId());
        } catch (Exception e) {
            assertEquals("Category not found", e.getMessage());
        }
    }

    private CategoryDto addCategory(Long userId, String name){
        return categoryService.save(
                CategoryDto.builder()
                        .name(name)
                        .description("Test dategory description")
                        .build()
                , userId);
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
        user.setWallet(Wallet.builder()
                .balance(BigDecimal.ZERO)
                .user(user).build());
        return userService.save(user);
    }
}
