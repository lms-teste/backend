package com.lms.teste.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.teste.Models.Atividade;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long>{

    
}
