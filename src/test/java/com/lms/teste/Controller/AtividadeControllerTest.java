package com.lms.teste.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lms.teste.Models.Atividade;
import com.lms.teste.Service.AtividadeService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AtividadeController.class)
public class AtividadeControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Mock
  private Auth auth;

  @InjectMocks
  private AtividadeController atividadeController;

  @MockBean
  private AtividadeService atividadeService;

  private Atividade atividade;
  private LocalDateTime data;
  private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    // Pega data atual para criar as atividades
    data = LocalDateTime.now();
    // Cria novo objeto Atividade
    atividade = new Atividade(
        1L,
        "Atividade 1",
        "Atividade teste",
        data,
        data,
        false,
        10);
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  @Test
  public void testListAtividade() {
    Atividade atividade2 = new Atividade(
        2L,
        "Atividade 2",
        "Atividade teste",
        data,
        data,
        false,
        10);
    // Cria uma lista com duas atividades
    List<Atividade> atividades = Arrays.asList(atividade, atividade2);

    // Mock do service de Atividade
    when(atividadeService.list()).thenReturn(atividades);

    try {
      // Requisição GET para "/api/atividades/"
      mockMvc.perform(get("/api/atividades/")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0].id").isNumber())
          .andExpect(jsonPath("$[1].id").isNumber());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetAtividadeById() {
    // Mock do service de Atividade
    when(atividadeService.getById(atividade.getId())).thenReturn(atividade);

    try {
      // Requisição GET para "/api/atividades/"
      mockMvc.perform(get("/api/atividades/" + atividade.getId())
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.id").isNumber())
          .andExpect(jsonPath("$.id").value(atividade.getId()))
          .andExpect(jsonPath("$.titulo").value(atividade.getTitulo()))
          .andExpect(jsonPath("$.descricao").value(atividade.getDescricao()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetByIdNotFound() {
    Long fakeAtividadeId = 1L;

    // Mock do service de Atividade
    when(atividadeService.getById(fakeAtividadeId)).thenThrow(
        new RuntimeException("Atividade não encontrada"));

    try {
      mockMvc.perform(get("/api/atividades/{id}", fakeAtividadeId))
          .andExpect(status().isNotFound());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCreateAtividade() {
    // Mock do service de Atividade
    when(atividadeService.save(any(Atividade.class))).thenReturn(atividade);

    String atividadeJson = "";

    try {
      // Escreve o objeto atividade em JSON
      atividadeJson = mapper.writeValueAsString(atividade);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    try {
      // Requisição POST para "/api/atividades/"
      mockMvc.perform(post("/api/atividades/")
          .contentType(MediaType.APPLICATION_JSON)
          .content(atividadeJson))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.id").isNumber())
          .andExpect(jsonPath("$.id").value(atividade.getId()))
          .andExpect(jsonPath("$.titulo").value(atividade.getTitulo()))
          .andExpect(jsonPath("$.descricao").value(atividade.getDescricao()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testUpdateAtividade() {
    // Cria novo objeto Atividade para atualizar a outra
    Atividade atividadeAtualizada = new Atividade(
        0L,
        "Atividade 2",
        "Atividade atualizada",
        data,
        data,
        true,
        5);

    // Mock do service de Atividade
    when(atividadeService.getById(atividade.getId())).thenReturn(atividade);
    when(atividadeService
        .update(any(Atividade.class), any(Long.class)))
        .thenReturn(atividadeAtualizada);

    String atividadeAtualizadaJson = "";

    try {
      // Escreve o objeto atividade em JSON
      atividadeAtualizadaJson = mapper.writeValueAsString(atividadeAtualizada);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return;
    }

    try {
      // Requisição put para "/api/atividades/"
      mockMvc.perform(put("/api/atividades/{id}", atividade.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(atividadeAtualizadaJson))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.titulo").value(atividadeAtualizada.getTitulo()))
          .andExpect(jsonPath("$.descricao").value(atividadeAtualizada.getDescricao()))
          .andExpect(jsonPath("$.grupo").value(atividadeAtualizada.isGrupo()))
          .andExpect(jsonPath("$.notaMaxima").value(atividadeAtualizada.getNotaMaxima()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testUpdateAtividadeNotFound() {
    Long fakeAtividadeId = 1L;
    atividade.setTitulo("Atividade 2");

    // Mock do service de Atividade
    when(atividadeService
        .update(any(Atividade.class), any(Long.class)))
        .thenThrow(new RuntimeException("nao foi possivel achar essa atividade"));

    String atividadeJson = "";

    try {
      atividadeJson = mapper.writeValueAsString(atividade);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    try {
      mockMvc.perform(put("/api/atividades/{id}", fakeAtividadeId)
          .contentType(MediaType.APPLICATION_JSON)
          .content(atividadeJson))
          .andExpect(status().isNotFound())
          .andExpect(content().string("nao foi possivel achar essa atividade"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testDeleteAtividade() {
    // ID da atividade que será deletada
    Long atividadeId = 1L;

    // Requisição DELETE para "/api/atividade/{id}"
    try {
      mockMvc
          .perform(delete("/api/atividades/{id}", atividadeId))
          .andExpect(status().is(204));
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Verifica se o método de excluir atividade foi chamado com o ID correto
    verify(atividadeService, times(1)).delete(atividadeId);
  }
}
