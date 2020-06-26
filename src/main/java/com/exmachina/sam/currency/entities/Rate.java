package com.exmachina.sam.currency.entities;


import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "source_currency")
    private String source;

    @Column(name = "destination_currency")
    private String destination;

    @Column(name = "coefficient")
    private double coefficient;

    public Rate() {
    }

    public Rate(String source, String destination, double coefficient) {
        this.source = source;
        this.destination = destination;
        this.coefficient = coefficient;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", sourceCurrency='" + source + '\'' +
                ", destinationCurrency='" + destination + '\'' +
                ", coefficient=" + coefficient +
                '}';
    }
}
