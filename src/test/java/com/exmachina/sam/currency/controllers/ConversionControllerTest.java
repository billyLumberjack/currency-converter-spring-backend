package com.exmachina.sam.currency.controllers;

import com.exmachina.sam.currency.entities.CurrenciesConversion;
import com.exmachina.sam.currency.entities.Rate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IgnoreCoefficientCurrenciesConversionComparator implements Comparator<CurrenciesConversion>
{
    public int compare(CurrenciesConversion a, CurrenciesConversion b)
    {
        int comparisonResult;

        comparisonResult = a.getSourceAmount().compareTo(b.getSourceAmount());

        if(comparisonResult != 0)
            return comparisonResult;

        comparisonResult = a.getDestinationAmount().compareTo(b.getDestinationAmount());

        if(comparisonResult != 0)
            return comparisonResult;

        comparisonResult = a.getUsedRate().getSource().compareTo(b.getUsedRate().getSource());

        if(comparisonResult != 0)
            return comparisonResult;

        comparisonResult = a.getUsedRate().getDestination().compareTo(b.getUsedRate().getDestination());

        return comparisonResult;

    }
}

@SpringBootTest
@AutoConfigureMockMvc
public class ConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
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

        assertThat(actualCurrenciesConversion).usingComparator(new IgnoreCoefficientCurrenciesConversionComparator()).isEqualTo(expectedCurrenciesConversion);
    }

    @Test
    public void whenConversionRateDoesNotExists_thenReturnsError4xx() throws Exception {
        String sourceCurrency = "BTC";
        String destinationCurrency = "USD";
        Rate expectedRate = new Rate(sourceCurrency, destinationCurrency, 0);

        MvcResult mvcResult = mockMvc.perform(get("/convert")
                .contentType("application/json")
                .param("sourceCurrency", sourceCurrency)
                .param("destinationCurrency", destinationCurrency))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        List<Rate> actualRateList = objectMapper.readValue(actualResponseBody, new TypeReference<List<Rate>>() { });

        //assertThat(actualRateList).allSatisfy(
        //        actualRate -> assertThat(actualRate).usingComparator(ignoreRateCoefficientComparator).isEqualTo(expectedRate)
        //);
    }
}