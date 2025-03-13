package com.example.chicken.client.request;

public record MoveRequest(String name, int x, int y) implements KillChickenRequest {
}
