package com.example.turmas.controller;

// Importa as classes necessárias
import com.example.turmas.model.Turma; // Modelo da turma
import com.example.turmas.repository.TurmaRepository; // Repositório da turma
import org.junit.jupiter.api.BeforeEach; // Para configuração de testes
import org.junit.jupiter.api.Test; // Para os métodos de teste
import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependências
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // Para configuração do MockMvc
import org.springframework.boot.test.context.SpringBootTest; // Para configuração do contexto do Spring
import org.springframework.http.MediaType; // Para tipos de mídia
import org.springframework.test.web.servlet.MockMvc; // Para simular requisições HTTP

import static org.hamcrest.Matchers.is; // Para asserções de matchers
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // Para construir requisições
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // Para asserções de resultados

// Anotações para configurar o teste
@SpringBootTest // Carrega o contexto do Spring
@AutoConfigureMockMvc // Habilita o uso do MockMvc para testar o controlador
class TurmaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc para simular requisições HTTP

    @Autowired
    private TurmaRepository turmaRepository; // Repositório para manipulação de dados

    // Método executado antes de cada teste
    @BeforeEach
    void setUp() {
        turmaRepository.deleteAll(); // Limpa o repositório antes de cada teste
    }

    // Teste do método listarTurmas
    @Test
    void listarTurmas() throws Exception {
        // Cria e salva duas turmas no repositório
        Turma turma1 = new Turma("Turma 1", "Criador 1", "Convite1");
        Turma turma2 = new Turma("Turma 2", "Criador 2", "Convite2");
        turmaRepository.save(turma1);
        turmaRepository.save(turma2);

        // Simula uma requisição GET para /turmas
        mockMvc.perform(get("/turmas"))
                .andExpect(status().isOk()) // Verifica se o status da resposta é 200 OK
                .andExpect(jsonPath("$[0].nome", is("Turma 1"))) // Verifica se o nome da primeira turma é "Turma 1"
                .andExpect(jsonPath("$[1].nome", is("Turma 2"))); // Verifica se o nome da segunda turma é "Turma 2"
    }

    // Teste do método criarTurma
    @Test
    void criarTurma() throws Exception {
        // Simula uma requisição POST para criar uma nova turma
        mockMvc.perform(post("/turmas")
                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo como JSON
                .content("{\"nome\": \"Nova Turma\", \"criador\": \"Novo Criador\", \"codigoConvite\": \"NovoConvite\"}")) // Dados da nova turma
                .andExpect(status().isOk()) // Verifica se o status da resposta é 200 OK
                .andExpect(jsonPath("$.nome", is("Nova Turma"))); // Verifica se o nome da turma criada é "Nova Turma"
    }

    // Teste do método atualizarTurma
    @Test
    void atualizarTurma() throws Exception {
        // Cria e salva uma turma original no repositório
        Turma turma = new Turma("Turma Original", "Criador Original", "ConviteOriginal");
        turma = turmaRepository.save(turma); // Salva a turma original

        // Simula uma requisição PUT para atualizar a turma
        mockMvc.perform(put("/turmas/" + turma.getId()) // Endpoint com o ID da turma
                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo como JSON
                .content("{\"nome\": \"Turma Atualizada\", \"criador\": \"Criador Atualizado\", \"codigoConvite\": \"ConviteAtualizado\"}")) // Dados da turma atualizada
                .andExpect(status().isOk()) // Verifica se o status da resposta é 200 OK
                .andExpect(jsonPath("$.nome", is("Turma Atualizada"))); // Verifica se o nome da turma atualizada é "Turma Atualizada"
    }

    // Teste do método excluirTurma
    @Test
    void excluirTurma() throws Exception {
        // Cria e salva uma turma a ser excluída no repositório
        Turma turma = new Turma("Turma a Ser Excluída", "Criador", "ConviteExcluir");
        turma = turmaRepository.save(turma); // Salva a turma

        // Simula uma requisição DELETE para excluir a turma
        mockMvc.perform(delete("/turmas/" + turma.getId())) // Endpoint com o ID da turma
                .andExpect(status().isNoContent()); // Verifica se o status da resposta é 204 No Content
    }
}

