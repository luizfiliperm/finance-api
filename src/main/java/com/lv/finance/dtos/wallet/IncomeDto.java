package com.lv.finance.dtos.wallet;

import com.lv.finance.entities.wallet.Income;
import com.lv.finance.entities.wallet.enums.IncomeType;
import com.lv.finance.exceptions.FinanceException;
import com.lv.finance.util.DateUtil;
import jakarta.validation.constraints.NotBlank;
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
public class IncomeDto {

    private Long id;

    @NotBlank(message = "The name is required")
    private String name;

    @NotBlank(message = "The value is required")
    private BigDecimal value;

    @NotBlank(message = "The income type is required")
    private String incomeType;

    @NotBlank(message = "The date time is required")
    @Pattern(regexp = DateUtil.LOCAL_DATE_TIME_REGEX, message = "Invalid date time, the correct format is " + DateUtil.LOCAL_DATE_TIME_FORMAT)
    private String dateTime;

    public IncomeDto(Income income){
        this.id = income.getId();
        this.name = income.getName();
        this.value = income.getValue();
        this.incomeType = income.getIncomeType().toString();
        this.dateTime = DateUtil.formatLocalDateTimeToString(income.getDateTime());
    }

    public Income convertToIncome(){
        try {
            return Income.builder()
                    .name(this.name)
                    .value(this.value)
                    .incomeType(IncomeType.valueOf(this.incomeType.toUpperCase()))
                    .dateTime(DateUtil.formatStringToLocalDateTime(this.dateTime))
                    .build();
        } catch (IllegalArgumentException e) {
            throw new FinanceException("Invalid income type, the correct types are 'monthly' or 'sporadic'", HttpStatus.BAD_REQUEST);
        }
    }

}
