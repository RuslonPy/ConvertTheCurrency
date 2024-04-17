package com.itembase.task.controller;

import com.itembase.task.request.ConversionRequest;
import com.itembase.task.response.ConversionResponse;
import com.itembase.task.service.CurrencyConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class CurrencyController {

    private final CurrencyConversionService conversionService;

    public CurrencyController(CurrencyConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping("/currency/convert")
    public Mono<ConversionResponse> convertCurrency(@RequestBody ConversionRequest request) {
        return conversionService.convertCurrency(request)
                .switchIfEmpty(Mono.error(new IllegalStateException("No providers available")));
    }
}
