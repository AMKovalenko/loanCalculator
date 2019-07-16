package ru.engine;

import lombok.Builder;
import lombok.Getter;
import ru.dto.ScheduleItem;
import ru.request.GetScheduleRequest;
import ru.utils.CalculationUtils;
import ru.utils.InterestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.math.BigDecimal.ONE;
import static ru.utils.CalculationUtils.ZERO;

@Builder
public class ScheduleEngine {

    private AtomicInteger counter;
    private AtomicInteger remainedTerm;
    private BigDecimal remainedMainDebt;
    private BigDecimal yearInterestRate;
    private LocalDate initialDate;
    private BigDecimal paymentAmount;

    @Getter
    @Builder.Default
    private List<ScheduleItem> result;

    public void buildFullSchedule() {
        LocalDate startDate = initialDate.plusDays(1);
        LocalDate endDate = initialDate.plusMonths(1);

        while (remainedTerm.getAndDecrement() > 1) {
            createOrdinaryItem(startDate, endDate);
            startDate = endDate.plusDays(1);
            endDate = endDate.plusMonths(1);
        }
        createLastItem(startDate, endDate);
    }

    private void createOrdinaryItem(LocalDate startDate, LocalDate endDate) {
        BigDecimal interestAmount = computeInterestAmount(startDate, endDate);
        BigDecimal mainDebt = paymentAmount.subtract(interestAmount);
        remainedMainDebt = remainedMainDebt.subtract(mainDebt);

        result.add(
            buildItem(endDate, paymentAmount, mainDebt, interestAmount)
        );
    }

    private void createLastItem(LocalDate startDate, LocalDate endDate) {
        BigDecimal interestAmount = computeInterestAmount(startDate, endDate);
        BigDecimal lastPaymentAmount = remainedMainDebt.add(interestAmount);
        BigDecimal mainDebt = remainedMainDebt;
        remainedMainDebt = ZERO;
        result.add(
                buildItem(endDate, lastPaymentAmount, mainDebt, interestAmount)
        );
    }


    private ScheduleItem buildItem(LocalDate paymentDate, BigDecimal paymentAmount, BigDecimal mainDebt, BigDecimal interest) {

        return ScheduleItem.builder()
                .number(counter.getAndIncrement())
                .paymentDate(paymentDate)
                .paymentAmount(paymentAmount)
                .mainDebt(mainDebt)
                .interest(interest)
                .remainMainDebt(remainedMainDebt)
                .build();
    }

    private static BigDecimal calculatePaymentAmount(GetScheduleRequest request) {
        BigDecimal yearRate = CalculationUtils.divide(request.getYearInterestRate(), BigDecimal.valueOf(100));
        BigDecimal interestForPeriod = InterestUtils.getMonthlyInterest(yearRate);
        BigDecimal commonFormulaPart = (interestForPeriod.add(ONE)).pow(request.getTerm());
        BigDecimal numerator = commonFormulaPart.multiply(interestForPeriod);
        BigDecimal denominator = commonFormulaPart.subtract(ONE);
        BigDecimal coefficient = CalculationUtils.divide(numerator, denominator);
        return CalculationUtils.multiplyAndGetAsMoney(request.getAmount(), coefficient);
    }

    private BigDecimal computeInterestAmount(LocalDate startDate, LocalDate finishDate) {
        return InterestUtils.calculateAmountForPeriod(startDate, finishDate, remainedMainDebt, yearInterestRate);
    }

    public static ScheduleEngine init(GetScheduleRequest request) {
        return ScheduleEngine.builder()
                .remainedMainDebt(request.getAmount())
                .remainedTerm(new AtomicInteger(request.getTerm()))
                .yearInterestRate(CalculationUtils.divide(request.getYearInterestRate(), BigDecimal.valueOf(100)))
                .initialDate(request.getLoanDate()/*.plusDays(1)*/)
                .paymentAmount(calculatePaymentAmount(request))
                .counter(new AtomicInteger(1))
                .result(new ArrayList<>())
                .build();
    }
}
