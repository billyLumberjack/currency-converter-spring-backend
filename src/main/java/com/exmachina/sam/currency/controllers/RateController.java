package com.exmachina.sam.currency.controllers;

import java.util.ArrayList;
import java.util.List;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.exception.RateNotFoundException;
import com.exmachina.sam.currency.services.interfaces.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


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
	){
		try {
			return rateService.findBy(querySourceCurrency, queryDestinationCurrency);
		} catch (RateNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate Not Found", ex);
		}
	}
}