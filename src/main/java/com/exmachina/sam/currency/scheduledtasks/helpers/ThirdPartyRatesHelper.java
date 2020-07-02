package com.exmachina.sam.currency.scheduledtasks.helpers;

import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ThirdPartyRatesHelper {
    private RestTemplate restTemplate;
    private String baseUrl;
    private String apiKey;

    private static ThirdPartyRatesHelper instance;

    public ThirdPartyRatesHelper(RestTemplate restTemplate, String baseUrl, String apiKey)
    {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public ThirdPartyRates getThirdPartyRatesBySourceAndDestinationCurrency(String currenciesToSynch){
        Map<String, String> httpParams = new HashMap<String, String>();
        httpParams.put("comma_separated_currencies" , currenciesToSynch);
        httpParams.put("key" , apiKey);

        return restTemplate.getForObject(baseUrl, ThirdPartyRates.class, httpParams);
    }

}
