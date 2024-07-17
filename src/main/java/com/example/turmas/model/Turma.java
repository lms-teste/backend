package com.example.turmas.model; // Pacote onde a classe está localizada

import jakarta.persistence.Entity; // Importa a anotação Entity do Jakarta Persistence
import jakarta.persistence.GeneratedValue; // Importa a anotação para geração automática de valores
import jakarta.persistence.GenerationType; // Importa as estratégias de geração de IDs
import jakarta.persistence.Id; // Importa a anotação para definir a chave primária
import jakarta.persistence.Column; // Importa a anotação para definir colunas

@Entity // Indica que esta classe é uma entidade JPA, representando uma tabela no banco de dados
public class Turma {

    @Id // Define o campo 'id' como chave primária da entidade
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que o valor do 'id' será gerado automaticamente
    private Long id; // Atributo que representa o identificador único da turma

    @Column(nullable = false) // Define a coluna 'nome' como não nula
    private String nome; // Atributo que representa o nome da turma

    @Column(nullable = false) // Define a coluna 'criador' como não nula
    private String criador; // Atributo que representa o criador da turma

    @Column(name = "codigo_convite", unique = true) // Define a coluna 'codigo_convite' como única
    private String codigoConvite; // Atributo que representa o código de convite da turma

    // Construtor padrão (sem parâmetros)
    public Turma() {}

    // Construtor com parâmetros para inicializar os atributos
    public Turma(String nome, String criador, String codigoConvite) {
        this.nome = nome;
        this.criador = criador;
        this.codigoConvite = codigoConvite;
    }

    // Getters e Setters para acessar e modificar os atributos

    public Long getId() {
        return id; // Retorna o ID da turma
    }

    public void setId(Long id) {
        this.id = id; // Define o ID da turma
    }

    public String getNome() {
        return nome; // Retorna o nome da turma
    }

    public void setNome(String nome) {
        this.nome = nome; // Define o nome da turma
    }

    public String getCriador() {
        return criador; // Retorna o criador da turma
    }

    public void setCriador(String criador) {
        this.criador = criador; // Define o criador da turma
    }

    public String getCodigoConvite() {
        return codigoConvite; // Retorna o código de convite da turma
    }

    public void setCodigoConvite(String codigoConvite) {
        this.codigoConvite = codigoConvite; // Define o código de convite da turma
    }

    @Override
    public String toString() {
        // Retorna uma representação em String da turma, incluindo todos os atributos
        return "Turma{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", criador='" + criador + '\'' +
                ", codigoConvite='" + codigoConvite + '\'' +
                '}';
    }
}
