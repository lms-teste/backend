package com.lms.teste.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
    void testGetUserByIdSuccess(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user, foundUser);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByIdFailed() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));

        verify(userRepository, times(1)).findById(1L);
    }


    @Test
    void testFindByEmailSuccess() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        User foundUser = userService.findByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals(user, foundUser);

        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    public void testFindByEmailFailedUserNotFound() {
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.findByEmail(email);
        });
    }


    @Test
    void testDeleteUserSuccess() {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        doThrow(new UserNotFoundException("Usuário não encontrado")).when(userRepository).deleteById(1L);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));

        verify(userRepository, times(1)).deleteById(1L);
    }


    @Test
    void testCreateUserSuccess() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user, createdUser);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUserFailedEmailAlreadyExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testCreateUserFailedRoleNonexistent() {
        user.setPapel(null);

        assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testCreateUserFailedPasswordMinChars() {
        user.setSenha("123");

        assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });

        verify(userRepository, times(0)).save(any(User.class));
    }


    @Test
    public void testUpdateUserSuccess() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(user);
        assertNotNull(updatedUser);
        assertEquals(updatedUser.getId(), user.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUserFailedUserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(user);
        });
    }

    @Test
    public void testUpdateUserFailedEmailAlreadyExists() {
        User anotherUser = new User();
        anotherUser.setId(user.getId() + 1);
        anotherUser.setEmail("test2@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(anotherUser);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> {
            userService.updateUser(user);
        });
    }

    @Test
    public void testUpdateUserFailedPasswordMinChars() {
        user.setSenha("123"); // setting password less than minimum characters required

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> {
            userService.updateUser(user);
        });
    }

    @Test
    public void testUpdateUserFailedRoleNonexistent() {
        user.setPapel(null); // setting role to null

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> {
            userService.updateUser(user);
        });
    }


}
