package com.mrunalioza.microservices.Transaction.stubs;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class BalanceClientStub {

    public static void stubBalanceCall(
            String accountNumber,
            BigDecimal amount) {

        if (amount.compareTo(new BigDecimal("50000")) <= 0) {

            stubFor(get(urlEqualTo(
                    "/api/balance?accountNumber="
                            + accountNumber
                            + "&amount="
                            + amount))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader(
                                    "Content-Type",
                                    "application/json")
                            .withBody("true")));

        } else {

            stubFor(get(urlEqualTo(
                    "/api/balance?accountNumber="
                            + accountNumber
                            + "&amount="
                            + amount))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader(
                                    "Content-Type",
                                    "application/json")
                            .withBody("false")));
        }
    }
}