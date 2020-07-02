package com.exmachina.sam.currency.controllers;

import com.exmachina.sam.currency.entities.Rate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Comparator;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected final Comparator<Rate> ignoreRateCoefficientComparator =
            Comparator.comparing(Rate::getSource).thenComparing(Rate::getDestination);

    protected final Comparator<Rate> ignoreRateCoefficientAndSourceComparator =
            Comparator.comparing(Rate::getSource);

    protected final Comparator<Rate> ignoreRateCoefficientAndDestinationComparator =
            Comparator.comparing(Rate::getDestination);

    abstract public void whenEndpointPromptedWithoutAuth_thenReturnsUnauthorized() throws Exception;

}
