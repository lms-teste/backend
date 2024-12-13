package com.lms.teste.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.teste.Models.TurmaModel;
import com.lms.teste.Service.TurmaService;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {
    private final TurmaService turmaService;

    @Autowired
    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping
    public List<TurmaModel> getAllTurmas() {
        return turmaService.getAllTurmas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaModel> getTurmaById(@PathVariable Long id) {
        return turmaService.getTurmaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TurmaModel createTurma(@RequestBody TurmaModel turma) {
        return turmaService.createTurma(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaModel> updateTurma(@PathVariable Long id, @RequestBody TurmaModel turma) {
        try {
            TurmaModel updatedTurma = turmaService.updateTurma(id, turma);
            return ResponseEntity.ok(updatedTurma);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        turmaService.deleteTurma(id);
        return ResponseEntity.noContent().build();
    }
}
