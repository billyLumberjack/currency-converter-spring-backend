package com.exmachina.sam.currency.scheduledtasks.helpers;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RatesMapper {
    public static List<Rate> mapThirdPartyRatesToRates(ThirdPartyRates thirdPartyRatesMap) {
        List<Rate> mappedRates = new ArrayList<Rate>();
        Rate rateToAdd;
        String currentSourceCurrency, currentTargetCurrency;
        Double currentCoefficient;

        for(Map.Entry<String, LinkedHashMap<String,Double>> sourceCurrencyEntry : thirdPartyRatesMap.entrySet()){
            currentSourceCurrency = sourceCurrencyEntry.getKey();
            for(Map.Entry<String,Double> targetCurrencyEntry : sourceCurrencyEntry.getValue().entrySet()){
                currentTargetCurrency = targetCurrencyEntry.getKey();
                currentCoefficient = targetCurrencyEntry.getValue();
                rateToAdd = new Rate(currentSourceCurrency, currentTargetCurrency, currentCoefficient);
                mappedRates.add(rateToAdd);
            }

        }

        return mappedRates;
    }
}
