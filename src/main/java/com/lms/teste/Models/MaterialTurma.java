package com.lms.teste.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "materiais_turmas")
public class MaterialTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurma;

    private Long idMaterial;

    @Lob
    private byte[] material;

    private String nomeMaterial;

    

    public MaterialTurma(Long idTurma, Long idMaterial, byte[] material, String nomeMaterial) {
        this.idTurma = idTurma;
        this.idMaterial = idMaterial;
        this.material = material;
        this.nomeMaterial = nomeMaterial;
    }

    

    public MaterialTurma() {
    }



    // Getters e Setters
    public Long getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Long idTurma) {
        this.idTurma = idTurma;
    }

    public byte[] getMaterial() {
        return material;
    }

    public void setMaterial(byte[] material) {
        this.material = material;
    }

    public String getNomeMaterial() {
        return nomeMaterial;
    }

    public void setNomeMaterial(String nomeMaterial) {
        this.nomeMaterial = nomeMaterial;
    }

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

}
