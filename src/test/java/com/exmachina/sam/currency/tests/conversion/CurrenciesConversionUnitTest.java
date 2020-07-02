package com.exmachina.sam.currency.tests.conversion;


import com.exmachina.sam.currency.conversion.ConversionController;
import com.exmachina.sam.currency.conversion.CurrenciesConversion;
import com.exmachina.sam.currency.conversion.CurrencyConverter;
import com.exmachina.sam.currency.rates.Rate;
import com.exmachina.sam.currency.rates.RateNotFoundException;
import com.exmachina.sam.currency.rates.RateService;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.*;
import static com.exmachina.sam.currency.tests.conversion.custom.assertion.Assertions.assertThat;

public class CurrenciesConversionUnitTest {

    private final RateService mockedRateService = mock(RateService.class);
    private final CurrencyConverter mockedCurrencyConverter = mock(CurrencyConverter.class);
    private final String sourceCurrency = "source_currency";
    private final String destinationCurrency = "destination_currency";

    @Test
    public  void whenComputingActualConversion_thenReturnsRateAndAmountMoltiplication(){
        Rate expectedRateForConversion = new Rate(sourceCurrency, destinationCurrency, BigDecimal.TEN);
        BigDecimal sourceAmountToConvert = BigDecimal.TEN;

        CurrenciesConversion expectedCurrenciesConversion = new CurrenciesConversion(
                expectedRateForConversion,
                sourceAmountToConvert,
                BigDecimal.TEN.multiply(BigDecimal.TEN)
        );

        CurrencyConverter currencyConverter = new CurrencyConverter();
        CurrenciesConversion actualCurrenciesConversion = currencyConverter.convertByRateAndAmount(
                expectedRateForConversion,
                sourceAmountToConvert
        );
        assertThat(actualCurrenciesConversion)
                .hasDestinationAmount(expectedCurrenciesConversion.getDestinationAmount())
                .hasSourceAmount(expectedCurrenciesConversion.getSourceAmount())
                .hasUsedRate(expectedCurrenciesConversion.getUsedRate());
    }
}
