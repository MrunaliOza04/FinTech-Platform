package com.mrunalioza.microservices.Transaction.service;

import com.mrunalioza.microservices.Transaction.client.BalanceClient;
import com.mrunalioza.microservices.Transaction.dto.TransactionRequest;
import com.mrunalioza.microservices.Transaction.model.Transaction;
import com.mrunalioza.microservices.Transaction.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor(onConstructor_ = {@Lazy})
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final BalanceClient balanceClient;
    public void createTransaction(TransactionRequest transactionRequest) {

        var hasSufficientBalance =
                balanceClient.hasSufficientBalance(
                        transactionRequest.fromAccount(),
                        transactionRequest.amount());
        if(hasSufficientBalance) {

            Transaction transaction = new Transaction();

            transaction.setTransactionNumber(UUID.randomUUID().toString());
            transaction.setFromAccount(transactionRequest.fromAccount());
            transaction.setToAccount(transactionRequest.toAccount());
            transaction.setAmount(transactionRequest.amount());
            transaction.setTransactionType(transactionRequest.transactionType());



            transactionRepository.save(transaction);

        } else {

            throw new RuntimeException(
                    "Insufficient balance in account "
                            + transactionRequest.fromAccount());

        }
    }
}