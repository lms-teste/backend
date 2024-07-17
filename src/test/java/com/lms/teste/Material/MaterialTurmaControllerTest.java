package com.lms.teste.Material;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.lms.teste.Controller.MaterialTurmaController;
import com.lms.teste.Models.MaterialTurma;
import com.lms.teste.Service.MaterialTurmaService;

@WebMvcTest(MaterialTurmaController.class)
public class MaterialTurmaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaterialTurmaService turmaService;

    private MaterialTurma turma;

    @BeforeEach
    public void setUp() {
        turma = new MaterialTurma();
        turma.setIdTurma(1L);
        turma.setNomeMaterial("Material 1");
        turma.setMaterial(new byte[] { 1, 2, 3 });
    }

    @Test
    public void testCreateTurma() throws Exception {
        when(turmaService.save(any(MaterialTurma.class))).thenReturn(turma);

        mockMvc.perform(multipart("/api/materiais/turmas")
                .file("material", turma.getMaterial())
                .param("idTurma", "1")
                .param("nomeMaterial", turma.getNomeMaterial())
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTurma").value(1L));
    }

    @Test
    public void testGetAllTurmas() throws Exception {
        when(turmaService.findAll()).thenReturn(Arrays.asList(turma));

        mockMvc.perform(get("/api/materiais/turmas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTurma").value(1L));
    }

    @Test
    public void testGetTurmaById() throws Exception {
        when(turmaService.findById(1L)).thenReturn(turma);

        mockMvc.perform(get("/api/materiais/turmas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTurma").value(1L));
    }

    @Test
    public void testUpdateTurma() throws Exception {
        when(turmaService.findById(1L)).thenReturn(turma);
        when(turmaService.save(any(MaterialTurma.class))).thenReturn(turma);

        mockMvc.perform(multipart("/api/materiais/turmas/1")
                .file("material", turma.getMaterial())
                .param("nomeMaterial", turma.getNomeMaterial())
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTurma").value(1L));
    }

    @Test
    public void testDeleteTurma() throws Exception {
        doNothing().when(turmaService).deleteById(1L);

        mockMvc.perform(delete("/api/materiais/turmas/1"))
                .andExpect(status().isNoContent());
    }
}
