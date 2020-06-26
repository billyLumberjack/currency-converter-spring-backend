package com.exmachina.sam.currency.services.implementations;

import java.util.List;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.repositories.RateRepository;
import com.exmachina.sam.currency.services.interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService implements IRateService {

    @Autowired
    private RateRepository repository;

    @Override
    public List<Rate> findAll() {
        List<Rate> rates = (List<Rate>) repository.findAll();
        return rates;
    }

    @Override
    public Rate findBySourceAndTargetCurrency(String sourceCurrency, String destinationCurrency) {
        Rate rate = repository.findBySourceAndTargetCurrency(sourceCurrency, destinationCurrency);
        return rate;
    }
}