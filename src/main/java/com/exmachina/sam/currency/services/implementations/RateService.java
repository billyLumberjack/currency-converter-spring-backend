package com.exmachina.sam.currency.services.implementations;

import java.util.List;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.exception.RateNotFoundException;
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
    public Rate findBySourceAndDestination(String sourceCurrency, String destinationCurrency) throws RateNotFoundException {
        Rate rate = repository.findBySourceAndDestination(sourceCurrency, destinationCurrency);
        raiseErrorIfNullOrEmpty(rate);
        return rate;
    }

    @Override
    public List<Rate> findBySource(String sourceCurrency) throws RateNotFoundException{
        List<Rate> rates = repository.findBySource(sourceCurrency);
        raiseErrorIfNullOrEmpty(rates);
        return rates;
    }

    @Override
    public List<Rate> findByDestination(String targetCurrency) throws RateNotFoundException{
        List<Rate> rates = repository.findByDestination(targetCurrency);
        raiseErrorIfNullOrEmpty(rates);
        return rates;
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

    private void raiseErrorIfNullOrEmpty(Rate rate) throws RateNotFoundException {
        if(rate == null){
            throw new RateNotFoundException("Rate not found exception");
        }
    }

    private void raiseErrorIfNullOrEmpty(List<Rate> rates) throws RateNotFoundException {
        if(rates.size() == 0){
            throw new RateNotFoundException("Rate not found exception");
        }
    }
}