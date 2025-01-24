package com.authserver.auth.exception;

public class InvalidJwtException extends Exception {
  public InvalidJwtException(String message) {
    super(message);
  }
}
