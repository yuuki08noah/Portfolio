package com.authserver.auth.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

public class JwtProvider {
  public String createToken(Long userId, Long expiration, Key key) {
    Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + expiration * 1000);

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  };

  public Long getUserId(String token, Key key) {
    return Long.valueOf(Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject());
  };

  public Boolean validateToken(String token, Key key) {
    try {
      Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  };
}
