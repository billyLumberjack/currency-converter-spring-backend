package com.exmachina.sam.currency.repositories;

import com.exmachina.sam.currency.entities.Rate;

public interface RateRepositoryCustom {
    public Rate findBySourceAndTargetCurrency(String sourceCurrency, String destinationCurrency);
}
