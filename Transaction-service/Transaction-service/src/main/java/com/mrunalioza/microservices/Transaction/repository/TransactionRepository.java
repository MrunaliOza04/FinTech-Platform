package com.mrunalioza.microservices.Transaction.repository;

import com.mrunalioza.microservices.Transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}