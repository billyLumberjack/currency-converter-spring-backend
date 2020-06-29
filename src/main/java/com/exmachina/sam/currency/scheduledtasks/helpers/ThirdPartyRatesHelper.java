package com.exmachina.sam.currency.scheduledtasks.helpers;

import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdPartyRatesHelper {
    private final RestTemplate restTemplate = new RestTemplate();
    private String baseUrl;
    private String apiKey;

    private static ThirdPartyRatesHelper instance;

    private ThirdPartyRatesHelper(String baseUrl , String apiKey)
    {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;

    }

    public static ThirdPartyRatesHelper getInstance(String baseUrl, String apiKey){
        if ( instance == null) {
            instance = new ThirdPartyRatesHelper(baseUrl, apiKey);
        }
        return instance;
    }

    public ThirdPartyRates getThirdPartyRatesBySourceAndDestinationCurrency(String currenciesToSynch){
        Map<String, String> httpParams = new HashMap<String, String>();
        httpParams.put("comma_separated_currencies" , currenciesToSynch);
        httpParams.put("key" , apiKey);

        return restTemplate.getForObject(baseUrl, ThirdPartyRates.class, httpParams);
    }



}
