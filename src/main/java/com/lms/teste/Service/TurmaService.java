package com.lms.teste.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.teste.Models.TurmaModel;
import com.lms.teste.Repository.TurmaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {
    private final TurmaRepository turmaRepository;

    @Autowired
    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public List<TurmaModel> getAllTurmas() {
        return turmaRepository.findAll();
    }

    public Optional<TurmaModel> getTurmaById(Long id) {
        return turmaRepository.findById(id);
    }

    public TurmaModel createTurma(TurmaModel turma) {
        return turmaRepository.save(turma);
    }

    public TurmaModel updateTurma(Long id, TurmaModel turma) {
        if (turmaRepository.existsById(id)) {
            return turmaRepository.save(new TurmaModel(id, turma.nome(), turma.criador(), turma.codigoConvite(), turma.listaAlunos()));
        } else {
            throw new RuntimeException("Turma não encontrada");
        }
    }

    public void deleteTurma(Long id) {
        turmaRepository.deleteById(id);
    }
}
