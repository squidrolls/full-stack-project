package com.elaine.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
