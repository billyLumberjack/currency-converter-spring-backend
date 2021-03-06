package com.exmachina.sam.currency.rates;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Rate Not Found")
public class RateNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public RateNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}