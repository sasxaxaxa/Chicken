package com.example.chicken.exception;

public class KillChickenClientException extends RuntimeException {


  public KillChickenClientException(String message) {
    super(message);
  }

  public KillChickenClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
