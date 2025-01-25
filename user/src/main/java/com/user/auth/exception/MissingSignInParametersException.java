package com.user.auth.exception;

public class MissingSignInParametersException extends RuntimeException {
  public MissingSignInParametersException(String message) {
    super(message);
  }
}
