package com.exmachina.sam.currency.scheduledtasks.helpers;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.pojos.BitcoinRates;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RatesMapper {
    public static List<Rate> mapThirdPartyRatesToRates(ThirdPartyRates updatedThirdPartyRates) {
        List<Rate> mappedRates = new ArrayList<Rate>();
        Rate rateToAdd;
        String currentSourceCurrency, currentTargetCurrency;
        Double currentCoefficient;

        Map<String, Map<String,Double>> thirdPartyRatesMap = convertPojoToMap(updatedThirdPartyRates);

        for(Map.Entry<String, Map<String,Double>> sourceCurrencyEntry : thirdPartyRatesMap.entrySet()){
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

    private static <K,V> Map<K,V> convertPojoToMap(Object mapMe){
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(mapMe, new TypeReference<Map<K,V>>() {});
    }
}
