package com.lms.teste.Controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.lms.teste.Models.TurmaModel;
import com.lms.teste.Service.TurmaService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TurmaController.class)
public class TurmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TurmaService turmaService;

    @Test
    void testGetAllTurmas() throws Exception {
        List<TurmaModel> turmas = List.of(new TurmaModel(1L, "Turma A", "Criador A", "ABC123", List.of("Aluno1")));
        Mockito.when(turmaService.getAllTurmas()).thenReturn(turmas);

        mockMvc.perform(get("/api/turmas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Turma A"));
    }

    @Test
    void testGetTurmaById() throws Exception {
        TurmaModel turma = new TurmaModel(1L, "Turma A", "Criador A", "ABC123", List.of("Aluno1"));
        Mockito.when(turmaService.getTurmaById(1L)).thenReturn(Optional.of(turma));

        mockMvc.perform(get("/api/turmas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Turma A"));
    }

    @Test
    void testCreateTurma() throws Exception {
        TurmaModel turma = new TurmaModel(null, "Turma A", "Criador A", "ABC123", List.of("Aluno1"));
        TurmaModel createdTurma = new TurmaModel(1L, "Turma A", "Criador A", "ABC123", List.of("Aluno1"));
        Mockito.when(turmaService.createTurma(any(TurmaModel.class))).thenReturn(createdTurma);

        String turmaJson = """
                {
                    "nome": "Turma A",
                    "criador": "Criador A",
                    "codigoConvite": "ABC123",
                    "listaAlunos": ["Aluno1"]
                }
                """;

        mockMvc.perform(post("/api/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(turmaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Turma A"));
    }

    @Test
    void testUpdateTurma() throws Exception {
        TurmaModel turma = new TurmaModel(null, "Turma A", "Criador A", "ABC123", List.of("Aluno1"));
        TurmaModel updatedTurma = new TurmaModel(1L, "Turma B", "Criador B", "DEF456", List.of("Aluno2"));
        Mockito.when(turmaService.updateTurma(anyLong(), any(TurmaModel.class))).thenReturn(updatedTurma);

        String turmaJson = """
                {
                    "nome": "Turma B",
                    "criador": "Criador B",
                    "codigoConvite": "DEF456",
                    "listaAlunos": ["Aluno2"]
                }
                """;

        mockMvc.perform(put("/api/turmas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(turmaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Turma B"));
    }

    @Test
    void testDeleteTurma() throws Exception {
        Mockito.doNothing().when(turmaService).deleteTurma(1L);

        mockMvc.perform(delete("/api/turmas/1"))
                .andExpect(status().isNoContent());
    }
}

