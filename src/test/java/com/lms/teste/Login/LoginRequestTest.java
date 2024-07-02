package com.lms.teste.Login;
import org.junit.jupiter.api.Test;
import com.lms.teste.Models.LoginRequest;
import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {
     @Test
    public void testGetEmail() {
        LoginRequest loginRequest = new LoginRequest();
        String testEmail = "uesb@email.com";
        loginRequest.setEmail(testEmail);

        assertEquals(testEmail, loginRequest.getEmail());
    }

    @Test
    public void testSetEmail() {
        LoginRequest loginRequest = new LoginRequest();
        String testEmail = "uesb@email.com";
        loginRequest.setEmail(testEmail);

        assertEquals(testEmail, loginRequest.getEmail());
    }

    @Test
    public void testGetSenha() {
        LoginRequest loginRequest = new LoginRequest();
        String testPassword = "password123";
        loginRequest.setSenha(testPassword);

        assertEquals(testPassword, loginRequest.getSenha());
    }

    @Test
    public void testSetSenha() {
        LoginRequest loginRequest = new LoginRequest();
        String testPassword = "password123";
        loginRequest.setSenha(testPassword);

        assertEquals(testPassword, loginRequest.getSenha());
    }
}
