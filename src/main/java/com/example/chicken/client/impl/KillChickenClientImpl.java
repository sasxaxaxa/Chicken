package com.example.chicken.client.impl;

import com.example.chicken.client.KillChickenClient;
import com.example.chicken.client.request.KillChickenRequest;
import com.example.chicken.config.property.KillChickenProperty;
import com.example.chicken.exception.KillChickenClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class KillChickenClientImpl implements KillChickenClient {

  private static final String BEARER_PREFIX = "Bearer ";

  private final WebClient webClient;

  @Value("${token}")
  private String token;


  public <T> T killChickenRequest(KillChickenRequest killChickenRequest,
                                  KillChickenProperty killChickenProperty,
                                  ParameterizedTypeReference<T> responseType)
      throws KillChickenClientException {
    log.info("Отправляем запрос по пути {} с body: {}, типом ответа: {}",
             killChickenProperty.getPath(), killChickenRequest, responseType);
    return webClient.post()
                    .uri(killChickenProperty.getPath(), killChickenRequest.name())
                    .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token)
                    .bodyValue(killChickenRequest)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError,
                              errorResponse -> errorResponse.bodyToMono(String.class)
                                                            .map(KillChickenClientException::new)
                                                            .defaultIfEmpty(
                                                                new KillChickenClientException(
                                                                    "Получен ответ с путым body и статусом: %s".formatted(
                                                                        errorResponse.statusCode()))))

                    .bodyToMono(responseType)
                    .onErrorMap(e -> {
                      log.error(e.getMessage(), e);
                      if (e instanceof KillChickenClientException exception) {
                        return exception;
                      }
                      return new KillChickenClientException("Иная ошибка при выполнении запроса",
                                                            e);
                    }).block();
  }
}
