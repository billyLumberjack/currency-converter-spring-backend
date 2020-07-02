package com.exmachina.sam.currency.scheduledtasks.helpers;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RatesMapper {
    public List<Rate> mapThirdPartyRatesToRates(ThirdPartyRates thirdPartyRatesMap) {
        List<Rate> mappedRates = new ArrayList<Rate>();
        Rate rateToAdd = null;
        String currentSourceCurrency = null, currentDestinationCurrency = null;
        BigDecimal currentCoefficient = BigDecimal.ZERO;

        for(Map.Entry<String, LinkedHashMap<String, BigDecimal>> sourceCurrencyEntry : thirdPartyRatesMap.entrySet()){
            currentSourceCurrency = sourceCurrencyEntry.getKey();
            for(Map.Entry<String,BigDecimal> destinationCurrencyEntry : sourceCurrencyEntry.getValue().entrySet()){
                currentDestinationCurrency = destinationCurrencyEntry.getKey();
                currentCoefficient = destinationCurrencyEntry.getValue();
                rateToAdd = new Rate(currentSourceCurrency, currentDestinationCurrency, currentCoefficient);
                mappedRates.add(rateToAdd);
            }
        }

        return mappedRates;
    }
}
