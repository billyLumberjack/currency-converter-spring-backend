package com.exmachina.sam.currency.scheduledtasks.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoinRates {
	@JsonProperty("USD")
	public Double toDollar;
	@JsonProperty("EUR")
	public Double toEur;

}