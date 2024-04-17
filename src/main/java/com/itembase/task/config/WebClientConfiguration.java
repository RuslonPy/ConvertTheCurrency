package com.itembase.task.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
public class WebClientConfiguration {

    @Value("${webclient.timeout}")
    private Integer timeout;

    @Bean
    @Qualifier("exchangeRates")
    public WebClient exchangeRates() {
        return WebClient.builder()
                .baseUrl("https://api.exchangeratesapi.io")
                .clientConnector(getConnector())
                .build();
    }

    @Bean
    @Qualifier("exchangeRateApiClient")
    public WebClient exchangeRate() {
        return WebClient.builder()
                .baseUrl("https://api.exchangerate-api.com/v4")
                .clientConnector(getConnector())
                .build();
    }

    private ReactorClientHttpConnector getConnector() {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(timeout))
                        .addHandlerLast(new WriteTimeoutHandler(timeout)));

        HttpClient httpClient = HttpClient.from(tcpClient);

        return new ReactorClientHttpConnector(httpClient);
    }
}
