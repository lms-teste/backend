package com.lms.teste.Controller;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lms.teste.Models.TokenRequest;

import java.util.HashMap;
import java.util.Map;

public class Auth {
  public String generateToken(String id, String role, String name) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", id);
    claims.put("role", role);
    claims.put("name", name);

    String secretKey = "segredinho";

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    JWTCreator.Builder jwtBuilder = JWT.create()
        .withIssuer("auth")
        .withSubject(id)
        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *  10)); // 10 horas // 60 * 60 * 

    for (Map.Entry<String, Object> entry : claims.entrySet()) {
      jwtBuilder.withClaim(entry.getKey(), (String) entry.getValue());
    }

    return jwtBuilder.sign(algorithm);
  }

  public DecodedJWT decodeToken(TokenRequest tokenRequest) {

    String secretKey = "segredinho";
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer("auth")
        .build(); // Reusable verifier instance

    DecodedJWT jwt = verifier.verify(tokenRequest.getToken());

    return jwt;
  }
  

}