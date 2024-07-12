package com.lms.teste.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lms.teste.Models.MaterialAtividade;
import com.lms.teste.Service.MaterialAtividadeService;

@RestController
@RequestMapping("/api/materiais/atividades")
public class MaterialAtividadeController {

    @Autowired
    private MaterialAtividadeService materialAtividadeService;

    @PostMapping("/")
    public MaterialAtividade createAtividade(@RequestParam("nomeMaterial") String nomeMaterial, @RequestParam("material") MultipartFile material) throws IOException {
        MaterialAtividade materialAtividade = new MaterialAtividade();
        materialAtividade.setNomeMaterial(nomeMaterial);
        materialAtividade.setMaterial(material.getBytes());
        return materialAtividadeService.save(materialAtividade);
    }

    @GetMapping("/")
    public List<MaterialAtividade> getAllAtividades() {
        return materialAtividadeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialAtividade> getAtividadeById(@PathVariable Long id) {
        MaterialAtividade atividade = materialAtividadeService.findById(id);
        if (atividade != null) {
            return ResponseEntity.ok(atividade);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialAtividade> updateAtividade(@PathVariable Long id, @RequestParam("nomeMaterial") String nomeMaterial, @RequestParam("material") MultipartFile material) throws IOException {
        MaterialAtividade materialAtividade = materialAtividadeService.findById(id);
        if (materialAtividade != null) {
            materialAtividade.setNomeMaterial(nomeMaterial);
            materialAtividade.setMaterial(material.getBytes());
            return ResponseEntity.ok(materialAtividadeService.save(materialAtividade));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtividade(@PathVariable Long id) {
        materialAtividadeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
