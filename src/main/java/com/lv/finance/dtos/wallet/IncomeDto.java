package com.lv.finance.dtos.wallet;

import com.lv.finance.entities.wallet.Income;
import com.lv.finance.util.DateUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        this.name = income.getName();
        this.value = income.getValue();
        this.incomeType = income.getIncomeType().toString();
        this.dateTime = DateUtil.formatLocalDateTimeToString(income.getDateTime());
    }

}
