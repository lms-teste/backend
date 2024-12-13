package com.lms.teste.Models;

//import javax.persistence.*;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public record TurmaModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    String nome,
    String criador,
    String codigoConvite,
    @ElementCollection
    List<String> listaAlunos
) {}
