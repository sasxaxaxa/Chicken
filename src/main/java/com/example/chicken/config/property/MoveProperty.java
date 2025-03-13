package com.example.chicken.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "move-path")
public class MoveProperty extends KillChickenProperty {
  public MoveProperty(String path) {
    super(path);
  }
}
