package ru.response.builder;

import ru.dto.ScheduleItem;
import ru.response.GetScheduleResponse;

import java.math.BigDecimal;
import java.util.List;

public class GetScheduleResponseBuilder {

    public static synchronized GetScheduleResponse build(List<ScheduleItem> schedule) {
        BigDecimal totalInterest = getTotalInterest(schedule);
        BigDecimal totalPaymentAmount = getTotalPaymentAmount(schedule);
        BigDecimal paymentAmount = getPaymentAmount(schedule);

        return GetScheduleResponse.builder()
                .totalInterest(totalInterest)
                .totalPaymentAmount(totalPaymentAmount)
                .monthlyPayment(paymentAmount)
                .schedules(schedule)
                .build();
    }

    private static BigDecimal getPaymentAmount(List<ScheduleItem> schedule) {
        return schedule.stream()
                .findFirst()
                .map(ScheduleItem::getPaymentAmount)
                .orElse(BigDecimal.ZERO);
    }

    private static BigDecimal getTotalPaymentAmount(List<ScheduleItem> schedule) {
        return schedule.stream()
                .map(ScheduleItem::getPaymentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal getTotalInterest(List<ScheduleItem> schedule) {
        return schedule.stream()
                .map(ScheduleItem::getInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
