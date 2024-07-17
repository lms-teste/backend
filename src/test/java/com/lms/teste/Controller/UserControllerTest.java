package com.lms.teste.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.teste.Exceptions.UserNotFoundException;
import com.lms.teste.Models.LoginRequest;
import com.lms.teste.Models.TokenRequest;
import com.lms.teste.Models.User;
import com.lms.teste.Service.UserService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private Auth auth;

    private User user;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * GetUser 
     *  - success (user found)
     *  - failed (user not found)
     * 
     * DeleteUser
     *  - failed (user not found)
     * 
     * FindByEmail
     *  - success (user found)
     *  - failed (user not found)
     * 
     * CreateUser
     *  - failed (email arealdy exists)
     *  - failed (password min chars)
     *  - failed (role nonexistent)
     * 
     * UpdateUser
     *  - failed (email arealdy exists)
     *  - failed (password min chars)
     *  - failed (role nonexistent)
     */

     @Test
    void testGetUserById_Success() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserById_Failed() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }


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
    public void testDeleteUser_Success() {
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

    
    @Test
    public void testDeleteUser_Failed(){
        Long userId = 1L;
        doThrow(new UserNotFoundException("Usuário não encontrado")).when(userService).deleteUser(userId);

        try{
            mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByEmail_Success(){
        when(userService.findByEmail("test@example.com")).thenReturn(user);

        try {
            mockMvc.perform(get("/users/email/{email}", "test@example.com"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                    .andExpect(jsonPath("$.name", is(user.getNome())))
                    .andExpect(jsonPath("$.email", is(user.getEmail())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Test
    public void testFindByEmail_Failed(){
        when(userService.findByEmail("test@example.com")).thenThrow(new UserNotFoundException("User not found"));

        try {
            mockMvc.perform(get("/users/email/{email}", "test@example.com"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateUser_Success() {
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
    
    @Test
    public void testCreateUserFailed_EmailAlreadyExists() throws Exception {
        when(userService.createUser(any(User.class))).thenThrow(new RuntimeException("Email already exists"));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserFailed_PasswordMinLimitNotReached() throws Exception {
        User userWithShortPassword = new User(1L, "Test User", "short", "test@example.com", User.Role.ADMIN, true);

        when(userService.createUser(any(User.class))).thenThrow(new RuntimeException("Password must be at least 8 characters"));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userWithShortPassword)))
                .andExpect(status().isBadRequest());
    }
    

    @Test
    public void testCreateUserFailed_RoleNonExistent() throws Exception {
        User userWithInvalidRole = new User(1L, "Test User", "password", "test@example.com", null, true);

        when(userService.createUser(any(User.class))).thenThrow(new RuntimeException("Role does not exist"));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userWithInvalidRole)))
                .andExpect(status().isBadRequest());
    }

    
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("vamosTestar@uesb.com");
        loginRequest.setSenha("senha123");

        User user = new User();
        user.setId(1L);
        user.setNome("Vamos Testar");
        user.setSenha("senha123");
        user.setEmail("vamosTestar@uesb.com");
        user.setPapel(User.Role.USER);

        when(userService.findByEmail("vamosTestar@uesb.com")).thenReturn(user);
        when(auth.generateToken("1", "USER", "Vamos Testar")).thenReturn("token");

        ResponseEntity<?> response = userController.login(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);

        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertEquals("token", responseBody.get("token"));
    }

    @Test
    public void testLoginUserNotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("userinexistente@uesb.com");
        loginRequest.setSenha("password");

        when(userService.findByEmail("userinexistente@uesb.com")).thenReturn(null);

        ResponseEntity<?> response = userController.login(loginRequest);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    public void testLoginInvalidPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("teste@uesb.com");
        loginRequest.setSenha("senhaErrada");

        User user = new User();
        user.setId(1L);
        user.setNome("Usuario Teste");
        user.setSenha("senha123");
        user.setEmail("teste@uesb.com");
        user.setPapel(User.Role.USER);

        when(userService.findByEmail("teste@uesb.com")).thenReturn(user);

        ResponseEntity<?> response = userController.login(loginRequest);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    public void testLoginException() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("teste@uesb.com");
        loginRequest.setSenha("senha123");

        when(userService.findByEmail("teste@uesb.com")).thenThrow(new RuntimeException());

        ResponseEntity<?> response = userController.login(loginRequest);
        assertEquals(HttpStatus.REQUEST_TIMEOUT, response.getStatusCode());
    }

    @Test
    public void testValidateTokenSuccess() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setToken("validToken");

        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(decodedJWT.getSubject()).thenReturn("1");

        Claim nameClaim = mock(Claim.class);
        when(nameClaim.asString()).thenReturn("Test User");
        when(decodedJWT.getClaim("name")).thenReturn(nameClaim);

        Claim roleClaim = mock(Claim.class);
        when(roleClaim.asString()).thenReturn("USER");
        when(decodedJWT.getClaim("role")).thenReturn(roleClaim);

        when(auth.decodeToken(tokenRequest)).thenReturn(decodedJWT);

        ResponseEntity<?> response = userController.validateToken(tokenRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(1L, responseBody.get("id"));
        assertEquals("Test User", responseBody.get("nome"));
        assertEquals("USER", responseBody.get("papel"));
    }

    @Test
    public void testValidateTokenEmptyToken() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setToken("");
        ResponseEntity<?> response = userController.validateToken(tokenRequest);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    public void testValidateTokenException() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setToken("invalidToken");
        when(auth.decodeToken(any(TokenRequest.class))).thenThrow(new RuntimeException());
        ResponseEntity<?> response = userController.validateToken(tokenRequest);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }
}
