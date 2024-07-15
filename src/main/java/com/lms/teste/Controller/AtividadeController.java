package com.lms.teste.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.teste.Models.Atividade;
import com.lms.teste.Service.AtividadeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/atividades")
@RequiredArgsConstructor
public class AtividadeController {

    private final AtividadeService service;

    @GetMapping
    public List<Atividade> list() {
        return service.list();
    }

    @GetMapping("/{atividadeId}")
    public Atividade getById(@PathVariable Long atividadeId) {
        return service.getById(atividadeId);
    }

    @PostMapping("/")
    public Atividade create(@RequestBody Atividade atividade) {
        return service.save(atividade);
    }

    @PutMapping("/{atividadeId}")
    public Atividade update(@PathVariable Long atividadeId, @RequestBody Atividade atividade) {
        return service.update(atividade, atividadeId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{atividadeId}")
    public void delete(@PathVariable Long atividadeId) {
        service.delete(atividadeId);
    }

       
    
}
