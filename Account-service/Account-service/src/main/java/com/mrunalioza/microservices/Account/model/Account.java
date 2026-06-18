package com.mrunalioza.microservices.Account.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "account")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Account {

    @Id
    private String id;

    private String accountNumber;

    private String customerName;

    private String accountType;

    private BigDecimal balance;
}