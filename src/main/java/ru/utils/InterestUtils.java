package ru.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InterestUtils {

    private final static BigDecimal LEAP_YEAR_DAYS = BigDecimal.valueOf(366);
    private final static BigDecimal COMMON_YEAR_DAYS = BigDecimal.valueOf(365);
    private final static BigDecimal MONTHS_A_YEAR = BigDecimal.valueOf(12);

    public static BigDecimal getDailyRateByYearRateOnDate(BigDecimal yearInterestRate, LocalDate date) {

        BigDecimal daysPerYear = date.isLeapYear() ? LEAP_YEAR_DAYS : COMMON_YEAR_DAYS;
        return yearInterestRate.divide(daysPerYear, 8, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal getDailyDebt(BigDecimal yearInterestRate, LocalDate date, BigDecimal mainDebt) {

        BigDecimal dailyRate = getDailyRateByYearRateOnDate(yearInterestRate, date);
        return CalculationUtils.multiplyAndGetAsMoney(dailyRate, mainDebt);
    }

    public static BigDecimal calculateAmountForPeriod(LocalDate startDate,
                                                      LocalDate endDate,
                                                      BigDecimal mainDebt,
                                                      BigDecimal yearInterestRate) {
        LocalDate date = startDate;
        BigDecimal overallInterest = CalculationUtils.ZERO;

        while (!date.isAfter(endDate)) {
            BigDecimal interestDebtOnDate = getDailyDebt(yearInterestRate, date, mainDebt);
            overallInterest = overallInterest.add(interestDebtOnDate);
            date = date.plusDays(1);
        }
        return overallInterest;
    }

    public static BigDecimal getMonthlyInterest(BigDecimal yearRate) {
        return CalculationUtils.divide(yearRate, MONTHS_A_YEAR);
    }

}
