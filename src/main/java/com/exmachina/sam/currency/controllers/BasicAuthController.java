package com.exmachina.sam.currency.controllers;

import com.exmachina.sam.currency.inmemoryauth.security.AuthenticationBean;
import org.springframework.web.bind.annotation.*;

@RestController
public class BasicAuthController {

    @GetMapping(value="/basicauth")
    public AuthenticationBean basicauth() {
        return new AuthenticationBean("You are authenticated");
    }

}
