package com.exmachina.sam.currency.services.interfaces;

import com.exmachina.sam.currency.entities.Rate;

import java.util.List;

public interface IRateService {

    List<Rate> findAll();
}