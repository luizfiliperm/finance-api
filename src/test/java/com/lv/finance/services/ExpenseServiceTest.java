package com.lv.finance.services;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.CategoryDto;
import com.lv.finance.dtos.wallet.ExpenseDto;
import com.lv.finance.dtos.wallet.ExpenseReceiveDto;
import com.lv.finance.dtos.wallet.WalletDto;
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
public class ExpenseServiceTest {

    @Autowired
    ExpenseService expenseService;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    WalletService walletService;

    @Autowired
    CategoryService categoryService;

    @Test
    public void testAddExpense(){
        User user = getUser("addexpenseuser@example.com");
        Long categoryId = getCategoryId(user.getId());

        ExpenseDto expenseDto = addExpense(user.getId(), categoryId, "11/04/2024 20:57");

        WalletDto walletDto = walletService.getWallet(user.getId());

        assertNotNull(expenseDto.getId());
        assertEquals(walletDto.getBalance(), expenseDto.getAmount().negate());

    }

    @Test
    public void testGetAll(){
        User user = getUser("testgetallexpenseuser@example.com");

        addExpense(user.getId(), getCategoryId(user.getId()), "12/04/2024 10:05");
        addExpense(user.getId(), getCategoryId(user.getId()), "12/04/2024 10:00");

        PageResponse<ExpenseDto> response = expenseService.findAllExpenses(
                user.getId(),
                0,
                10,
                "dateTime",
                "desc");

        assertNotNull(response);
        assertNotNull(response.getContent());
        assertEquals(2, response.getTotalElements());

        assertEquals("12/04/2024 10:05", response.getContent().get(0).getDateTime());
    }

    @Test
    public void testDelete(){
        User user = getUser("testedeleteexpense@example.com");

        ExpenseDto expenseDto = addExpense(user.getId(), getCategoryId(user.getId()), "12/04/2024 10:05");
        expenseService.deleteExpense(expenseDto.getId(), user.getId());
        WalletDto walletDto = walletService.getWallet(user.getId());

        PageResponse<ExpenseDto> response = expenseService.findAllExpenses(
                user.getId(),
                0,
                10,
                "dateTime",
                "desc");

        assertEquals(0, response.getTotalElements());

        assertEquals(new BigDecimal("0.00"), walletDto.getBalance());
    }

    private Long getCategoryId(Long userId){
        return categoryService.save(
                CategoryDto.builder()
                        .name("Test")
                        .description("Test")
                        .build(),
                userId).getId();
    }

    private ExpenseDto addExpense(Long userId, Long categoryId ,String dateTime){
        ExpenseReceiveDto expenseReceiveDto = ExpenseReceiveDto.builder()
                .name("Test Expense")
                .amount(new BigDecimal("10.00"))
                .paymentMethod("debit")
                .dateTime(dateTime)
                .categoryId(categoryId)
                .build();

        return expenseService.addExpense(expenseReceiveDto, userId);
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
