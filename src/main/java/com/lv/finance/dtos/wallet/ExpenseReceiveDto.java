package com.lv.finance.dtos.wallet;

import com.lv.finance.entities.wallet.Expense;
import com.lv.finance.entities.wallet.enums.PaymentMethod;
import com.lv.finance.exceptions.FinanceException;
import com.lv.finance.util.DateUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExpenseReceiveDto {

    @NotBlank(message = "The name is required")
    private  String name;

    @NotNull(message = "The amount is required")
    private BigDecimal amount;

    @NotBlank(message = "The payment method is required")
    private String paymentMethod;

    @NotBlank(message = "The date time is required")
    @Pattern(regexp = DateUtil.LOCAL_DATE_TIME_REGEX, message = "Invalid date time, the correct format is " + DateUtil.LOCAL_DATE_TIME_FORMAT)
    private String dateTime;

    @NotNull
    private Long categoryId;

    public Expense convertToExpense(){
        try {
            return Expense.builder()
                    .name(this.name)
                    .amount(this.amount)
                    .paymentMethod(PaymentMethod.valueOf(this.paymentMethod.toUpperCase()))
                    .dateTime(DateUtil.formatStringToLocalDateTime(this.dateTime))
                    .build();
        } catch (IllegalArgumentException e) {
            throw new FinanceException("Invalid payment method, the correct methods are 'credit' and 'debit'", HttpStatus.BAD_REQUEST);
        }
    }
}
