package com.mrunalioza.microservices.Account.service;

import com.mrunalioza.microservices.Account.dto.AccountRequest;
import com.mrunalioza.microservices.Account.dto.AccountResponse;
import com.mrunalioza.microservices.Account.model.Account;
import com.mrunalioza.microservices.Account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponse createAccount(AccountRequest accountRequest) {

        Account account = Account.builder()
                .accountNumber(accountRequest.accountNumber())
                .customerName(accountRequest.customerName())
                .accountType(accountRequest.accountType())
                .balance(accountRequest.balance())
                .build();

        accountRepository.save(account);

        log.info("Account created successfully");

        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getCustomerName(),
                account.getAccountType(),
                account.getBalance()
        );
    }

    public List<AccountResponse> getAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountResponse(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getCustomerName(),
                        account.getAccountType(),
                        account.getBalance()
                ))
                .toList();
    }
}