package com.exmachina.sam.currency.controllers;

import com.exmachina.sam.currency.entities.CurrenciesConversion;
import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.exception.RateNotFoundException;
import com.exmachina.sam.currency.services.converter.CurrencyConverter;
import com.exmachina.sam.currency.services.interfaces.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
public class ConversionController {

    @Autowired
    public ConversionController(RateService rateService, CurrencyConverter currencyConverter){
        this.currencyConverter = currencyConverter;
        this.rateService = rateService;
    }

    private RateService rateService;
    private CurrencyConverter currencyConverter;

    @GetMapping("/convert")
    public CurrenciesConversion convertAmount(
            @RequestParam(value = "sourceCurrency" , defaultValue = "USD") String querySourceCurrency,
            @RequestParam(value = "destinationCurrency" , defaultValue = "USD") String queryDestinationCurrency,
            @RequestParam(value = "amount" , defaultValue = "0") BigDecimal sourceAmount
    ) {
        try {
            Rate rateForConversion = rateService.findBySourceAndDestination(querySourceCurrency, queryDestinationCurrency);
            return currencyConverter.convertByRateAndAmount(rateForConversion, sourceAmount);
        }
        catch(RateNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate Not Found", ex);
        }
    }
}