package com.lms.teste.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.teste.Exceptions.UserNotFoundException;
import com.lms.teste.Models.LoginRequest;
import com.lms.teste.Models.User;
import com.lms.teste.Service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private Auth auth;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetAllUsers() {
        // Mock de dados de usuários

        User user1 = new User(1L, "Teste 1", "senha1", "teste1@teste.com", User.Role.USER, true);
        User user2 = new User(2L, "Teste 2", "senha2", "teste2@teste.com", User.Role.ADMIN, true);
        List<User> userList = Arrays.asList(user1, user2);

        // Mock do serviço para retornar os usuários
        when(userService.getAllUsers()).thenReturn(userList);

        try {
            // Requisição GET para "/api/users/"
            mockMvc.perform(get("/api/users/")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].nome").value("Teste 1"))
                    .andExpect(jsonPath("$[1].nome").value("Teste 2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateUser() {
        // Objeto de usuário para criar
        User user = new User(null, "Novo Usuário", "senha123", "novo@usuario.com", User.Role.USER, true);

        // Mock do serviço para criar usuário
        when(userService.createUser(any(User.class))).thenReturn(user);

        String userJson = ""; // Inicialize com uma string vazia para evitar erros de compilação
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            // Requisição POST para "/api/users/"
            mockMvc.perform(post("/api/users/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userJson))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.nome").value("Novo Usuário"))
                    .andExpect(jsonPath("$.email").value("novo@usuario.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDeleteUser() {
        // ID do usuário a ser deletado
        Long userId = 1L;

        // Requisição DELETE para "/api/users/{id}"
        try {
            mockMvc.perform(delete("/api/users/{id}", userId))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Verifica se o método deleteUser foi chamado com o ID correto
        verify(userService, times(1)).deleteUser(userId);

    }

     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User(userId, "Old Name", "oldpassword", "oldemail@example.com", User.Role.USER, true);

        User updatedDetails = new User(userId, "New Name", "newpassword", "newemail@example.com", User.Role.ADMIN, false);

        when(userService.getUserById(userId)).thenReturn(existingUser);
        when(userService.updateUser(any(User.class))).thenReturn(updatedDetails);

        ObjectMapper objectMapper = new ObjectMapper();
        String updatedDetailsJson = "";

        try {
            updatedDetailsJson = objectMapper.writeValueAsString(updatedDetails);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            // Act
            mockMvc.perform(put("/api/users/{id}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedDetailsJson))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.nome").value("New Name"))
                    .andExpect(jsonPath("$.email").value("newemail@example.com"))
                    .andExpect(jsonPath("$.senha").value("newpassword"))
                    .andExpect(jsonPath("$.papel").value("ADMIN"))
                    .andExpect(jsonPath("$.ativo").value(false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateUser_UserNotFound() {
        Long userId = 1L;
        User updatedDetails = new User();
        updatedDetails.setNome("New Name");

        when(userService.getUserById(userId)).thenThrow(new UserNotFoundException("Usuário não encontrado"));

        ObjectMapper objectMapper = new ObjectMapper();
        String updatedDetailsJson = "";

        try {
            updatedDetailsJson = objectMapper.writeValueAsString(updatedDetails);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(put("/api/users/{id}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedDetailsJson))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Usuário não encontrado"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(userService, times(1)).getUserById(userId);
        verify(userService, times(0)).updateUser(any(User.class));
    }

}
