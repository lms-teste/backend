package com.lms.teste.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

  }

  @Test
  public void testGetAtividadeById() {

  }

  @Test
  public void testCreateAtividade() {
    // Cria novo objeto Atividade
    Atividade atividade = new Atividade();

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
      // Requisição POST para "/api/users/"
      mockMvc.perform(post("/api/atividades/")
          .contentType(MediaType.APPLICATION_JSON)
          .content(atividadeJson))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").isNumber())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON));
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
