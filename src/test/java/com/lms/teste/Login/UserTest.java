package com.lms.teste.Login;
import org.junit.jupiter.api.Test;
import com.lms.teste.Models.User;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserConstructorAndGetters() {
        User user = new User(1L, "Alan Turing", "password123", "alan.turing@email.com", User.Role.ADMIN, true);

        assertEquals(1L, user.getId());
        assertEquals("Alan Turing", user.getNome());
        assertEquals("password123", user.getSenha());
        assertEquals("alan.turing@email.com", user.getEmail());
        assertEquals(User.Role.ADMIN, user.getPapel());
        assertTrue(user.getAtivo());
    }

    @Test
    public void testSetters() {
        User user = new User();
        user.setId(1L);
        user.setNome("Alan Turing");
        user.setSenha("password123");
        user.setEmail("alan.turing@email.com");
        user.setPapel(User.Role.USER);
        user.setAtivo(false);

        assertEquals(1L, user.getId());
        assertEquals("Alan Turing", user.getNome());
        assertEquals("password123", user.getSenha());
        assertEquals("alan.turing@email.com", user.getEmail());
        assertEquals(User.Role.USER, user.getPapel());
        assertFalse(user.getAtivo());
    }
}
