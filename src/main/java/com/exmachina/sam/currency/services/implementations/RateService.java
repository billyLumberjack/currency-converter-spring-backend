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
        Rate rate = repository.findBySourceAndDestination(sourceCurrency, destinationCurrency);
        return rate;
    }

    @Override
    public List<Rate> saveAll(List<Rate> ratesToSave) {
        List<Rate> rates = (List<Rate>) repository.saveAll(ratesToSave);
        return rates;
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    public Rate save(Rate rateToSave){
        return repository.save(rateToSave);
    }
}