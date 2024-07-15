package com.lms.teste.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.teste.Exceptions.UserNotFoundException;
import com.lms.teste.Models.User;
import com.lms.teste.Repository.UserRepository;

import java.util.List;

@Service // Deve ser gerenciada com um conteineer de Inversao de controle do String
public class UserService {
    @Autowired // injeta uma dependencia no UserRepository
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
        return userRepository.save(user);
    }
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User createUser(User user) {
        User newUser = new User();

        BeanUtils.copyProperties(user, newUser);
        User userCreated = userRepository.save(newUser);

        return userCreated;
    }

    @Transactional
    public User findByEmail(String Email) {
        return userRepository.findByEmail(Email);
    }

}