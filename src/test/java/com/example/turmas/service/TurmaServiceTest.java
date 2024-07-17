package com.example.turmas.service;

// Importa as classes necessárias
import com.example.turmas.model.Turma; // Modelo da turma
import com.example.turmas.repository.TurmaRepository; // Repositório da turma
import org.junit.jupiter.api.BeforeEach; // Para configuração de testes
import org.junit.jupiter.api.Test; // Para os métodos de teste
import org.mockito.InjectMocks; // Para injetar mocks
import org.mockito.Mock; // Para criar mocks
import org.mockito.MockitoAnnotations; // Para inicializar os mocks

import java.util.Arrays; // Para trabalhar com arrays
import java.util.List; // Para usar listas

import static org.junit.jupiter.api.Assertions.*; // Para asserções
import static org.mockito.Mockito.*; // Para métodos do Mockito

// Classe de teste para o serviço TurmaService
class TurmaServiceTest {

    @Mock
    private TurmaRepository turmaRepository; // Mock do repositório

    @InjectMocks
    private TurmaService turmaService; // Serviço a ser testado

    // Método executado antes de cada teste
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    // Teste do método listarTurmas
    @Test
    void listarTurmas() {
        // Cria duas turmas de exemplo
        Turma turma1 = new Turma("Turma 1", "Criador 1", "Convite1");
        Turma turma2 = new Turma("Turma 2", "Criador 2", "Convite2");
        List<Turma> turmas = Arrays.asList(turma1, turma2); // Adiciona as turmas a uma lista

        // Configura o mock para retornar a lista de turmas
        when(turmaRepository.findAll()).thenReturn(turmas);

        // Chama o método a ser testado
        List<Turma> result = turmaService.listarTurmas();

        // Verifica se o resultado está correto
        assertEquals(2, result.size()); // Verifica se o tamanho da lista é 2
        verify(turmaRepository, times(1)).findAll(); // Verifica se findAll foi chamado uma vez
    }

    // Teste do método criarTurma
    @Test
    void criarTurma() {
        Turma turma = new Turma("Nova Turma", "Novo Criador", "NovoConvite"); // Nova turma a ser criada

        // Configura o mock para retornar a turma criada
        when(turmaRepository.save(any(Turma.class))).thenReturn(turma);

        // Chama o método a ser testado
        Turma result = turmaService.criarTurma(turma);

        // Verifica se o resultado está correto
        assertNotNull(result); // Verifica se o resultado não é nulo
        assertEquals("Nova Turma", result.getNome()); // Verifica se o nome está correto
        verify(turmaRepository, times(1)).save(turma); // Verifica se save foi chamado uma vez
    }

    // Teste do método atualizarTurma
    @Test
    void atualizarTurma() {
        Long id = 1L; // ID da turma que será atualizada
        Turma turma = new Turma("Turma Atualizada", "Criador Atualizado", "ConviteAtualizado"); // Turma atualizada

        // Configura o mock para indicar que a turma existe
        when(turmaRepository.existsById(id)).thenReturn(true);
        when(turmaRepository.save(any(Turma.class))).thenReturn(turma); // Retorna a turma atualizada

        // Chama o método a ser testado
        Turma result = turmaService.atualizarTurma(id, turma);

        // Verifica se o resultado está correto
        assertNotNull(result); // Verifica se não é nulo
        assertEquals("Turma Atualizada", result.getNome()); // Verifica se o nome está correto
        verify(turmaRepository, times(1)).existsById(id); // Verifica se existsById foi chamado uma vez
        verify(turmaRepository, times(1)).save(turma); // Verifica se save foi chamado uma vez
    }

    // Teste do método excluirTurma
    @Test
    void excluirTurma() {
        Long id = 1L; // ID da turma que será excluída

        // Configura o mock para indicar que a turma existe
        when(turmaRepository.existsById(id)).thenReturn(true);

        // Chama o método a ser testado
        boolean result = turmaService.excluirTurma(id);

        // Verifica se o resultado está correto
        assertTrue(result); // Verifica se a exclusão foi bem-sucedida
        verify(turmaRepository, times(1)).existsById(id); // Verifica se existsById foi chamado uma vez
        verify(turmaRepository, times(1)).deleteById(id); // Verifica se deleteById foi chamado uma vez
    }
}
