package com.exmachina.sam.currency.repositories;

import com.exmachina.sam.currency.entities.Rate;
import com.exmachina.sam.currency.projection.RateSourceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, BigInteger> {
    Rate findBySourceAndDestination(String sourceCurrency, String destinationCurrency);
    List<Rate> findByDestination(String destinationCurrency);
    List<Rate> findBySource(String sourceCurrency);
    List<RateSourceProjection> findDistinctBy();
}