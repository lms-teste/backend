package com.lms.teste.Exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
      super(message);
  }
}
