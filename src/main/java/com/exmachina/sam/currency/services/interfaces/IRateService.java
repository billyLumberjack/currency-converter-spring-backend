package com.exmachina.sam.currency.services.interfaces;

import com.exmachina.sam.currency.entities.Rate;

import java.util.List;

public interface IRateService {

    List<Rate> findAll();
    Rate findBySourceAndTargetCurrency(String sourceCurrency , String destinationCurrency);
    List<Rate> saveAll(List<Rate> ratesToSave);
    Rate save(Rate rateToSave);
    void deleteAll();
}