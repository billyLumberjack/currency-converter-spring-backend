package com.exmachina.sam.currency.tests.conversion;


import com.exmachina.sam.currency.conversion.ConversionController;
import com.exmachina.sam.currency.conversion.CurrenciesConversion;
import com.exmachina.sam.currency.conversion.CurrencyConverter;
import com.exmachina.sam.currency.rates.Rate;
import com.exmachina.sam.currency.rates.RateNotFoundException;
import com.exmachina.sam.currency.rates.RateService;
import com.exmachina.sam.currency.security.UserRoles;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class ConversionControllerUnitTest {

    private final RateService mockedRateService = mock(RateService.class);
    private final CurrencyConverter mockedCurrencyConverter = mock(CurrencyConverter.class);
    private final String sourceCurrency = "source_currency";
    private final String destinationCurrency = "destination_currency";

    @Test
    public void whenConversionIsPrompted_thenReturnsConverterResult() throws Exception {
        Rate expectedRate = new Rate(sourceCurrency, destinationCurrency, BigDecimal.TEN);
        CurrenciesConversion expectedCurrenciesConversion = new CurrenciesConversion(
                expectedRate,
                BigDecimal.TEN,
                BigDecimal.TEN.multiply(BigDecimal.TEN)
        );

        when(mockedRateService.findBySourceAndDestination(anyString(), anyString())).thenReturn(expectedRate);
        when(mockedCurrencyConverter.convertByRateAndAmount(any(Rate.class), any(BigDecimal.class))).thenReturn(expectedCurrenciesConversion);

        ConversionController toTestController = new ConversionController(mockedRateService, mockedCurrencyConverter);
        CurrenciesConversion actualCurrenciesConversion = toTestController.convertAmount(sourceCurrency, destinationCurrency, BigDecimal.TEN);

        assertThat(actualCurrenciesConversion).isEqualTo(expectedCurrenciesConversion);
    }

    @Test
    public void onConversionIfRateServiceThrowsException_thenThrowsResponseStatusException() throws Exception {
        RateNotFoundException expectedRateNotFoundException = mock(RateNotFoundException.class);
        when(mockedRateService.findBySourceAndDestination(anyString(), anyString())).thenThrow(expectedRateNotFoundException);
        ConversionController toTestController = new ConversionController(mockedRateService, mockedCurrencyConverter);
        assertThatThrownBy(() -> toTestController.convertAmount(sourceCurrency, destinationCurrency, BigDecimal.TEN))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Rate Not Found");
    }
}
