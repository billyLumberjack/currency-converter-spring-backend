package com.exmachina.sam.currency.controllers;

import com.exmachina.sam.currency.services.interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class CurrenciesController {

	@Autowired
	public CurrenciesController(IRateService iRateService){
		this.rateService = iRateService;
	}

	private IRateService rateService;

	@GetMapping("/currencies")
	public Set<String> getCurrencies(){

		return rateService
				.findBy()
				.stream()
				.map(onlySourceRateProjection -> onlySourceRateProjection.getSource())
				.collect(Collectors.toSet());
	}
}