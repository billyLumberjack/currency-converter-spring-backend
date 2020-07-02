package com.exmachina.sam.currency.rates;

import java.util.List;

public interface RateService {
    List<Rate> findAll();
    List<Rate> findBy(String sourceCurrency, String destinationCurrency) throws RateNotFoundException;
    Rate findBySourceAndDestination(String sourceCurrency , String destinationCurrency) throws RateNotFoundException;
    List<Rate> findBySource(String sourceCurrency) throws RateNotFoundException;
    List<Rate> findByDestination(String sourceCurrency) throws RateNotFoundException;
    List<Rate> saveAll(List<Rate> ratesToSave);
    Rate save(Rate rateToSave);
    void deleteAll();
    List<RateSourceProjection> findBy();
}