package com.example.chicken.config;


import com.example.chicken.config.property.WebClientProperty;
import io.netty.channel.ChannelOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

  private final WebClientProperty webClientProperty;

  @Bean
  public WebClient getWebClient() {
    HttpClient baseClient = HttpClient
        .create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientProperty.connectTimeoutMs())
        .responseTimeout(Duration.of(webClientProperty.responseTimeoutMs(), ChronoUnit.MILLIS));

    return WebClient.builder()
                    .baseUrl(webClientProperty.baseUrl())
                    .clientConnector(new ReactorClientHttpConnector(baseClient))
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .build();
  }
}
