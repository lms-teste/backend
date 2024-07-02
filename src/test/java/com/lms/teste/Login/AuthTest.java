package com.lms.teste.Login;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lms.teste.Controller.Auth;
import com.lms.teste.Models.TokenRequest;
import org.junit.jupiter.api.Test;

public class AuthTest {
    @Test
    public void testGenerateToken() {
        Auth auth = new Auth();
        String id = "12345";
        String role = "admin";
        String name = "Alan Turing";

        String token = auth.generateToken(id, role, name);
        assertNotNull(token);

        String secretKey = "segredinho";
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth")
                .build(); 

        DecodedJWT jwt = verifier.verify(token);
        assertEquals(id, jwt.getSubject());
        assertEquals(role, jwt.getClaim("role").asString());
        assertEquals(name, jwt.getClaim("name").asString());
    }

    @Test
    public void testDecodeToken() {
        Auth auth = new Auth();
        String id = "12345";
        String role = "admin";
        String name = "Alan Turing";

        String token = auth.generateToken(id, role, name);
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setToken(token);

        DecodedJWT jwt = auth.decodeToken(tokenRequest);
        assertNotNull(jwt);
        assertEquals(id, jwt.getSubject());
        assertEquals(role, jwt.getClaim("role").asString());
        assertEquals(name, jwt.getClaim("name").asString());
    }
}
