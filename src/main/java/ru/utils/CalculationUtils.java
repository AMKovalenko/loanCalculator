package ru.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtils {

    private final static RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private final static Integer CALCULATION_SCALE = 12;
    private final static Integer MONEY_SCALE = 2;
    public final static BigDecimal ZERO = BigDecimal.ZERO.setScale(MONEY_SCALE);

    public static BigDecimal divide(BigDecimal numerator, BigDecimal denominator){
        return numerator.divide(denominator, CALCULATION_SCALE, ROUNDING_MODE);
    }

    public static BigDecimal getAsMoney(BigDecimal value){
        return value.setScale(MONEY_SCALE, ROUNDING_MODE);
    }

    public static BigDecimal multiplyAndGetAsMoney(BigDecimal firstValue, BigDecimal secondValue){
        return getAsMoney(firstValue.multiply(secondValue));
    }
}
