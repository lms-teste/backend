package com.lms.teste.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import com.lms.teste.Service.MaterialTurmaService;
import com.lms.teste.Models.MaterialTurma;

@RestController
@RequestMapping("/api/materiais/turmas")
public class MaterialTurmaController {
    @Autowired
    private MaterialTurmaService turmaService;

    @PostMapping
    public MaterialTurma createTurma(@RequestParam("nomeMaterial") String nomeMaterial, @RequestParam("material") MultipartFile material) throws IOException {
        MaterialTurma turma = new MaterialTurma();
        turma.setNomeMaterial(nomeMaterial);
        turma.setMaterial(material.getBytes());
        return turmaService.save(turma);
    }

    @GetMapping
    public List<MaterialTurma> getAllTurmas() {
        return turmaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialTurma> getTurmaById(@PathVariable Long id) {
        MaterialTurma turma = turmaService.findById(id);
        
        if(turma != null) {
            return ResponseEntity.ok(turma);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialTurma> updateTurma(@PathVariable Long id, @RequestParam("nomeMaterial") String nomeMaterial, @RequestParam("material") MultipartFile material) throws IOException {
        MaterialTurma turma = turmaService.findById(id);

        if(turma != null) {
            turma.setNomeMaterial(nomeMaterial);
            turma.setMaterial(material.getBytes());
            return ResponseEntity.ok(turmaService.save(turma));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        turmaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
