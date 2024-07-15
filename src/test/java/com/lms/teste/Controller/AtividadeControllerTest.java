package com.lms.teste.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  @Test
  public void testListAtividade() {
    //Pega data atual para criar as atividades
    LocalDateTime data = LocalDateTime.now();

    Atividade atividade1 = new Atividade(0, "Atividade 1", "Atividade teste", data, data, false, 0);
    Atividade atividade2 = new Atividade(1, "Atividade 2", "Atividade teste", data, data, false, 0);
    //Cria uma lista com duas atividades
    List<Atividade> atividades = Arrays.asList(atividade1, atividade2);

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
    //Pega data atual para criar uma atividade
    LocalDateTime data = LocalDateTime.now();

    // Cria novo objeto Atividade
    Atividade atividade1 = new Atividade(0, "Atividade", "Atividade teste", data, data, false, 0);

    // Mock do service de Atividade
    when(atividadeService.getById(atividade1.getId())).thenReturn(atividade1);

    try {
      // Requisição GET para "/api/atividades/"
      mockMvc.perform(get("/api/atividades/" + atividade1.getId())
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.id").isNumber())
          .andExpect(jsonPath("$.id").value(atividade1.getId()))
          .andExpect(jsonPath("$.titulo").value(atividade1.getTitulo()))
          .andExpect(jsonPath("$.descricao").value(atividade1.getDescricao()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCreateAtividade() {
    //Pega data atual para criar uma atividade
    LocalDateTime data = LocalDateTime.now();

    // Cria novo objeto Atividade
    Atividade atividade = new Atividade(0, "Atividade", "Atividade teste", data, data, false, 0);

    // Mock do service de Atividade
    when(atividadeService.save(any(Atividade.class))).thenReturn(atividade);

    String atividadeJson = "";
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      // Escreve o objeto atividade em JSON
      atividadeJson = objectMapper.writeValueAsString(atividade);
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

  }

  @Test
  public void testDeleteAtividade() {

  }
}
