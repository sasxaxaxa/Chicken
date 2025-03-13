package com.example.chicken.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kill-chicken-client")
public record WebClientProperty(String baseUrl, int responseTimeoutMs, int connectTimeoutMs) {
}
