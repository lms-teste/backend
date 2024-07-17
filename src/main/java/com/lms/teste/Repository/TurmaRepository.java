package com.lms.teste.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.teste.Models.TurmaModel;

public interface TurmaRepository extends JpaRepository<TurmaModel, Long> {
}
