package com.mrunalioza.microservices.Balance.controller;

import com.mrunalioza.microservices.Balance.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean hasSufficientBalance(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount) {

        return balanceService
                .hasSufficientBalance(accountNumber, amount);
    }
}
