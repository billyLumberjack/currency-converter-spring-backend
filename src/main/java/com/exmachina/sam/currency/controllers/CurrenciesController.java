package com.exmachina.sam.currency.controllers;

import com.exmachina.sam.currency.projection.RateSourceProjection;
import com.exmachina.sam.currency.services.interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CurrenciesController {

	@Autowired
	private IRateService rateService;

	@GetMapping("/currencies")
	public List<String> getCurrencies(){

		return rateService
				.findBy()
				.stream()
				.map(obj -> obj.getSource())
				.collect(Collectors.toList());
	}
}