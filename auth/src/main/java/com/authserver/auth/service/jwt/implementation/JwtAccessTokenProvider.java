package com.authserver.auth.service.jwt.implementation;

import com.authserver.auth.service.jwt.JwtProvider;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;

@Service
public class JwtAccessTokenProvider extends JwtProvider {
  private static final String accessTokenKey;
  private static final Long expiration;
  private final Key accessKey;

  static {
    accessTokenKey = System.getenv("JWT_ACCESSTOKEN_KEY");
    expiration = Long.parseLong(System.getenv("JWT_ACCESSTOKEN_EXPIRATION"));
  }

  public JwtAccessTokenProvider() {
    accessKey = Keys.hmacShaKeyFor(accessTokenKey.getBytes());
  }

  public String createToken(Long userId) {
    return super.createToken(userId, expiration, accessKey);
  }

  public Boolean validateToken(String token) {
    return super.validateToken(token, accessKey);
  }

  public Long getUserId(String token) {
    return super.getUserId(token, accessKey);
  }
}