package com.example.chicken.controller;

import com.example.chicken.service.GameCharacterService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/character")
public class GameCharacterController {
    private final GameCharacterService gameCharacterService;

    public GameCharacterController(GameCharacterService gameCharacterService) {
        this.gameCharacterService = gameCharacterService;
    }

    @PostMapping("/{name}/move")
    public Mono<String> moveCharacter(
            @RequestHeader("Authorization") String token,
            @PathVariable String name,
            @RequestParam int x,
            @RequestParam int y
    ) {
        return gameCharacterService.move(token, name, x, y);
    }

    @PostMapping("/{name}/gather")
    public void gatherResources(
            @RequestHeader("Authorization") String token,
            @PathVariable String name,
            @RequestBody GatherRequest request
    ) {
        System.out.println("Received name: " + name);
        System.out.println("Received count: " + request.getCount());
        gameCharacterService.gathering(token, name, request.getCount());
    }

    public static class GatherRequest {
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
