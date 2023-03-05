package com.esliceu.Forum.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
public class TokenService {
    @Value("${token.secret}")
    String tokenSecret;

    @Value("${token.expiration}")
    String tokenExpiration;


    public String newToken(String user) {
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(Long.parseLong(tokenExpiration));
        Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
        String token = JWT.create()
                .withSubject(user)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
        return token;
    }

    public String getUser(String token) {
        return JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }
}
