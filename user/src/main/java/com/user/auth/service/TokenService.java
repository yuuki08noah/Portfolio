package com.user.auth.service;

import com.user.auth.service.jwt.implementation.JwtAccessTokenProvider;
import com.user.auth.service.jwt.implementation.JwtRefreshTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
  private final JwtAccessTokenProvider jwtAccessTokenProvider;
  private final JwtRefreshTokenProvider jwtRefreshTokenProvider;
  private final RedisTemplate<String, String> redisTemplate;

  public Pair<String, String> createToken(String userId) {
    String accessToken = jwtAccessTokenProvider.createToken(userId);
    String refreshToken = jwtRefreshTokenProvider.createToken(userId);
    redisTemplate.opsForValue().set(userId.toString(), refreshToken);
    return Pair.of(accessToken, refreshToken);
  }

  public Boolean deleteToken(String refreshToken) {
    return redisTemplate.delete(jwtRefreshTokenProvider.getUserId(refreshToken).toString());
  }

  public Boolean validateAccessToken(String accessToken) {
    if (jwtAccessTokenProvider.validateToken(accessToken)) {
      return true;
    }
    return false;
  }

  public Pair<String, Pair<String, String>> validateRefreshToken(String refreshToken) {
    if(jwtRefreshTokenProvider.validateToken(refreshToken)) {
      String userId = jwtRefreshTokenProvider.getUserId(refreshToken);
      String redis = redisTemplate.opsForValue().get(userId.toString());
      if(redis != null && redis.equals(refreshToken)) {
        deleteToken(refreshToken);
        Pair<String, String> token = createToken(userId);
        return Pair.of("refresh", token);
      }
      else {
        return Pair.of("invalid", Pair.of("", ""));
      }
    } else {
      return Pair.of("invalid", Pair.of("", ""));
    }
  }


}
