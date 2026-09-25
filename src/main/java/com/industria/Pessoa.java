package com.industria;

import java.time.LocalDate;

/**
 * Representa os dados básicos de uma pessoa.
 * A classe é usada como classe base para Funcionario, permitindo reutilizar
 * nome e data de nascimento através do conceito de herança da orientação a objetos.
 */
public class Pessoa {

    private String nome;
    private LocalDate dataNascimento;

    /**
     * Construtor responsável por criar uma pessoa já com seus dados básicos obrigatórios.
     */
    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}