package com.exmachina.sam.currency.inmemoryauth.security;

public enum UserRoles {
    STRING_ADMIN("ADMINISTRATOR"),
    STRING_USER("USER");

    private final String role;

    UserRoles(final String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}