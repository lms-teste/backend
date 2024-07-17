package com.example.turmas.service; // Pacote onde a classe está localizada

import com.example.turmas.model.Turma; // Importa a classe Turma, que representa a entidade
import com.example.turmas.repository.TurmaRepository; // Importa o repositório para acesso a dados
import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação para injeção de dependências
import org.springframework.stereotype.Service; // Importa a anotação que define esta classe como um serviço

import java.util.List; // Importa a classe List para manipulação de listas

@Service // Anotação que indica que esta classe é um serviço do Spring
public class TurmaService {

    @Autowired // Anotação que injeta automaticamente a instância do repositório TurmaRepository
    private TurmaRepository turmaRepository;

    // Método para listar todas as turmas
    public List<Turma> listarTurmas() {
        return turmaRepository.findAll(); // Chama o método do repositório para obter todas as turmas
    }

    // Método para criar uma nova turma
    public Turma criarTurma(Turma turma) {
        return turmaRepository.save(turma); // Chama o método do repositório para salvar a turma recebida
    }

    // Método para atualizar uma turma existente
    public Turma atualizarTurma(Long id, Turma turmaDetails) {
        // Verifica se a turma com o ID existe
        if (turmaRepository.existsById(id)) {
            turmaDetails.setId(id); // Define o ID da turma nos detalhes
            return turmaRepository.save(turmaDetails); // Salva e retorna a turma atualizada
        }
        return null; // Retorna null se a turma não existir
    }

    // Método para excluir uma turma
    public boolean excluirTurma(Long id) {
        // Verifica se a turma com o ID existe
        if (turmaRepository.existsById(id)) {
            turmaRepository.deleteById(id); // Exclui a turma pelo ID
            return true; // Retorna true se a exclusão for bem-sucedida
        }
        return false; // Retorna false se a turma não existir
    }
}
