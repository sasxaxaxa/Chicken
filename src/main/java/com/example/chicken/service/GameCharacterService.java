package com.example.chicken.service;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

//это аннотация Spring, которая говорит, что этот класс будет сервисом (логика работы с API).
@Service
public class GameCharacterService {
    private final AsyncHttpClient client = new DefaultAsyncHttpClient();
    private static final String BASE_URL = "https://api.artifactsmmo.com/my/";

    public CompletableFuture<String> move(String token, String name, int x, int y) {
        String url = BASE_URL + name + "/action/move";
        System.out.println("Запрос на API: " + url);

        String payload = String.format("{\"name\":\"%s\", \"x\":%d, \"y\":%d}", name, x, y);
        System.out.println("Тело запроса: " + payload);

        ListenableFuture<Response> responseFuture = client.preparePost(url)
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .setHeader("Authorization", "Bearer " + token)
                .setBody(payload)
                .execute();

        return responseFuture.toCompletableFuture()
                .thenApply(response -> {
                    System.out.println("Ответ API: " + response.getResponseBody()); // Лог ответа
                    return response.getResponseBody();
                })
                .exceptionally(ex -> {
                    System.err.println("Ошибка при запросе: " + ex.getMessage()); // Лог ошибки
                    return "Ошибка запроса";
                });
    }

    public CompletableFuture<String> gathering(String token, String name) {
        String url = BASE_URL + name + "/action/gathering";
        String payload = String.format("{\"name\":\"%s\"}", name);

        ListenableFuture<Response> responseFuture = client.preparePost(url)
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .setHeader("Authorization", "Bearer " + token)
                .setBody(payload)
                .execute();
        return responseFuture.toCompletableFuture().thenApply(Response::getResponseBody);
    }
}
