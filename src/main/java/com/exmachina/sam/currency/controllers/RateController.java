package com.exmachina.sam.currency.controllers;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.services.interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateController {

	@Autowired
	private IRateService rateService;

	@GetMapping("/rate")
	public List<Rate> greeting() {

		List<Rate> allRates = (List<Rate>) rateService.findAll();

		return allRates;
	}
}