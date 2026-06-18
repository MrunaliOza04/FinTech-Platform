package com.mrunalioza.microservices.Account.repository;

import com.mrunalioza.microservices.Account.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}