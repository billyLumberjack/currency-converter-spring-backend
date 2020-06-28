package com.exmachina.sam.currency.controllers;

import com.exmachina.sam.currency.entities.CurrenciesConversion;
import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.exception.RateNotFoundException;
import com.exmachina.sam.currency.services.interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ConversionController {

    @Autowired
    private IRateService rateService;

    @GetMapping("/convert")
    public CurrenciesConversion convertAmount(
            @RequestParam(value = "sourceCurrency" , defaultValue = "USD") String querySourceCurrency,
            @RequestParam(value = "destinationCurrency" , defaultValue = "USD") String queryDestinationCurrency,
            @RequestParam(value = "amount" , defaultValue = "0") Double sourceAmount
    ) {
        try {
            Rate rateForConversion = rateService.findBySourceAndDestination(querySourceCurrency, queryDestinationCurrency);
            Double conversionResult = sourceAmount * rateForConversion.getCoefficient();
            return new CurrenciesConversion(
                    rateForConversion,
                    sourceAmount,
                    conversionResult
            );
        }
        catch(RateNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate Not Found", ex);
        }
    }
}