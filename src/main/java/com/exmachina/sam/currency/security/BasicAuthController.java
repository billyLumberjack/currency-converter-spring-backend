package com.exmachina.sam.currency.security;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class BasicAuthController {

    @GetMapping(value="/basicauth")
    public AuthenticationBean basicauth() {
        return new AuthenticationBean("You are authenticated");
    }

}
