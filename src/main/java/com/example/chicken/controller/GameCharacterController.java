package com.example.chicken.controller;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import com.example.chicken.service.GameCharacterService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/character")
public class GameCharacterController {
    private final GameCharacterService gameCharacterService;

    public GameCharacterController(GameCharacterService gameCharacterService) {
        this.gameCharacterService = gameCharacterService;
    }

    @PostMapping("/{name}/move")
    public CompletableFuture<String> moveCharacter(
            @RequestHeader("Authorization") String token,
            @PathVariable String name,
            @RequestParam int x,
            @RequestParam int y
    ) {
        return gameCharacterService.move(token, name, x, y);
    }

    @PostMapping("/{name}/gather")
    public CompletableFuture<String> gatherResources(
            @RequestHeader("Authorization") String token,
            @PathVariable String name
    ) {
        return gameCharacterService.gathering(token, name);
    }

}
