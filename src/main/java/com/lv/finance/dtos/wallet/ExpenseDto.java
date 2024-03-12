package com.lv.finance.dtos.wallet;

import com.lv.finance.entities.wallet.Expense;
import com.lv.finance.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExpenseDto {

    private Long id;

    private String name;

    private BigDecimal amount;

    private String paymentMethod;

    private String dateTime;

    private CategoryDto category;

    public ExpenseDto(Expense expense){
        this.id = expense.getId();
        this.name = expense.getName();
        this.amount = expense.getAmount();
        this.paymentMethod = expense.getPaymentMethod().toString();
        this.dateTime = DateUtil.formatLocalDateTimeToString(expense.getDateTime());
        this.category = new CategoryDto(expense.getCategory());
    }
}
