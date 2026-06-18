package com.mrunalioza.api_gateway.routes;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.boot.health.contributor.Health;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    @Value("${account.service.url}")
    private String accountServiceUrl;

    @Value("${transaction.service.url}")
    private String transactionServiceUrl;

    @Value("${balance.service.url}")
    private String balanceServiceUrl;

    @Bean
    public HealthIndicator accountServiceHealthIndicator(CircuitBreakerRegistry circuitBreakerRegistry) {
        return () -> Health.up()
                .withDetail("accountServiceCircuitBreaker",
                        circuitBreakerRegistry.circuitBreaker("accountServiceCircuitBreaker").getState().toString())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> accountServiceRoute() {
        return GatewayRouterFunctions.route("account_service")
                .route(RequestPredicates.path("/api/account"),
                        HandlerFunctions.http())
                .before(uri(accountServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "accountServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> accountServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("account_service_swagger")
                .route(RequestPredicates.path("/aggregate/account-service/v3/api-docs"),
                        HandlerFunctions.http())
                .before(setPath("/api-docs")) // <-- CHANGED from /v3/api-docs to /api-docs
                .before(uri(accountServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "accountServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public HealthIndicator transactionServiceHealthIndicator(CircuitBreakerRegistry circuitBreakerRegistry) {
        return () -> Health.up()
                .withDetail("transactionServiceCircuitBreaker",
                        circuitBreakerRegistry.circuitBreaker("transactionServiceCircuitBreaker").getState().toString())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> transactionServiceRoute() {
        return GatewayRouterFunctions.route("transaction_service")
                .route(RequestPredicates.path("/api/transaction"),
                        HandlerFunctions.http())
                .before(uri(transactionServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "transactionServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> transactionServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("transaction_service_swagger")
                .route(RequestPredicates.path("/aggregate/transaction-service/v3/api-docs"),
                        HandlerFunctions.http())
                .before(setPath("/api-docs"))
                .before(uri(transactionServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "transactionServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public HealthIndicator balanceServiceHealthIndicator(CircuitBreakerRegistry circuitBreakerRegistry) {
        return () -> Health.up()
                .withDetail("balanceServiceCircuitBreaker",
                        circuitBreakerRegistry.circuitBreaker("balanceServiceCircuitBreaker").getState().toString())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> balanceServiceRoute() {
        return GatewayRouterFunctions.route("balance_service")
                .route(RequestPredicates.path("/api/balance"),
                        HandlerFunctions.http())
                .before(uri(balanceServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "balanceServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> balanceServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("balance_service_swagger")
                .route(RequestPredicates.path("/aggregate/balance-service/v3/api-docs"),
                        HandlerFunctions.http())
                .before(setPath("/api-docs"))
                .before(uri(balanceServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "balanceServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }



    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .GET("/fallbackRoute",
                        request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Service Unavailable, please try again later"))
                .build();
    }
}