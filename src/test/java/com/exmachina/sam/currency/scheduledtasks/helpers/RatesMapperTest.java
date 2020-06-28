package com.exmachina.sam.currency.scheduledtasks.helpers;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;


@SpringBootTest
public class RatesMapperTest {

    @Test
    public void mapThirdPartyRatesToRates(){
        ThirdPartyRates thirdPartyRates = createThirdPartyRatesWithFakes();
        List<Rate> actualMappedThirdPartyRates = RatesMapper.mapThirdPartyRatesToRates(thirdPartyRates);

        assertThat(actualMappedThirdPartyRates)
                .extracting(
                        rate -> rate.getSource(),
                        rate -> rate.getDestination(),
                        rate -> rate.getCoefficient())
                .containsExactlyInAnyOrder(
                        tuple("source_currency_1" , "destination_currency_1" , 3.14),
                        tuple("source_currency_1" , "destination_currency_2" , 1.59),
                        tuple("source_currency_1" , "destination_currency_3" , 2.65),
                        tuple("source_currency_2" , "destination_currency_1" , 3.14),
                        tuple("source_currency_2" , "destination_currency_2" , 1.59),
                        tuple("source_currency_2" , "destination_currency_3" , 2.65)
                );
    }

    private ThirdPartyRates createThirdPartyRatesWithFakes(){

        LinkedHashMap<String, Double> destinationsAndCoefficients = new LinkedHashMap<String, Double>()
        {{
            put("destination_currency_1", 3.14);
            put("destination_currency_2", 1.59);
            put("destination_currency_3", 2.65);
        }};

        return new ThirdPartyRates(){{
           put("source_currency_1", destinationsAndCoefficients);
           put("source_currency_2", destinationsAndCoefficients);
        }};

    }
}