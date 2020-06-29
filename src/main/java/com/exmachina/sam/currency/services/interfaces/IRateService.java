package com.exmachina.sam.currency.services.interfaces;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.exception.RateNotFoundException;
import com.exmachina.sam.currency.projection.RateSourceProjection;

import java.util.List;

public interface IRateService {
    List<Rate> findAll();
    Rate findBySourceAndDestination(String sourceCurrency , String destinationCurrency) throws RateNotFoundException;
    List<Rate> findBySource(String sourceCurrency) throws RateNotFoundException;
    List<Rate> findByDestination(String sourceCurrency) throws RateNotFoundException;
    List<Rate> saveAll(List<Rate> ratesToSave);
    Rate save(Rate rateToSave);
    void deleteAll();
    List<RateSourceProjection> findBy();
}