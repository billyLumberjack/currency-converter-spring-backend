package com.exmachina.sam.currency.scheduledtasks;

import java.text.SimpleDateFormat;
import java.util.*;

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
	private final RestTemplate restTemplate = new RestTemplate();
	private final String baseUrl = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC&tsyms=USD,EUR&api_key=12e663f7242e98c88435b6d8d939c276da860097834847d645f437c26935acd6";

	@Scheduled(fixedRate = 5000)
	public void ratesSynchronization() {
		ThirdPartyRates updatedThirdPartyRates = restTemplate.getForObject(baseUrl, ThirdPartyRates.class);
		List<Rate> updatedRates = RatesMapper.mapThirdPartyRatesToRates(updatedThirdPartyRates);

		rateService.deleteAll();
		rateService.saveAll(updatedRates);
	}
}



