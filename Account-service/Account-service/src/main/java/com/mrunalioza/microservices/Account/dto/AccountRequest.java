package com.mrunalioza.microservices.Account.dto;

import java.math.BigDecimal;

public record AccountRequest(
        String id,
        String accountNumber,
        String customerName,
        String accountType,
        BigDecimal balance
) {
}