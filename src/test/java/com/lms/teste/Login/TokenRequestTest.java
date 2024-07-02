package com.lms.teste.Login;
import org.junit.jupiter.api.Test;

import com.lms.teste.Models.TokenRequest;
import static org.junit.jupiter.api.Assertions.*;

public class TokenRequestTest {

    @Test
    public void testGetToken() {
        TokenRequest tokenRequest = new TokenRequest();
        String testToken = "12345";
        tokenRequest.setToken(testToken);

        assertEquals(testToken, tokenRequest.getToken());
    }

    @Test
    public void testSetToken() {
        TokenRequest tokenRequest = new TokenRequest();
        String testToken = "54321";
        tokenRequest.setToken(testToken);
        assertEquals(testToken, tokenRequest.getToken());
    }
}
