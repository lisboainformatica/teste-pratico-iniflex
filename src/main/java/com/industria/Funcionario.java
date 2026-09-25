package com.industria;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa um funcionário da indústria.
 * Funcionario herda de Pessoa porque funcionário também é uma pessoa e,
 * portanto, possui nome e data de nascimento.
 */
public class Funcionario extends Pessoa {

    private BigDecimal salario;
    private String funcao;

    /**
     * Cria um funcionário utilizando os dados herdados de Pessoa e os
     * dados específicos do funcionário.
     */
    public Funcionario(
            String nome,
            LocalDate dataNascimento,
            BigDecimal salario,
            String funcao) {

        super(nome, dataNascimento);

        validarSalario(salario);
        validarFuncao(funcao);

        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    /**
     * Atualiza o salário do funcionário.
     * A validação também é realizada no setter porque o objeto pode ter seu
     * salário alterado depois de criado.
     */
    public void setSalario(BigDecimal salario) {
        validarSalario(salario);
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    /**
     * Atualiza a função do funcionário garantindo que o objeto não fique
     * com uma função vazia ou nula.
     */
    public void setFuncao(String funcao) {
        validarFuncao(funcao);
        this.funcao = funcao;
    }

    /**
     * Valida o salário antes de armazená-lo.
     * BigDecimal é utilizado para valores monetários porque oferece maior
     * precisão decimal do que tipos como double.
     */
    private void validarSalario(BigDecimal salario) {

        if (salario == null) {
            throw new IllegalArgumentException(
                    "O salário não pode ser nulo."
            );
        }

        if (salario.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "O salário não pode ser negativo."
            );
        }
    }

    /**
     * Valida a função do funcionário.
     * isBlank() também considera uma String contendo apenas espaços como inválida.
     */
    private void validarFuncao(String funcao) {

        if (funcao == null || funcao.isBlank()) {
            throw new IllegalArgumentException(
                    "A função não pode ser vazia."
            );
        }
    }
}