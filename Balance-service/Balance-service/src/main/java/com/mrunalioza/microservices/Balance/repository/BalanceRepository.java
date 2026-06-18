package com.mrunalioza.microservices.Balance.repository;

import com.mrunalioza.microservices.Balance.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    boolean existsByAccountNumberAndBalanceGreaterThanEqual(
            String accountNumber,
            java.math.BigDecimal balance
    );
}