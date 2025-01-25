package com.user.auth.service;

import com.user.auth.controller.dto.request.SignInByEmailDTO;
import com.user.auth.controller.dto.request.SignInByIdDTO;
import com.user.auth.controller.dto.request.SignOutDTO;
import com.user.auth.exception.MissingSignInParametersException;
import com.user.user.dto.UserDTO;
import com.user.user.exception.UserNotFoundException;
import com.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserService userService;
  private final TokenService tokenService;

  public void signIn(SignInByIdDTO signInByIdDTO) throws MissingSignInParametersException, UserNotFoundException {
    if (signInByIdDTO.id() == null || signInByIdDTO.password() == null) {
      throw new MissingSignInParametersException("Missing parameters");
    }
    UserDTO userDTO = userService.getUserById(signInByIdDTO.id());
    if (!BCrypt.checkpw(signInByIdDTO.password(), userDTO.password())) {
      throw new MissingSignInParametersException("Missing parameters");
    }
  }

  public void signIn(SignInByEmailDTO signInByEmailDTO) throws MissingSignInParametersException, UserNotFoundException {
    if (signInByEmailDTO.email() == null || signInByEmailDTO.password() == null) {
      throw new MissingSignInParametersException("Missing parameters");
    }
    UserDTO userDTO = userService.getUserByEmail(signInByEmailDTO.email());
    if (!BCrypt.checkpw(signInByEmailDTO.password(), userDTO.password())) {
      throw new MissingSignInParametersException("Missing parameters");
    }
  }
}
