package com.exmachina.sam.currency.controllers;

import java.util.ArrayList;
import java.util.List;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.exception.RateNotFoundException;
import com.exmachina.sam.currency.services.interfaces.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RateController {

	@Autowired
	public RateController(RateService rateService){
		this.rateService = rateService;
	}

	private RateService rateService;

	@GetMapping("/rate")
	public List<Rate> getRate(
			@RequestParam(value = "sourceCurrency" , defaultValue = "") String querySourceCurrency ,
			@RequestParam(value = "destinationCurrency" , defaultValue = "") String queryDestinationCurrency
	) throws RateNotFoundException {

		List<Rate> resultRates = new ArrayList<Rate>();

		if(!querySourceCurrency.isEmpty() && !queryDestinationCurrency.isEmpty()){
			resultRates.add(
					rateService.findBySourceAndDestination(querySourceCurrency, queryDestinationCurrency)
			);
		}
		if(!querySourceCurrency.isEmpty() && queryDestinationCurrency.isEmpty()){
			resultRates.addAll(
					rateService.findBySource(querySourceCurrency)
			);
		}
		if(querySourceCurrency.isEmpty() && !queryDestinationCurrency.isEmpty()){
			resultRates.addAll(
					rateService.findByDestination(queryDestinationCurrency)
			);
		}
		if(querySourceCurrency.isEmpty() && queryDestinationCurrency.isEmpty()){
			resultRates.addAll(
					rateService.findAll()
			);
		}
		return resultRates;
	}
}