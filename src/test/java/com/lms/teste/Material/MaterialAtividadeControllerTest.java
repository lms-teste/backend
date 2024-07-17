package com.lms.teste.Material;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.lms.teste.Controller.MaterialAtividadeController;
import com.lms.teste.Models.MaterialAtividade;
import com.lms.teste.Service.MaterialAtividadeService;

@WebMvcTest(MaterialAtividadeController.class)
public class MaterialAtividadeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaterialAtividadeService materialAtividadeService;

    @InjectMocks
    private MaterialAtividadeController materialAtividadeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(materialAtividadeController).build();
    }

    @Test
    void testCreateAtividade() throws Exception {
        MaterialAtividade atividade = new MaterialAtividade();
        atividade.setIdAtividade(1L);
        atividade.setNomeMaterial("Teste");
        atividade.setMaterial("Conteúdo".getBytes());

        when(materialAtividadeService.save(any(MaterialAtividade.class))).thenReturn(atividade);

        MockMultipartFile file = new MockMultipartFile("material", "teste.txt", "text/plain", "Conteúdo".getBytes());

        mockMvc.perform(multipart("/api/materiais/atividades")
                .file(file)
                .param("idAtividade", "1")
                .param("nomeMaterial", "Teste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAtividade").value(1L))
                .andExpect(jsonPath("$.nomeMaterial").value("Teste"));
    }

    @Test
    void testGetAllAtividades() throws Exception {
        MaterialAtividade atividade1 = new MaterialAtividade();
        atividade1.setIdAtividade(1L);
        atividade1.setNomeMaterial("Teste1");
        atividade1.setMaterial("Conteúdo1".getBytes());

        MaterialAtividade atividade2 = new MaterialAtividade();
        atividade2.setIdAtividade(2L);
        atividade2.setNomeMaterial("Teste2");
        atividade2.setMaterial("Conteúdo2".getBytes());

        List<MaterialAtividade> atividades = Arrays.asList(atividade1, atividade2);

        when(materialAtividadeService.findAll()).thenReturn(atividades);

        mockMvc.perform(get("/api/materiais/atividades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAtividade").value(1L))
                .andExpect(jsonPath("$[0].nomeMaterial").value("Teste1"))
                .andExpect(jsonPath("$[1].idAtividade").value(2L))
                .andExpect(jsonPath("$[1].nomeMaterial").value("Teste2"));
    }

    @Test
    void testGetAtividadeById() throws Exception {
        MaterialAtividade atividade = new MaterialAtividade();
        atividade.setIdAtividade(1L);
        atividade.setNomeMaterial("Teste");
        atividade.setMaterial("Conteúdo".getBytes());

        when(materialAtividadeService.findById(anyLong())).thenReturn(atividade);

        mockMvc.perform(get("/api/materiais/atividades/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAtividade").value(1L))
                .andExpect(jsonPath("$.nomeMaterial").value("Teste"));
    }

    @Test
    void testUpdateAtividade() throws Exception {
        MaterialAtividade atividade = new MaterialAtividade();
        atividade.setIdAtividade(1L);
        atividade.setNomeMaterial("Teste");
        atividade.setMaterial("Conteúdo".getBytes());

        when(materialAtividadeService.findById(anyLong())).thenReturn(atividade);
        when(materialAtividadeService.save(any(MaterialAtividade.class))).thenReturn(atividade);

        MockMultipartFile file = new MockMultipartFile("material", "teste.txt", "text/plain",
                "Conteúdo atualizado".getBytes());

        mockMvc.perform(multipart("/api/materiais/atividades/{id}", 1L)
                .file(file)
                .param("nomeMaterial", "Teste Atualizado")
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAtividade").value(1L))
                .andExpect(jsonPath("$.nomeMaterial").value("Teste Atualizado"));
    }

    @Test
    void testDeleteAtividade() throws Exception {
        mockMvc.perform(delete("/api/materiais/atividades/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
