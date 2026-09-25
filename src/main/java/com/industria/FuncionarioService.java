package com.industria;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contém as operações relacionadas ao processamento dos funcionários.
 * A responsabilidade desta classe é executar as regras de negócio do exercício.
 * A classe Principal fica responsável pelo fluxo da aplicação e pela
 * apresentação dos resultados no console.
 */
public class FuncionarioService {

    /**
     * Remove um funcionário pelo nome.
     * A comparação é feita ignorando diferenças entre letras maiúsculas e minúsculas.
     * @param funcionarios lista de funcionários
     * @param nome nome do funcionário que será removido
     */
    public void removerFuncionarioPorNome(
            List<Funcionario> funcionarios,
            String nome) {

        funcionarios.removeIf(
                funcionario ->
                        funcionario.getNome()
                                .equalsIgnoreCase(nome)
        );
    }

    /**
     * Aplica um percentual de aumento a todos os funcionários.
     * O percentual deve ser informado em formato decimal.
     * Exemplos:
     * 0.10 = 10%
     * 0.05 = 5%
     * 0.00 = 0%
     * Um percentual negativo não é aceito porque esta operação representa especificamente
     * um aumento salarial.
        @param funcionarios lista de funcionários
        @param percentual percentual representado em formato decimal
     */
    public void aplicarAumento(
            List<Funcionario> funcionarios,
            BigDecimal percentual) {

        if (percentual == null) {
            throw new IllegalArgumentException(
                    "O percentual de aumento não pode ser nulo."
            );
        }

        if (percentual.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "O percentual de aumento não pode ser negativo."
            );
        }

        BigDecimal fator =
                BigDecimal.ONE.add(percentual);

        funcionarios.forEach(funcionario -> {

            BigDecimal novoSalario =
                    funcionario.getSalario()
                            .multiply(fator)
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP
                            );

            funcionario.setSalario(novoSalario);
        });
    }

    /**
     * Agrupa os funcionários de acordo com sua função.
     * @param funcionarios lista de funcionários
     * @return mapa onde a chave é a função e o valor é a lista de funcionários daquela função
     */
    public Map<String, List<Funcionario>> agruparPorFuncao(
            List<Funcionario> funcionarios) {

        return funcionarios.stream()
                .collect(
                        Collectors.groupingBy(
                                Funcionario::getFuncao
                        )
                );
    }

    /**
     * Busca funcionários que fazem aniversário nos meses informados.
     * @param funcionarios lista de funcionários
     * @param meses meses que devem ser pesquisados
     * @return lista de aniversariantes
     */
    public List<Funcionario> buscarAniversariantes(
            List<Funcionario> funcionarios,
            List<Integer> meses) {

        return funcionarios.stream()
                .filter(
                        funcionario ->
                                meses.contains(
                                        funcionario
                                                .getDataNascimento()
                                                .getMonthValue()
                                )
                )
                .collect(Collectors.toList());
    }

    /**
     * Busca o funcionário mais velho.
     * A data de nascimento mais antiga representa a pessoa mais velha.
     * @param funcionarios lista de funcionários
     * @return funcionário mais velho
     * @throws IllegalArgumentException se a lista estiver vazia
     */
    public Funcionario buscarMaisVelho(
            List<Funcionario> funcionarios) {

        return funcionarios.stream()
                .min(
                        Comparator.comparing(
                                Funcionario::getDataNascimento
                        )
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "A lista de funcionários está vazia."
                        )
                );
    }

    /**
     * Calcula a idade atual de uma pessoa.
     * @param dataNascimento data de nascimento
     * @return idade em anos completos
     */
    public int calcularIdade(
            LocalDate dataNascimento) {

        return Period.between(
                dataNascimento,
                LocalDate.now()
        ).getYears();
    }

    /**
     * Retorna os funcionários ordenados alfabeticamente pelo nome.
     * A lista original não é modificada.
     * @param funcionarios lista original
     * @return nova lista ordenada
     */
    public List<Funcionario> ordenarPorNome(
            List<Funcionario> funcionarios) {

        return funcionarios.stream()
                .sorted(
                        Comparator.comparing(
                                Funcionario::getNome
                        )
                )
                .collect(Collectors.toList());
    }

    /**
     * Calcula a soma dos salários de todos os funcionários.
     * BigDecimal é utilizado para preservar a precisão os valores monetários.
     * @param funcionarios lista de funcionários
     * @return soma dos salários
     */
    public BigDecimal calcularTotalSalarios(
            List<Funcionario> funcionarios) {

        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }
}