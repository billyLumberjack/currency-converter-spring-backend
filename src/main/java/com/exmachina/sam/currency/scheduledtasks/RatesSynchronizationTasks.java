package com.exmachina.sam.currency.scheduledtasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.scheduledtasks.helpers.RatesMapper;
import com.exmachina.sam.currency.scheduledtasks.pojos.ThirdPartyRates;
import com.exmachina.sam.currency.services.interfaces.IRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Component
public class RatesSynchronizationTasks {

	@Autowired
	private IRateService rateService;

	private static final Logger log = LoggerFactory.getLogger(RatesSynchronizationTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private final RestTemplate restTemplate = new RestTemplate();

	private final String baseUrl = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC&tsyms=USD,EUR&api_key=12e663f7242e98c88435b6d8d939c276da860097834847d645f437c26935acd6";

	@Transactional
	@Scheduled(fixedRate = 5000)
	public void ratesSynchronization() {
		log.info("Synchronizing rates at time {}", dateFormat.format(new Date()));

		ThirdPartyRates updatedThirdPartyRates = restTemplate.getForObject(baseUrl, ThirdPartyRates.class);
		List<Rate> updatedRates = RatesMapper.mapThirdPartyRatesToRates(updatedThirdPartyRates);

		for(Rate updatedRate : updatedRates){
			Rate rateToUpdate = (Rate) rateService.findBySourceAndTargetCurrency(
					updatedRate.getSource(),
					updatedRate.getDestination()
					);
			System.out.println("");
		}

		System.out.println("");

	}
}



