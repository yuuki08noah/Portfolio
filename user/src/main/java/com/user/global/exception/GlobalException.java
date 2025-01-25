package com.user.global.exception;

import com.user.auth.exception.MissingSignInParametersException;
import com.user.user.exception.UserAlreadyExistsException;
import com.user.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.user.global.exception.ErrorCode.*;

@RestControllerAdvice
@Slf4j
public class GlobalException {
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(USER_NOT_FOUND));
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(USER_ALREADY_EXISTS));
  }

  @ExceptionHandler(MissingSignInParametersException.class)
  public ResponseEntity<ErrorResponse> handleMissingSignInParametersException(MissingSignInParametersException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(MISSING_SIGN_IN_PARAMETERS));
  }
}
