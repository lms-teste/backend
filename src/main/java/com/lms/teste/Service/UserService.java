package com.lms.teste.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.teste.Exceptions.UserNotFoundException;
import com.lms.teste.Models.User;
import com.lms.teste.Models.User.Role;
import com.lms.teste.Repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    public User updateUser(User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new UserNotFoundException("Usuário não encontrado");
        }

        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            throw new RuntimeException("Email already exists");
        }
        if (user.getSenha().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters long");
        }
        if (user.getPapel() == null) {
            throw new RuntimeException("Role must be specified");
        }
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (user.getSenha().length() < 8) {
            throw new IllegalArgumentException("Password must have at least 8 characters");
        }

        if (!isRoleValid(user.getPapel())) {
            throw new IllegalArgumentException("Role does not exist");
        }

        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        return userRepository.save(newUser);
    }

    private boolean isRoleValid(Role role) {
        List<Role> validRoles = Arrays.asList(Role.USER, Role.ADMIN);
        return validRoles.contains(role);
    }

    @Transactional
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Usuário não encontrado");
        }
        return user;
    }
}