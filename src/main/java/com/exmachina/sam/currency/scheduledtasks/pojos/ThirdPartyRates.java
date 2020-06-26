package com.exmachina.sam.currency.scheduledtasks.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThirdPartyRates {
	@JsonProperty("BTC")
	public BitcoinRates bitcoinRates;
}

