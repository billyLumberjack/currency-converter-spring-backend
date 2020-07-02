package com.exmachina.sam.currency.services.converter;

import com.exmachina.sam.currency.entities.CurrenciesConversion;
import com.exmachina.sam.currency.entities.Rate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
