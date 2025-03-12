package com.example.chicken.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//это аннотация Spring, которая говорит, что этот класс будет сервисом (логика работы с API).

@Service
public class GameCharacterService {

    private final WebClient webClient;
    private static final String BASE_URL = "https://api.artifactsmmo.com/my/";

    public GameCharacterService() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public Mono<String> move(String token, String name, int x, int y) {
        String payload = String.format("{\"name\":\"%s\", \"x\":%d, \"y\":%d}", name, x, y);

        return webClient.post()
                .uri("/{name}/action/move", name)
                .header("Authorization", token.startsWith("Bearer ") ? token : "Bearer " + token) // Проверка на "Bearer"
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSubscribe(subscription -> System.out.println("Подписка на запрос с Authorization: " + token))
                .doOnNext(response -> System.out.println("Ответ API: " + response))
                .doOnError(error -> System.err.println("Ошибка API: " + error.getMessage()))
                .onErrorReturn("Ошибка запроса");
    }

    private Mono<String> sendGatheringRequest(String token, String name) {
        String payload = String.format("{\"name\":\"%s\"}", name);

        return webClient.post()
                .uri("/{name}/action/gathering", name)
                .header("Authorization", token.startsWith("Bearer ") ? token : "Bearer " + token)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSubscribe(subscription -> System.out.println("Подписка на запрос с Authorization: " + token))
                .doOnNext(response -> System.out.println("Ответ API: " + response))
                .doOnError(error -> System.err.println("Ошибка API: " + error.getMessage()))
                .onErrorReturn("Ошибка запроса");
    }

    public void gathering(String token, String name, int count) {
        for (int i = 0; i < count; i++) {
            sendGatheringRequest(token, name)
                    .block();
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }


}
