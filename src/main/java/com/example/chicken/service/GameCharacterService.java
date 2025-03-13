package com.example.chicken.service;

import com.example.chicken.client.KillChickenClient;
import com.example.chicken.client.request.GatheringRequest;
import com.example.chicken.client.request.MoveRequest;
import com.example.chicken.config.property.GatheringProperty;
import com.example.chicken.config.property.MoveProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

//это аннотация Spring, которая говорит, что этот класс будет сервисом (логика работы с API).

@Service
@RequiredArgsConstructor
public class GameCharacterService {

  private final KillChickenClient killChickenClient;
  private final GatheringProperty gatheringProperty;
  private final MoveProperty moveProperty;

  public String move(String name, int x, int y) {
    var moveRequest = new MoveRequest(name, x, y);
    return killChickenClient.killChickenRequest(moveRequest, moveProperty,
                                                new ParameterizedTypeReference<String>() {
                                                });
  }

  public void gathering(String name, int count) {

    for (int i = 0; i < count; i++) {
      var gatheringRequest = new GatheringRequest(name);
      killChickenClient.killChickenRequest(gatheringRequest, gatheringProperty,
                                           new ParameterizedTypeReference<>() {
                                           });
      try {
        Thread.sleep(30000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      }
    }
  }


}
