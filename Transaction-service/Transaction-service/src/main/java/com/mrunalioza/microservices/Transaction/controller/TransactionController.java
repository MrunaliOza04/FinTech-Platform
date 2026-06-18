package com.mrunalioza.microservices.Transaction.controller;

import com.mrunalioza.microservices.Transaction.dto.TransactionRequest;
import com.mrunalioza.microservices.Transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createTransaction(@RequestBody TransactionRequest transactionRequest) {

        transactionService.createTransaction(transactionRequest);

        return "Transaction completed successfully";
    }
}