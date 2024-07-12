package com.lms.teste.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.teste.Models.MaterialAtividade;
import com.lms.teste.Repository.MaterialAtividadeRepository;

@Service
public class MaterialAtividadeService {

    @Autowired
    private MaterialAtividadeRepository atividadeRepository;

    public MaterialAtividade save(MaterialAtividade atividade) {
        return atividadeRepository.save(atividade);
    }

    public List<MaterialAtividade> findAll() {
        return atividadeRepository.findAll();
    }

    public MaterialAtividade findById(Long id) {
        return atividadeRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        atividadeRepository.deleteById(id);
    }
}
