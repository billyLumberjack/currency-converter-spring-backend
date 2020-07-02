package com.exmachina.sam.currency.tests.rates;

import com.exmachina.sam.currency.tests.AbstractControllerTest;
import com.exmachina.sam.currency.rates.Rate;
import com.exmachina.sam.currency.security.UserRoles;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;


import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RateControllerTest extends AbstractControllerTest {

    @Test
    @WithMockUser(roles = UserRoles.STRING_ADMIN)
    public void whenSourceAndDestinationProvided_thenReturnsConsistentData() throws Exception {
        String sourceCurrency = "BTC";
        String destinationCurrency = "USD";
        Rate expectedRate = new Rate(sourceCurrency, destinationCurrency, BigDecimal.ZERO);

        MvcResult mvcResult = mockMvc.perform(get("/rate")
                .contentType("application/json")
                .param("sourceCurrency", sourceCurrency)
                .param("destinationCurrency", destinationCurrency))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        List<Rate>  actualRateList = objectMapper.readValue(actualResponseBody, new TypeReference<List<Rate>>() { });

        assertThat(actualRateList).allSatisfy(
                actualRate -> assertThat(actualRate).usingComparator(ignoreRateCoefficientComparator).isEqualTo(expectedRate)
        );
    }

    @Test
    @WithMockUser(roles = UserRoles.STRING_ADMIN)
    public void whenOnlySourceIsProvided_thenReturnsConsistentData() throws Exception {
        String sourceCurrency = "BTC";
        String destinationCurrency = "XXX";
        Rate expectedRate = new Rate(sourceCurrency, destinationCurrency, BigDecimal.ZERO);

        MvcResult mvcResult = mockMvc.perform(get("/rate")
                .contentType("application/json")
                .param("sourceCurrency", sourceCurrency))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        List<Rate>  actualRateList = objectMapper.readValue(actualResponseBody, new TypeReference<List<Rate>>() { });

        assertThat(actualRateList).allSatisfy(
                actualRate -> assertThat(actualRate).usingComparator(ignoreRateCoefficientAndSourceComparator).isEqualTo(expectedRate)
        );
    }

    @Test
    @WithMockUser(roles = UserRoles.STRING_ADMIN)
    public void whenOnlyDestinationIsProvided_thenReturnsConsistentData() throws Exception {
        String sourceCurrency = "XXX";
        String destinationCurrency = "USD";
        Rate expectedRate = new Rate(sourceCurrency, destinationCurrency, BigDecimal.ZERO);

        MvcResult mvcResult = mockMvc.perform(get("/rate")
                .contentType("application/json")
                .param("destinationCurrency", destinationCurrency))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        List<Rate>  actualRateList = objectMapper.readValue(actualResponseBody, new TypeReference<List<Rate>>() { });

        assertThat(actualRateList).allSatisfy(
                actualRate -> assertThat(actualRate).usingComparator(ignoreRateCoefficientAndDestinationComparator).isEqualTo(expectedRate)
        );
    }

    @Test
    @WithMockUser(roles = UserRoles.STRING_ADMIN)
    public void whenSourceNorDestinationAreProvided_thenReturnsAllData() throws Exception {

        Class<Rate> expectedItemsClass = Rate.class;

        MvcResult mvcResult = mockMvc.perform(get("/rate")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        List<Rate>  actualRateList = objectMapper.readValue(actualResponseBody, new TypeReference<List<Rate>>() { });

        assertThat(actualRateList).allSatisfy(
                actualRate -> assertThat(actualRate).isInstanceOf(expectedItemsClass)
        );
    }

    @Test
    @WithMockUser(roles = UserRoles.STRING_ADMIN)
    public void whenRateDoesNotExist_thenReturns4xx() throws Exception {
        String sourceCurrency = "XXXXX";
        String destinationCurrency = "YYYYY";
        Rate expectedRate = new Rate(sourceCurrency, destinationCurrency, BigDecimal.ZERO);

        mockMvc.perform(get("/rate")
                .contentType("application/json")
                .param("sourceCurrency", sourceCurrency)
                .param("destinationCurrency", destinationCurrency))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @Override
    public void whenEndpointPromptedWithoutAuth_thenReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/rate")
                .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }
}
