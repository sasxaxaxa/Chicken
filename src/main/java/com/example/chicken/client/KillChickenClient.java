package com.example.chicken.client;

import com.example.chicken.client.request.KillChickenRequest;
import com.example.chicken.config.property.KillChickenProperty;
import org.springframework.core.ParameterizedTypeReference;

public interface KillChickenClient {

  <T> T killChickenRequest(KillChickenRequest killChickenRequest,
                           KillChickenProperty killChickenProperty,
                           ParameterizedTypeReference<T> responseType);
}
