package com.user.auth.controller;

import com.user.auth.controller.dto.request.SignInByEmailDTO;
import com.user.auth.controller.dto.request.SignInByIdDTO;
import com.user.auth.exception.MissingSignInParametersException;
import com.user.auth.service.AuthService;
import com.user.auth.service.TokenService;
import com.user.user.exception.UserNotFoundException;
import com.user.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final TokenService tokenService;
  private final AuthService authService;
  private final UserService userService;

  @PostMapping("/signin/id")
  public ResponseEntity<String> signin(@RequestBody SignInByIdDTO signInByIdDTO, HttpServletResponse response) throws MissingSignInParametersException, UserNotFoundException {
    authService.signIn(signInByIdDTO);
    addCookie(tokenService.createToken(signInByIdDTO.id()), response);
    return ResponseEntity.ok("User id "+ signInByIdDTO.id() + " successfully signed in.");
  }

  @PostMapping("/signin/email")
  public ResponseEntity<String> signin(@RequestBody SignInByEmailDTO signInByEmailDTO, HttpServletResponse response) throws MissingSignInParametersException, UserNotFoundException {
    authService.signIn(signInByEmailDTO);
    addCookie(tokenService.createToken(signInByEmailDTO.email()), response);
    return ResponseEntity.ok("User id "+ signInByEmailDTO.email() + " successfully signed in.");
  }

//  @GetMapping("/signout")
//  public ResponseEntity<String> signout(HttpServletResponse response) {
//    String refreshToken = response.getHeader("RefreshToken");
//    if (refreshToken != null) {
//
//    }
//    tokenService.deleteToken(response.getHeader("RefreshToken"))
//  }

  private Cookie makeCookie(String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(maxAge);
    return cookie;
  }

  private void addCookie(Pair<String, String> token, HttpServletResponse response) {
    Cookie accessToken = makeCookie("Authorization", token.getFirst(), 3600);
    Cookie refreshToken = makeCookie("RefreshToken", token.getSecond(), 259200);

    response.addCookie(accessToken);
    response.addCookie(refreshToken);
  }
}
