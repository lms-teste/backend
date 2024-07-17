package com.lms.teste.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lms.teste.Models.Atividade;
import com.lms.teste.Repository.AtividadeRepository;

public class AtividadeServiceTest {
  @Mock
  private AtividadeRepository atividadeRepository;

  @InjectMocks
  private AtividadeService atividadeService;

  private Atividade atividade;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    LocalDateTime data = LocalDateTime.now();
    atividade = new Atividade(1L, "Atividade 1", "Atividade teste", data, data, false, 10);
  }

  @Test
  void testSaveAtividade() {
    when(atividadeRepository.save(any(Atividade.class))).thenReturn(atividade);
    Atividade retornoAtividade = atividadeService.save(atividade);

    assertNotNull(retornoAtividade);
    assertEquals(atividade, retornoAtividade);

    verify(atividadeRepository, times(1)).save(any(Atividade.class));
  }
}
