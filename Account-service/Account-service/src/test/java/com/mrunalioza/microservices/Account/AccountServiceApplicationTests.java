package com.mrunalioza.microservices.Account;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.mongodb.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer =
			new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateAccount() {

		String requestBody = """
                {
                    "accountNumber":"ACC1001",
                    "customerName":"Mrunal Oza",
                    "accountType":"SAVINGS",
                    "balance":"50000"
                }
                """;

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/account")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("accountNumber", Matchers.equalTo("ACC1001"))
				.body("customerName", Matchers.equalTo("Mrunal Oza"))
				.body("accountType", Matchers.equalTo("SAVINGS"));
	}
}