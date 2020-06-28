package com.exmachina.sam.currency.scheduledtasks.helpers;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RatesMapper {
    public static List<Rate> mapThirdPartyRatesToRates(ThirdPartyRates thirdPartyRatesMap) {
        List<Rate> mappedRates = new ArrayList<Rate>();
        Rate rateToAdd = null;
        String currentSourceCurrency = null, currentDestinationCurrency = null;
        Double currentCoefficient = 0.0;

        for(Map.Entry<String, LinkedHashMap<String,Double>> sourceCurrencyEntry : thirdPartyRatesMap.entrySet()){
            currentSourceCurrency = sourceCurrencyEntry.getKey();
            for(Map.Entry<String,Double> destinationCurrencyEntry : sourceCurrencyEntry.getValue().entrySet()){
                currentDestinationCurrency = destinationCurrencyEntry.getKey();
                currentCoefficient = destinationCurrencyEntry.getValue();
                rateToAdd = new Rate(currentSourceCurrency, currentDestinationCurrency, currentCoefficient);
                mappedRates.add(rateToAdd);
            }
        }

        return mappedRates;
    }
}
