package com.exmachina.sam.currency.repositories;

import com.exmachina.sam.currency.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {
    Rate findBySourceAndDestination(String sourceCurrency, String destinationCurrency);
}