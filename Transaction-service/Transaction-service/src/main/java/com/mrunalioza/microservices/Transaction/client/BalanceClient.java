package com.mrunalioza.microservices.Transaction.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.math.BigDecimal;

public interface BalanceClient {

    Logger log = LoggerFactory.getLogger(BalanceClient.class);

    @GetExchange("/api/balance")
    @CircuitBreaker(name = "balance", fallbackMethod = "fallbackMethod")
    @Retry(name = "balance")
    boolean hasSufficientBalance(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount
    );

    default boolean fallbackMethod(
            String accountNumber,
            BigDecimal amount,
            Throwable throwable
    ) {
        log.info(
                "Cannot get balance for account {}, failure reason: {}",
                accountNumber,
                throwable.getMessage()
        );
        return false;
    }
}