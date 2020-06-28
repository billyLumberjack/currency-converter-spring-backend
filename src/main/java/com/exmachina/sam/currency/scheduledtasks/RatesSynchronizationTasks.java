package com.exmachina.sam.currency.scheduledtasks;

import java.util.*;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.helpers.RatesMapper;
import com.exmachina.sam.currency.scheduledtasks.helpers.ThirdPartyRatesHelper;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import com.exmachina.sam.currency.services.interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RatesSynchronizationTasks {

	@Autowired
	private Environment env;
	@Autowired
	private IRateService rateService;

	@Scheduled(fixedRateString = "${scheduler.fixed.rate}")
	public void ratesSynchronization() {

		String baseUrlFromProperties = env.getProperty("thirdparty.converter.base.url");
		String apiKeyFromProperties = env.getProperty("thirdparty.converter.api.key");

		ThirdPartyRatesHelper thirdPartyRatesHelper = ThirdPartyRatesHelper.getInstance(baseUrlFromProperties, apiKeyFromProperties);
		String sourceCurrencyToSynch = env.getProperty("thirdparty.converter.source.corrency");
		String[] destinationCurrencies = env.getProperty("thirdparty.converter.comma.separated.destination.currencies").split(",");

		ThirdPartyRates updatedThirdPartyRates = thirdPartyRatesHelper.getThirdPartyRatesBySourceAndDestinationCurrency(
				sourceCurrencyToSynch,
				destinationCurrencies
		);
		List<Rate> updatedRates = RatesMapper.mapThirdPartyRatesToRates(updatedThirdPartyRates);

		rateService.deleteAll();
		rateService.saveAll(updatedRates);
	}
}



