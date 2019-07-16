package ru.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetScheduleRequest {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    @Positive
    private Integer term; // month

    @NotNull
    @Positive
    private BigDecimal yearInterestRate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate loanDate;

}
