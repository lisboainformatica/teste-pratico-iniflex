package com.industria;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Classe principal do exercício.
 * Sua responsabilidade é controlar o fluxo da aplicação e apresentar os resultados no console.
 * As regras de negócio relacionadas aos funcionários estão concentradas na classe FuncionarioService.
 */
public class Principal {

    public static void main(String[] args) {

        DateTimeFormatter formatadorData =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DecimalFormat formatadorMoeda =
                criarFormatadorMoeda();

        /*
         * O Service concentra as regras de negócio
         * relacionadas aos funcionários.
         */
        FuncionarioService funcionarioService =
                new FuncionarioService();

        // Carrega os funcionários informados no exercício.
        List<Funcionario> funcionarios =
                carregarFuncionariosIniciais();

        System.out.println("======================================");
        System.out.println("FUNCIONÁRIOS INICIAIS");
        System.out.println("======================================");

        imprimirFuncionarios(
                funcionarios,
                formatadorData,
                formatadorMoeda
        );

        /*
         * Regra do exercício:
         * João deve ser removido da lista de funcionários.
         * A regra é executada pelo Service.
         */
        funcionarioService.removerFuncionarioPorNome(
                funcionarios,
                "João"
        );

        System.out.println("\n======================================");
        System.out.println("APÓS REMOVER JOÃO");
        System.out.println("======================================");

        imprimirFuncionarios(
                funcionarios,
                formatadorData,
                formatadorMoeda
        );

        /*
         * Regra do exercício:
         * todos os funcionários recebem aumento de 10%.
         * A regra de cálculo pertence ao Service.
         */
        funcionarioService.aplicarAumento(
                funcionarios,
                new BigDecimal("0.10")
        );

        System.out.println("\n======================================");
        System.out.println("APÓS AUMENTO DE 10%");
        System.out.println("======================================");

        imprimirFuncionarios(
                funcionarios,
                formatadorData,
                formatadorMoeda
        );

        /*
         * Agrupa os funcionários pela função exercida.
         * O Service executa o processamento e retorna
         * o resultado para o Principal apresentar.
         */
        Map<String, List<Funcionario>> funcionariosPorFuncao =
                funcionarioService.agruparPorFuncao(funcionarios);

        System.out.println("\n======================================");
        System.out.println("FUNCIONÁRIOS POR FUNÇÃO");
        System.out.println("======================================");

        funcionariosPorFuncao.forEach((funcao, lista) -> {

            System.out.println("\nFunção: " + funcao);

            lista.forEach(funcionario ->
                    System.out.println(
                            "- " + funcionario.getNome()
                    )
            );
        });

        /*
         * Busca os funcionários que fazem aniversário
         * nos meses 10 e 12.
         */
        List<Funcionario> aniversariantes =
                funcionarioService.buscarAniversariantes(
                        funcionarios,
                        List.of(10, 12)
                );

        System.out.println("\n======================================");
        System.out.println("ANIVERSARIANTES DE OUTUBRO E DEZEMBRO");
        System.out.println("======================================");

        aniversariantes.forEach(funcionario ->
                System.out.println(
                        funcionario.getNome()
                                + " - "
                                + funcionario
                                .getDataNascimento()
                                .format(formatadorData)
                )
        );

        /*
         * Busca o funcionário mais velho.
         * A regra de negócio é executada pelo Service.
         */
        Funcionario maisVelho =
                funcionarioService.buscarMaisVelho(
                        funcionarios
                );

        int idadeMaisVelho =
                funcionarioService.calcularIdade(
                        maisVelho.getDataNascimento()
                );

        System.out.println("\n======================================");
        System.out.println("FUNCIONÁRIO MAIS VELHO");
        System.out.println("======================================");

        System.out.println(
                maisVelho.getNome()
                        + " - "
                        + idadeMaisVelho
                        + " anos"
        );

        /*
         * Cria uma lista ordenada pelo nome.
         * O Service executa a ordenação e o Principal
         * apresenta o resultado.
         */
        List<Funcionario> funcionariosOrdenados =
                funcionarioService.ordenarPorNome(
                        funcionarios
                );

        System.out.println("\n======================================");
        System.out.println("FUNCIONÁRIOS EM ORDEM ALFABÉTICA");
        System.out.println("======================================");

        funcionariosOrdenados.forEach(funcionario ->
                System.out.println(
                        funcionario.getNome()
                )
        );

        /*
         * Calcula a soma dos salários.
         * O cálculo pertence ao Service.
         */
        BigDecimal totalSalarios =
                funcionarioService.calcularTotalSalarios(
                        funcionarios
                );

        System.out.println("\n======================================");
        System.out.println("TOTAL DOS SALÁRIOS");
        System.out.println("======================================");

        System.out.println(
                formatadorMoeda.format(totalSalarios)
        );

        /*
         * O exercício utiliza R$ 1.212,00 como salário mínimo.
         * Esta operação é apresentada diretamente no fluxo principal porque, neste exercício,
         * ela representa apenas a forma de exibir o salário de cada funcionário em quantidade
         * de salários mínimos.
         */
        BigDecimal salarioMinimo =
                new BigDecimal("1212.00");

        System.out.println("\n======================================");
        System.out.println("SALÁRIOS EM SALÁRIOS MÍNIMOS");
        System.out.println("======================================");

        for (Funcionario funcionario : funcionarios) {

            BigDecimal quantidade =
                    funcionario.getSalario()
                            .divide(
                                    salarioMinimo,
                                    2,
                                    RoundingMode.HALF_UP
                            );

            System.out.println(
                    funcionario.getNome()
                            + ": "
                            + quantidade
            );
        }
    }

