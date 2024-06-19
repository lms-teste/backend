package com.lms.teste.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String senha;
    private String email;
    private boolean ativo;
    public User() {
    }
    public User(Long id, String nome, String senha, String email, Role papel,boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.papel = papel;
        this.ativo = ativo;
    }

    @Enumerated(EnumType.STRING)
    private Role papel;

    public enum Role {
        ADMIN,
        USER
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getPapel() {
        return this.papel;
    }

    public void setPapel(Role papel) {
        this.papel = papel;
    }

    public boolean getAtivo(){
        return this.ativo;
    }

    public void setAtivo(boolean ativo){
        this.ativo=ativo;
    }


   
}

   
