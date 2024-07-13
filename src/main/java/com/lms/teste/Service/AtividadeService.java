package com.lms.teste.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.teste.Models.Atividade;
import com.lms.teste.Repository.AtividadeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository repositorio;

    public List<Atividade> list() {
        return repositorio.findAll();
    }

    public Atividade getById(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("nao foi possivel achar essa atividade"));
    }

    public Atividade save(Atividade atividade) {
        return repositorio.save(atividade);
    }

    public Atividade update(Atividade atividade, Long id) {
        var atividadeAntiga = getById(id);
        atividade.setId(atividadeAntiga.getId());
        return save(atividade);
    }

    public void delete(Long id) {
        repositorio.deleteById(id);

    }
}
