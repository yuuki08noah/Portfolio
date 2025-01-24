package com.authserver.auth.service;

import com.authserver.auth.service.jwt.implementation.JwtAccessTokenProvider;
import com.authserver.auth.service.jwt.implementation.JwtRefreshTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final JwtAccessTokenProvider jwtAccessTokenProvider;
  private final JwtRefreshTokenProvider jwtRefreshTokenProvider;
  private final RedisTemplate<String, String> redisTemplate;

  public Pair<String, String> createToken(Long userId) {
    String accessToken = jwtAccessTokenProvider.createToken(userId);
    String refreshToken = jwtRefreshTokenProvider.createToken(userId);
    redisTemplate.opsForValue().set(userId.toString(), refreshToken);
    return Pair.of(accessToken, refreshToken);
  }

  public Boolean deleteToken(String accessToken) {
    return redisTemplate.delete(jwtAccessTokenProvider.getUserId(accessToken).toString());
  }

  public Pair<String, Pair<String, String>> validateToken(String accessToken, String refreshToken) {
    if (jwtAccessTokenProvider.validateToken(accessToken)) {
      return Pair.of("access", Pair.of("", ""));
    } else {
      if (jwtRefreshTokenProvider.validateToken(refreshToken)) {
        Pair<String, String> token = createToken(jwtRefreshTokenProvider.getUserId(refreshToken));
        return Pair.of("refresh", token);
      } else {
        return Pair.of("invalid", Pair.of("", ""));
      }
    }
  }


}