    /**
     * Cria a lista inicial de funcionários.
     * Os valores são criados utilizando String no BigDecimal para evitar possíveis
     * imprecisões provocadas por double.
     */
    public static List<Funcionario> carregarFuncionariosIniciais() {

        List<Funcionario> funcionarios =
                new ArrayList<>();

        funcionarios.add(
                new Funcionario(
                        "Maria",
                        LocalDate.of(2000, 10, 18),
                        new BigDecimal("2009.44"),
                        "Operador"
                )
        );

        funcionarios.add(
                new Funcionario(
                        "João",
                        LocalDate.of(1990, 5, 12),
                        new BigDecimal("2284.38"),
                        "Operador"
                )
        );

        funcionarios.add(
                new Funcionario(
                        "Caio",
                        LocalDate.of(1961, 5, 2),
                        new BigDecimal("9836.14"),
                        "Coordenador"
                )
        );

        funcionarios.add(
                new Funcionario(
                        "Miguel",
                        LocalDate.of(1988, 10, 14),
                        new BigDecimal("19119.88"),
                        "Diretor"
                )
        );

        funcionarios.add(
                new Funcionario(
                        "Alice",
                        LocalDate.of(1995, 1, 5),
                        new BigDecimal("2234.68"),
                        "Recepcionista"
                )
        );

        funcionarios.add(
                new Funcionario(
                        "Heitor",
                        LocalDate.of(1999, 11, 19),
                        new BigDecimal("1582.72"),
                        "Operador"
                )
        );

        funcionarios.add(
                new Funcionario(
                        "Arthur",
                        LocalDate.of(1993, 3, 31),
                        new BigDecimal("4071.84"),
                        "Contador"
                )
        );

        funcionarios.add(
                new Funcionario(
                        "Laura",
                        LocalDate.of(1994, 7, 8),
                        new BigDecimal("3017.45"),
                        "Gerente"
                )
        );

        funcionarios.add(
                new Funcionario(
                        "Heloísa",
                        LocalDate.of(2003, 5, 24),
                        new BigDecimal("1606.85"),
                        "Eletricista"
                )
        );

        funcionarios.add(
                new Funcionario(
                        "Helena",
                        LocalDate.of(1996, 9, 2),
                        new BigDecimal("2799.93"),
                        "Gerente"
                )
        );

        return funcionarios;
    }

    /**
     * Cria o formatador monetário utilizado apenas para apresentação dos valores no console.
     * A formatação não altera o valor armazenado no BigDecimal.
     */
    private static DecimalFormat criarFormatadorMoeda() {

        DecimalFormatSymbols simbolos =
                new DecimalFormatSymbols(
                        new Locale("pt", "BR")
                );

        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');

        return new DecimalFormat(
                "R$ #,##0.00",
                simbolos
        );
    }

    /**
     * Exibe os funcionários no console.
     * Este método é exclusivamente responsável pela apresentação.
     * Ele não modifica os dados.
     */
    private static void imprimirFuncionarios(
            List<Funcionario> funcionarios,
            DateTimeFormatter formatadorData,
            DecimalFormat formatadorMoeda) {

        funcionarios.forEach(funcionario -> {

            System.out.println(
                    "Nome: "
                            + funcionario.getNome()
            );

            System.out.println(
                    "Nascimento: "
                            + funcionario
                            .getDataNascimento()
                            .format(formatadorData)
            );

            System.out.println(
                    "Salário: "
                            + formatadorMoeda.format(
                            funcionario.getSalario()
                    )
            );

            System.out.println(
                    "Função: "
                            + funcionario.getFuncao()
            );

            System.out.println("--------------------------------------");
        });
    }
}