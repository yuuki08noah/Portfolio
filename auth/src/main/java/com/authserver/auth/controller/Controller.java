package com.authserver.auth.controller;

import com.authserver.auth.controller.dto.request.SignInDTO;
import com.authserver.auth.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class Controller {
  private final RefreshTokenService refreshTokenService;

  @PostMapping("/signin")
  public String signin(@RequestBody SignInDTO signInDTO, HttpServletResponse response) {
    if (Long.valueOf(signInDTO.userId()) == 1L && signInDTO.password().equals("aing")) {
      Pair<String, String> token = refreshTokenService.createToken(signInDTO.userId());
      Cookie accessToken = makeCookie("Authorization", token.getFirst(), 3600);
      Cookie refreshToken = makeCookie("RefreshToken", token.getSecond(), 259200);

      response.addCookie(accessToken);
      response.addCookie(refreshToken);
      return "SUCCESS";
    }
    return "FAIL";
  }

  @GetMapping("/test")
  public String test(
          @CookieValue(value = "Authorization", required = false) String accessToken,
          @CookieValue(value = "RefreshToken", required = false) String refreshToken,
          HttpServletResponse response
          ) {
    Pair<String, Pair<String, String>> result = refreshTokenService.validateToken(accessToken, refreshToken);
    if (result.getFirst().equals("access")) {
      return "ACCESS";
    } else if (result.getFirst().equals("refresh")) {
      Pair<String, String> token = result.getSecond();
      Cookie newAccessToken = makeCookie("Authorization", token.getFirst(), Integer.parseInt(System.getenv("JWT_ACCESSTOKEN_EXPIRATION")));
      Cookie newRefreshToken = makeCookie("RefreshToken", token.getSecond(), Integer.parseInt(System.getenv("JWT_REFRESHTOKEN_EXPIRATION")));
      response.addCookie(newAccessToken);
      response.addCookie(newRefreshToken);
      return "REFRESH";
    }
    return "Failure";
  }

  private Cookie makeCookie(String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(maxAge);
    return cookie;
  }
}
