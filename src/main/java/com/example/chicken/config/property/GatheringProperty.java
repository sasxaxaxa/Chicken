package com.example.chicken.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gathering-path")
public class GatheringProperty extends KillChickenProperty {
  public GatheringProperty(String path) {
    super(path);
  }
}
