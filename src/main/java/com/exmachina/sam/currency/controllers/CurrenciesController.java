package com.exmachina.sam.currency.controllers;

import com.exmachina.sam.currency.exception.RateNotFoundException;
import com.exmachina.sam.currency.services.interfaces.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class CurrenciesController {

	@Autowired
	public CurrenciesController(RateService rateService){
		this.rateService = rateService;
	}

	private RateService rateService;

	@GetMapping("/currencies")
	public Set<String> getCurrencies(){
		return rateService
				.findBy()
				.stream()
				.map(onlySourceRateProjection -> onlySourceRateProjection.getSource())
				.collect(Collectors.toSet());
	}
}