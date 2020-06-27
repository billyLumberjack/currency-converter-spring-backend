package com.exmachina.sam.currency.entities;

public class CurrenciesConversion {
	private Rate usedRate;
	private Double sourceAmount;
	private Double destinationAmount;

	public CurrenciesConversion(Rate usedRate, Double sourceAmount, Double destinationAmount){
		this.usedRate = usedRate;
		this.sourceAmount = sourceAmount;
		this.destinationAmount = destinationAmount;
	}

	public Rate getUsedRate() {
		return usedRate;
	}

	public void setUsedRate(Rate usedRate) {
		this.usedRate = usedRate;
	}

	public Double getSourceAmount() {
		return sourceAmount;
	}

	public void setSourceAmount(Double sourceAmount) {
		this.sourceAmount = sourceAmount;
	}

	public Double getDestinationAmount() {
		return destinationAmount;
	}

	public void setDestinationAmount(Double destinationAmount) {
		this.destinationAmount = destinationAmount;
	}
}