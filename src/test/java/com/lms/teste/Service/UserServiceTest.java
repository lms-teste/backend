package com.lms.teste.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lms.teste.Exceptions.UserNotFoundException;
import com.lms.teste.Models.User;
import com.lms.teste.Repository.UserRepository;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    /**
     * GetUserById 
     *  - success (user found)
     *  - failed (user not found)
     * 
     * DeleteUser
     *  - success (user deleted successfully)
     *  - failed (user not found)
     * 
     * FindByEmail
     *  - success (user found)
     *  - failed (user not found)
     * 
     * CreateUser
     *  - success (user created)
     *  - failed (email arealdy exists)
     *  - failed (password min chars)
     *  - failed (role nonexistent)
     * 
     * UpdateUser
     *  - success (user found)
     *  - failed (user not found)
     *  - failed (email arealdy exists)
     *  - failed (password min chars)
     *  - failed (role nonexistent)
     */


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "Test User", "password", "test@example.com", User.Role.ADMIN, true);
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals(user, savedUser);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = Arrays.asList(user,
                new User(2L, "Another User", "password", "another@example.com", User.Role.ADMIN, true));
        when(userRepository.findAll()).thenReturn(userList);
        List<User> users = userService.getAllUsers();
        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user, foundUser);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_Failed() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser_Success() {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_Failed() {
        doThrow(new UserNotFoundException("Usuário não encontrado")).when(userRepository).deleteById(1L);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user, createdUser);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deverá lançar uma exceção do tipo RuntimeException")
    void testCreateUser_EmailAlreadyExists() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userService.createUser(user)); // Substitua RuntimeException por uma exceção específica

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testCreateUser_PasswordMinChars() {
        user.setSenha("123"); // Senha com menos de 6 caracteres
        assertThrows(RuntimeException.class, () -> userService.createUser(user)); // Substitua RuntimeException por uma exceção específica

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testCreateUser_RoleNonexistent() {
        user.setPapel(null); // Role inexistente
        assertThrows(RuntimeException.class, () -> userService.createUser(user)); // Substitua RuntimeException por uma exceção específica

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testUpdateUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals(user, updatedUser);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_Failed() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user));

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testUpdateUser_EmailAlreadyExists() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userService.updateUser(user)); // Substitua RuntimeException por uma exceção específica

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testUpdateUser_PasswordMinChars() {
        user.setSenha("123"); // Senha com menos de 6 caracteres
        assertThrows(RuntimeException.class, () -> userService.updateUser(user)); // Substitua RuntimeException por uma exceção específica

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testUpdateUser_RoleNonexistent() {
        user.setPapel(null); // Role inexistente
        assertThrows(RuntimeException.class, () -> userService.updateUser(user)); // Substitua RuntimeException por uma exceção específica

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testFindByEmail_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        User foundUser = userService.findByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals(user, foundUser);

        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testFindByEmail_Failed() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.findByEmail("test@example.com"));

        verify(userRepository, times(1)).findByEmail("test@example.com");
    }



   
}
