package com.exmachina.sam.currency.conversion;

import com.exmachina.sam.currency.rates.Rate;

import java.math.BigDecimal;

public class CurrencyConverter {
    public CurrenciesConversion convertByRateAndAmount(Rate rateForConversion, BigDecimal sourceAmount) {
        BigDecimal conversionResult = sourceAmount.multiply(rateForConversion.getCoefficient());
        return new CurrenciesConversion(
                rateForConversion,
                sourceAmount,
                conversionResult
        );
    }
}
