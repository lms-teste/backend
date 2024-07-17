package com.lms.teste.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
  private LocalDateTime data;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    data = LocalDateTime.now();
    atividade = new Atividade(1L, "Atividade 1", "Atividade teste", data, data, false, 10);
  }

  @Test
  void testGetAtividades() {
    Atividade novaAtividade = new Atividade(2L, "Atividade 2", "Atividade teste", data, data, false, 10);
    List<Atividade> atividades = Arrays.asList(atividade, novaAtividade);

    when(atividadeRepository.findAll()).thenReturn(atividades);

    List<Atividade> atividadesSalvas = atividadeService.list();

    assertNotNull(atividadesSalvas);
    assertEquals(2, atividadesSalvas.size());
    verify(atividadeRepository, times(1)).findAll();
  }

  @Test
  void testGetAtividadeById() {
    Long atividadeId = 1L;

    when(atividadeRepository.findById(atividadeId)).thenReturn(Optional.of(atividade));
    Atividade atividadeEncontrada = atividadeService.getById(atividadeId);

    assertNotNull(atividadeEncontrada);
    assertEquals(atividade, atividadeEncontrada);

    verify(atividadeRepository, times(1)).findById(atividadeId);
  }

  @Test
  void testGetAtividadeByIdNotFound() {
    Long fakeAtividadeId = 2L;

    when(atividadeRepository.findById(fakeAtividadeId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> atividadeService.getById(fakeAtividadeId));

    verify(atividadeRepository, times(1)).findById(fakeAtividadeId);
  }

  @Test
  void testCreateAtividade() {
    when(atividadeRepository.save(any(Atividade.class))).thenReturn(atividade);
    Atividade retornoAtividade = atividadeService.save(atividade);

    assertNotNull(retornoAtividade);
    assertEquals(atividade, retornoAtividade);

    verify(atividadeRepository, times(1)).save(any(Atividade.class));
  }

  @Test
  void testUpdateAtividade() {
    when(atividadeRepository.findById(atividade.getId())).thenReturn(Optional.of(atividade));
    when(atividadeRepository.save(any(Atividade.class))).thenReturn(atividade);

    Atividade retornoAtividade = atividadeService.update(atividade, atividade.getId());

    assertNotNull(retornoAtividade);
    assertEquals(atividade, retornoAtividade);

    verify(atividadeRepository, times(1)).save(any(Atividade.class));
  }

  @Test
  void testUpdateAtividadeNotfound() {
    Long fakeAtividadeId = 2L;

    when(atividadeRepository.findById(fakeAtividadeId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> atividadeService.update(atividade, atividade.getId()));

    verify(atividadeRepository, times(0)).save(any(Atividade.class));
  }

  @Test
  void testDeleteAtividade() {
    Long atividadeId = 1L;
    atividadeService.delete(atividadeId);
    verify(atividadeRepository, times(1)).deleteById(atividadeId);
  }
}
