package com.mrunalioza.microservices.Transaction.dto;

import java.math.BigDecimal;

public record TransactionRequest(
        Long id,
        String fromAccount,
        String toAccount,
        BigDecimal amount,
        String transactionType
) {
}