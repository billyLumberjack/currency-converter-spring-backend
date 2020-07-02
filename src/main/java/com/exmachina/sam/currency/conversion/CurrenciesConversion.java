package com.exmachina.sam.currency.conversion;

import com.exmachina.sam.currency.rates.Rate;

import java.math.BigDecimal;

public class CurrenciesConversion {
	private Rate usedRate;
	private BigDecimal sourceAmount;
	private BigDecimal destinationAmount;

	public CurrenciesConversion(Rate usedRate, BigDecimal sourceAmount, BigDecimal destinationAmount){
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

	public BigDecimal getSourceAmount() {
		return sourceAmount;
	}

	public void setSourceAmount(BigDecimal sourceAmount) {
		this.sourceAmount = sourceAmount;
	}

	public BigDecimal getDestinationAmount() {
		return destinationAmount;
	}

	public void setDestinationAmount(BigDecimal destinationAmount) {
		this.destinationAmount = destinationAmount;
	}
}