package com.mrunalioza.microservices.Transaction.config;

import com.mrunalioza.microservices.Transaction.client.BalanceClient;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.context.annotation.Lazy;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    @Value("${balance.url}")
    private String balanceServiceUrl;

   // private final ObservationRegistry observationRegistry;

    @Bean
    @Lazy
    public BalanceClient balanceClient() {

        RestClient restClient = RestClient.builder()
                .baseUrl(balanceServiceUrl)
               .requestFactory(getClientRequestFactory())
           //     .observationRegistry(observationRegistry)
                .build();

        var restClientAdapter = RestClientAdapter.create(restClient);

        var httpServiceProxyFactory =
                HttpServiceProxyFactory.builderFor(restClientAdapter).build();

        return httpServiceProxyFactory.createClient(BalanceClient.class);
    }

    private ClientHttpRequestFactory getClientRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        // Explicitly assign the network connection limits natively
        factory.setConnectTimeout(Duration.ofSeconds(3));
        factory.setReadTimeout(Duration.ofSeconds(3));

        return factory;
    }
}