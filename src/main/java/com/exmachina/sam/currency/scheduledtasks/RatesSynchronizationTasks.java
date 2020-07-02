package com.exmachina.sam.currency.scheduledtasks;

import java.util.*;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.helpers.RatesMapper;
import com.exmachina.sam.currency.scheduledtasks.helpers.ThirdPartyRatesHelper;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import com.exmachina.sam.currency.services.interfaces.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RatesSynchronizationTasks {

	@Value("${thirdparty.converter.comma.separated.currencies}")
	private final String currenciesToSynch = null;

	@Autowired
	public RatesSynchronizationTasks(RateService rateService, ThirdPartyRatesHelper thirdPartyRatesHelper){
		this.rateService = rateService;
		this.thirdPartyRatesHelper = thirdPartyRatesHelper;
	}

	private RateService rateService;
	private ThirdPartyRatesHelper thirdPartyRatesHelper;

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



