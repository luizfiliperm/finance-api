package com.lv.finance.services;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.wallet.IncomeDto;
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
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class IncomeServiceTest {

    @Autowired
    IncomeService incomeService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    WalletService walletService;

    @Test
    public void testAddIncome(){
        User user = getUser("addincomeuser@example.com");

        IncomeDto incomeDto = addIncome(user.getId(), "11/04/2024 20:57");

        WalletDto walletDto = walletService.getWallet(user.getId());

        assertNotNull(incomeDto.getId());
        assertEquals(walletDto.getBalance(), incomeDto.getAmount().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testGetAll(){
        User user = getUser("getallincomeuser@example.com");

        addIncome(user.getId(), "12/04/2024 10:05");
        addIncome(user.getId(), "12/04/2024 10:00");

        PageResponse<IncomeDto> response = incomeService.findAllIncomes(user.getId(),
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
    public void testDeleteIncome(){
        User user = getUser("deleteincomeuser@example.com");

        IncomeDto incomeDto = addIncome(user.getId(), "12/04/2024 10:05");


        incomeService.deleteIncome(incomeDto.getId(), user.getId());
        WalletDto walletDto = walletService.getWallet(user.getId());

        PageResponse<IncomeDto> response = incomeService.findAllIncomes(user.getId(),
                0,
                10,
                "dateTime",
                "desc");

        assertEquals(0, response.getTotalElements());
        assertEquals(new BigDecimal("0.00"), walletDto.getBalance());
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
    private IncomeDto addIncome(Long userId, String dateTime){
        IncomeDto incomeDto = IncomeDto.builder()
                .name("Test income")
                .amount(BigDecimal.TEN)
                .incomeType("Monthly")
                .dateTime(dateTime)
                .build();

        return incomeService.addIncome(incomeDto, userId);
    }


}
