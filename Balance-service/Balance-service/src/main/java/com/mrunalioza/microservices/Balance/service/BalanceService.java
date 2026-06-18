package com.mrunalioza.microservices.Balance.service;

import com.mrunalioza.microservices.Balance.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public boolean hasSufficientBalance(
            String accountNumber,
            BigDecimal amount) {

        return balanceRepository
                .existsByAccountNumberAndBalanceGreaterThanEqual(
                        accountNumber,
                        amount
                );
    }
}