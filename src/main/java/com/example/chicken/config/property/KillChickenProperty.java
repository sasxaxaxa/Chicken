package com.example.chicken.config.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class KillChickenProperty {
  private final String path;
}
