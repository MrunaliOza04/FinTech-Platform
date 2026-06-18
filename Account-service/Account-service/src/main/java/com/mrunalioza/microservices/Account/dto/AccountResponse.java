package com.mrunalioza.microservices.Account.dto;

import java.math.BigDecimal;

public record AccountResponse(
        String id,
        String accountNumber,
        String customerName,
        String accountType,
        BigDecimal balance
) {
}