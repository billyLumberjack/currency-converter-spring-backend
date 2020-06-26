package com.exmachina.sam.currency.models;


import javax.persistence.*;

@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "source_currency")
    private String sourceCurrency;

    @Column(name = "destination_currency")
    private String destinationCurrency;

    @Column(name = "rate")
    private double rate;
}
