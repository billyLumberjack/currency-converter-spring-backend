package com.exmachina.sam.currency.repositories;

import com.exmachina.sam.currency.entities.Rate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class RateRepositoryCustomImpl implements RateRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Rate findBySourceAndTargetCurrency(String sourceCurrency, String destinationCurrency) {
        List<Rate> queryRatesResult = new ArrayList<Rate>();
        TypedQuery<Rate> typedQuery = entityManager.createQuery("SELECT rate FROM Rate rate WHERE rate.sourceCurrency = :sourceCurrency AND rate.destinationCurrency = :destinationCurrency", Rate.class);
        typedQuery.setParameter("sourceCurrency", sourceCurrency);
        typedQuery.setParameter("destinationCurrency", destinationCurrency);

        if(typedQuery.getResultList().size() > 0){
            return typedQuery.getResultList().get(0);
        }

        return null;
    }
}
