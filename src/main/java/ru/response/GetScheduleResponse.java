package ru.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dto.ScheduleItem;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetScheduleResponse {

    private BigDecimal monthlyPayment;
    private BigDecimal totalInterest;
    private BigDecimal totalPaymentAmount;
    private List<ScheduleItem> schedules;
}
