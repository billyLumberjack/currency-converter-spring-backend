package com.exmachina.sam.currency;

import com.exmachina.sam.currency.scheduledtasks.helpers.ThirdPartyRatesHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Value("${thirdparty.converter.base.url}")
    private final String thirdPartyBaseUrl = null;

    @Value("${thirdparty.converter.api.key}")
    private final String thirdPartyApiKey = null;

    @Bean
    public ThirdPartyRatesHelper thirdPartyRatesHelper() {
        return new ThirdPartyRatesHelper(
                new RestTemplate(),
                thirdPartyBaseUrl,
                thirdPartyApiKey
        );
    }
}