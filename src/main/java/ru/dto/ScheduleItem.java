package ru.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ScheduleItem {

    private Integer number;

    private LocalDate paymentDate;

    private BigDecimal paymentAmount;

    private BigDecimal mainDebt;

    private BigDecimal interest;

    private BigDecimal remainMainDebt;

}
