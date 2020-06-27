package com.exmachina.sam.currency.scheduledtasks;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.helpers.RatesMapper;
import com.exmachina.sam.currency.scheduledtasks.helpers.ThirdPartyRatesHelper;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import com.exmachina.sam.currency.services.interfaces.IRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Component
public class RatesSynchronizationTasks {

	@Autowired
	private Environment env;
	@Autowired
	private IRateService rateService;

	@Scheduled(fixedRateString = "${scheduler.fixed.rate}")
	public void ratesSynchronization() {
		ThirdPartyRatesHelper thirdPartyRatesHelper = ThirdPartyRatesHelper.getInstance(env.getProperty("thirdparty.converter.api.key"));
		String sourceCurrencyToSynch = env.getProperty("thirdparty.converter.source.corrency");
		String[] targetCurrencies = env.getProperty("thirdparty.converter.comma.separated.target.currencies").split(",");

		ThirdPartyRates updatedThirdPartyRates = thirdPartyRatesHelper.getThirdPartyRatesBySourceAndTargetCurrency(
				sourceCurrencyToSynch,
				targetCurrencies
		);
		List<Rate> updatedRates = RatesMapper.mapThirdPartyRatesToRates(updatedThirdPartyRates);

		rateService.deleteAll();
		rateService.saveAll(updatedRates);
	}
}



