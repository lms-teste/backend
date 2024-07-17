package com.example.turmas.repository; // Pacote onde a interface está localizada

import com.example.turmas.model.Turma; // Importa a classe Turma que representa a entidade
import org.springframework.data.jpa.repository.JpaRepository; // Importa a interface JpaRepository para operações CRUD
import org.springframework.stereotype.Repository; // Importa a anotação Repository para indicar que é um repositório

@Repository // Anotação que indica que a interface é um repositório Spring
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    // Esta interface herda métodos padrão para operações de banco de dados,
    // como salvar, buscar, atualizar e excluir registros da entidade 'Turma'.
    // O tipo 'Turma' é a entidade e 'Long' é o tipo do identificador da entidade.
}
