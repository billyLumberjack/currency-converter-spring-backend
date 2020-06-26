package com.exmachina.sam.currency.scheduledtasks.helpers;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class RatesMapperTest {

    @Test
    public void mapThirdPartyRatesToRates(){
        ThirdPartyRates thirdPartyRates = createThirdPartyRatesWithFakes();
        List<Rate> expectedMappedThirdPartyRates = new ArrayList<Rate>(){{
            add(new Rate("source_currency_1" , "target_currency_1" , 3.14));
            add(new Rate("source_currency_1" , "target_currency_2" , 1.59));
            add(new Rate("source_currency_1" , "target_currency_3" , 2.65));
            add(new Rate("source_currency_2" , "target_currency_1" , 3.14));
            add(new Rate("source_currency_2" , "target_currency_2" , 1.59));
            add(new Rate("source_currency_2" , "target_currency_3" , 2.65));
        }};
        List<Rate> actualMappedThirdPartyRates = RatesMapper.mapThirdPartyRatesToRates(thirdPartyRates);

        assertThat(actualMappedThirdPartyRates).containsAll(expectedMappedThirdPartyRates);
    }

    private ThirdPartyRates createThirdPartyRatesWithFakes(){

        LinkedHashMap<String, Double> targetsAndCoefficients = new LinkedHashMap<String, Double>()
        {{
            put("target_currency_1", 3.14);
            put("target_currency_2", 1.59);
            put("target_currency_3", 2.65);
        }};

        return new ThirdPartyRates(){{
           put("source_currency_1", targetsAndCoefficients);
           put("source_currency_2", targetsAndCoefficients);
        }};

    }
}