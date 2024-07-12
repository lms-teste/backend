package com.lms.teste.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lms.teste.Models.MaterialTurma;
import com.lms.teste.Repository.MaterialTurmaRepository;

@Service
public class MaterialTurmaService {

    @Autowired
    private MaterialTurmaRepository turmaRepository;

    public MaterialTurma save(MaterialTurma turma) {
        return turmaRepository.save(turma);
    }

    public List<MaterialTurma> findAll() {
        return turmaRepository.findAll();
    }

    public MaterialTurma findById(Long id) {
        return turmaRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        turmaRepository.deleteById(id);
    }

}
