package com.exmachina.sam.currency.scheduledtasks;

import java.util.*;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.helpers.RatesMapper;
import com.exmachina.sam.currency.scheduledtasks.helpers.ThirdPartyRatesHelper;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import com.exmachina.sam.currency.services.interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RatesSynchronizationTasks {

	@Autowired
	private IRateService rateService;

	@Autowired
	private ThirdPartyRatesHelper thirdPartyRatesHelper;

	@Value("${thirdparty.converter.comma.separated.currencies}")
	private final String currenciesToSynch = null;

	@Scheduled(fixedRateString = "${scheduler.fixed.rate}")
	public void ratesSynchronization() {
		ThirdPartyRates updatedThirdPartyRates = thirdPartyRatesHelper.getThirdPartyRatesBySourceAndDestinationCurrency(
				currenciesToSynch
		);
		List<Rate> updatedRates = RatesMapper.mapThirdPartyRatesToRates(updatedThirdPartyRates);
		rateService.deleteAll();
		rateService.saveAll(updatedRates);
	}
}



