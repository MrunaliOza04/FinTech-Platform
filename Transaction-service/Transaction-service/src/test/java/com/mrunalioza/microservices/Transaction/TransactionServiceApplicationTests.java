package com.mrunalioza.microservices.Transaction;

import com.mrunalioza.microservices.Transaction.stubs.BalanceClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.wiremock.spring.EnableWireMock;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock
class TransactionServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer =
			new MySQLContainer<>("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	static {
		mySQLContainer.start();
	}

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void shouldCreateTransaction() {

		String createTransactionJson = """
                {
                  "fromAccount": "ACC1001",
                  "toAccount": "ACC1002",
                  "amount": 5000,
                  "transactionType": "TRANSFER"
                }
                """;

		BalanceClientStub.stubBalanceCall("ACC1001",new BigDecimal("5000"));

		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(createTransactionJson)
				.when()
				.post("/api/transaction")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body()
				.asString();

		assertThat(
				responseBodyString,
				Matchers.is("Transaction completed successfully")
		);
	}
}