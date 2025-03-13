package com.example.chicken.controller;

import com.example.chicken.request.GatherRequest;
import com.example.chicken.service.GameCharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
@Slf4j
public class GameCharacterController {

  private final GameCharacterService gameCharacterService;

  @PostMapping("/{name}/move")
  public String moveCharacter(
      @PathVariable String name,
      @RequestParam int x,
      @RequestParam int y
  ) {
    return gameCharacterService.move(name, x, y);
  }

  @PostMapping("/{name}/gather")
  public void gatherResources(
      @PathVariable String name,
      @RequestBody GatherRequest request

  ) {
    gameCharacterService.gathering(name, request.count());
  }
}
