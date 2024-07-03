package com.lms.teste.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.lms.teste.Models.LoginRequest;
import com.lms.teste.Models.TokenRequest;
import com.lms.teste.Models.User;
import com.lms.teste.Service.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController{
    @Autowired
    private UserService service;

    private Auth tocken = new Auth();

    @GetMapping("/")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return service.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        service.deleteUser(id);
    }
    
    @PostMapping("/")
    public User createUser(@RequestBody User user){
        return service.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Busque o usuário no banco de dados pelo e-mail
        try{
            User usuarioBanco = service.findByEmail(loginRequest.getEmail());
            
            // Verifique se o usuário existe e se a senha é correta
            if (usuarioBanco != null && usuarioBanco.getSenha().equals(loginRequest.getSenha())) {
                
                String token = tocken.generateToken("" + usuarioBanco.getId(), ""+usuarioBanco.getPapel(),usuarioBanco.getNome());
                
                // Retorne o token no corpo da resposta
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                // Se a validação falhar, retorne um status de erro
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestBody TokenRequest tokenRequest) {
        try {
            if(!tokenRequest.getToken().isEmpty()){
                DecodedJWT jwt  = tocken.decodeToken(tokenRequest);

                // Create a response map
                Map<String, Object> response = new HashMap<>();
                response.put("id", Long.parseLong(jwt.getSubject()));
                response.put("nome", jwt.getClaim("name").asString());
                response.put("papel", jwt.getClaim("role").asString());
    
                return ResponseEntity.ok(response);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
            

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    //(X)colocar try catch 
    //(x)colocar response para erros
    //()fazer teste de unidade 
    //()fazer teste de integra
}