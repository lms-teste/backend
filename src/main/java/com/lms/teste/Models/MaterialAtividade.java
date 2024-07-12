package com.lms.teste.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "materiais_atividades")
public class MaterialAtividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtividade;

    private Long idMaterial;

    @Lob
    private byte[] material;

    private String nomeMaterial;

    

    public MaterialAtividade(Long idAtividade, Long idMaterial, byte[] material, String nomeMaterial) {
        this.idAtividade = idAtividade;
        this.idMaterial = idMaterial;
        this.material = material;
        this.nomeMaterial = nomeMaterial;
    }

    

    public MaterialAtividade() {
    }



    // Getters e Setters
    public Long getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Long idAtividade) {
        this.idAtividade = idAtividade;
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