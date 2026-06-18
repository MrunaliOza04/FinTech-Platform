package com.mrunalioza.microservices.Balance;

import org.springframework.boot.SpringApplication;

public class TestBalanceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(BalanceServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
