package com.itembase.task.service;

import com.itembase.task.request.ConversionRequest;
import com.itembase.task.response.ConversionResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.Map;

@Service
public class CurrencyConversionService {

    private final WebClient exchangeRates;
    private final WebClient exchangeRate;

    public CurrencyConversionService(@Qualifier("exchangeRates") WebClient exchangeRates,
                                     @Qualifier("exchangeRateApiClient") WebClient exchangeRateApiClient) {
        this.exchangeRates = exchangeRates;
        this.exchangeRate = exchangeRateApiClient;
    }

    public Mono<ConversionResponse> convertCurrency(ConversionRequest request) {
        return fetchConversionRate(request.getFrom(), request.getTo())
                .map(conversionRate -> {
                    System.out.println(conversionRate.toString());
                    ConversionResponse response = new ConversionResponse();
                    response.setFrom(request.getFrom());
                    response.setTo(request.getTo());
                    response.setAmount(request.getAmount());
                    response.setConverted(Math.floor(request.getAmount() * conversionRate * 100) / 100);
                    return response;
                });
    }

    private Mono<Double> fetchConversionRate(String from, String to) {

        return exchangeRates.get()
                .uri("/latest?base={from}", from, to)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(response -> {
                    Map<String, Double> rates = (Map<String, Double>) response.get("rates");
                    if (rates != null && rates.containsKey(to)) {
                        return Mono.just(rates.get(to));
                    } else {
                        return exchangeRate.get()
                                .uri("/latest/{from}", from)
                                .retrieve()
                                .bodyToMono(Map.class)
                                .flatMap(conversionResponse -> {
                                    Map<String, Double> secondRates = (Map<String, Double>) conversionResponse.get("rates");
                                    if (secondRates != null && secondRates.containsKey(to)) {
                                        return Mono.just(secondRates.get(to));
                                    } else {
                                        return Mono.empty();
                                    }
                                });
                    }
                });
    }
}
