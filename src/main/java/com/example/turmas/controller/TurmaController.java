package com.example.turmas.controller; // Pacote onde a classe está localizada

import com.example.turmas.model.Turma; // Importa a classe Turma, que representa a entidade
import com.example.turmas.service.TurmaService; // Importa o serviço para manipulação das turmas
import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação para injeção de dependências
import org.springframework.http.ResponseEntity; // Importa a classe ResponseEntity para manipulação de respostas HTTP
import org.springframework.web.bind.annotation.*; // Importa as anotações para o mapeamento de rotas

import java.util.List; // Importa a classe List para manipulação de listas

@RestController // Anotação que indica que esta classe é um controlador REST
@RequestMapping("/turmas") // Define a rota base para os endpoints desta classe
public class TurmaController {

    @Autowired // Anotação que injeta automaticamente a instância do serviço TurmaService
    private TurmaService turmaService;

    // Método para listar todas as turmas
    @GetMapping // Mapeia requisições GET para esta rota
    public List<Turma> listarTurmas() {
        return turmaService.listarTurmas(); // Chama o método do serviço para obter a lista de turmas
    }

    // Método para criar uma nova turma
    @PostMapping // Mapeia requisições POST para esta rota
    public Turma criarTurma(@RequestBody Turma turma) {
        return turmaService.criarTurma(turma); // Chama o método do serviço para salvar a turma recebida no corpo da requisição
    }

    // Método para atualizar uma turma existente
    @PutMapping("/{id}") // Mapeia requisições PUT para a rota com o ID da turma
    public ResponseEntity<Turma> atualizarTurma(@PathVariable Long id, @RequestBody Turma turmaDetails) {
        Turma turmaAtualizada = turmaService.atualizarTurma(id, turmaDetails); // Atualiza a turma com os detalhes fornecidos
        if (turmaAtualizada != null) {
            return ResponseEntity.ok(turmaAtualizada); // Retorna a turma atualizada com status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se a turma não existir
        }
    }

    // Método para excluir uma turma
    @DeleteMapping("/{id}") // Mapeia requisições DELETE para a rota com o ID da turma
    public ResponseEntity<Void> excluirTurma(@PathVariable Long id) {
        if (turmaService.excluirTurma(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se a exclusão for bem-sucedida
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se a turma não existir
        }
    }
}

