package com.exmachina.sam.currency.controllers;

import com.exmachina.sam.currency.comparator.IgnoreCoefficientCurrenciesConversionComparator;
import com.exmachina.sam.currency.entities.CurrenciesConversion;
import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.inmemoryauth.security.UserRoles;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConversionControllerTest extends AbstractControllerTest{

    @Test
    @WithMockUser(roles = UserRoles.STRING_ADMIN)
    public void whenConversionIsPrompted_thenReturnsConsistentData() throws Exception {
        String sourceCurrency = "BTC";
        String destinationCurrency = "USD";
        Double amountToConvert = 10.0;
        Rate expectedRate = new Rate(sourceCurrency, destinationCurrency, 0);

        MvcResult mvcResult = mockMvc.perform(get("/convert")
                .contentType("application/json")
                .param("sourceCurrency", sourceCurrency)
                .param("destinationCurrency", destinationCurrency)
                .param("amount", amountToConvert.toString()))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        CurrenciesConversion actualCurrenciesConversion = objectMapper.readValue(actualResponseBody, CurrenciesConversion.class);

        CurrenciesConversion expectedCurrenciesConversion = new CurrenciesConversion(
                expectedRate,
                amountToConvert,
                amountToConvert * actualCurrenciesConversion.getUsedRate().getCoefficient()
                );

        assertThat(actualCurrenciesConversion)
                .usingComparator(new IgnoreCoefficientCurrenciesConversionComparator())
                .isEqualTo(expectedCurrenciesConversion);
    }

    @Test
    @WithMockUser(roles = UserRoles.STRING_ADMIN)
    public void whenConversionRateDoesNotExists_thenReturnsError4xx() throws Exception {
        String sourceCurrency = "XXXXX";
        String destinationCurrency = "YYYYY";
        Double amountToConvert = 10.0;
        Rate expectedRate = new Rate(sourceCurrency, destinationCurrency, 0);

        mockMvc.perform(get("/convert")
                .contentType("application/json")
                .param("sourceCurrency", sourceCurrency)
                .param("destinationCurrency", destinationCurrency)
                .param("amount", amountToConvert.toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Override
    public void whenEndpointPromptedWithoutAuth_thenReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/convert")
                .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }
}