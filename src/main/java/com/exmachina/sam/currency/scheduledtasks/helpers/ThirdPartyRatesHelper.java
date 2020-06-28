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
    private final String baseUrl = "https://min-api.cryptocompare.com/data/pricemulti?fsyms={source}&tsyms={comma_separated_destinations}&api_key={key}";
    private String apiKey;

    private static ThirdPartyRatesHelper instance;

    private ThirdPartyRatesHelper(String apiKey){
        this.apiKey = apiKey;
    }

    public static ThirdPartyRatesHelper getInstance(String apiKey){
        if ( instance == null) {
            instance = new ThirdPartyRatesHelper(apiKey);
        }
        return instance;
    }

    public ThirdPartyRates getThirdPartyRatesBySourceAndDestinationCurrency(String source , String[] destinations){
        Map<String, String> httpParams = new HashMap<String, String>();
        httpParams.put("source" , source);
        httpParams.put("comma_separated_destinations" , String.join(",", destinations));
        httpParams.put("key" , apiKey);

        return restTemplate.getForObject(baseUrl, ThirdPartyRates.class, httpParams);
    }



}
