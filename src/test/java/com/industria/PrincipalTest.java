package com.industria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Testes unitários das regras principais da aplicação.
 *
 * Os testes verificam:
 *
 * - carregamento dos funcionários;
 * - remoção;
 * - cálculo de salários;
 * - aumento salarial;
 * - validações do aumento salarial;
 * - agrupamento por função;
 * - aniversariantes;
 * - funcionário mais velho;
 * - ordenação;
 * - validações de domínio;
 * - comportamento em situações extremas.
 *
 * A utilização de @BeforeEach garante que cada teste
 * comece com uma lista independente contendo os
 * funcionários originais do exercício.
 */
class PrincipalTest {

    private List<Funcionario> funcionarios;

    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {

        funcionarios =
                Principal.carregarFuncionariosIniciais();

        funcionarioService =
                new FuncionarioService();
    }

    @Test
    @DisplayName("Deve carregar 10 funcionários inicialmente")
    void deveCarregar10FuncionariosInicialmente() {

        assertNotNull(funcionarios);

        assertEquals(
                10,
                funcionarios.size()
        );
    }

    @Test
    @DisplayName("Deve remover João da lista")
    void deveRemoverJoao() {

        funcionarioService.removerFuncionarioPorNome(
                funcionarios,
                "João"
        );

        assertEquals(
                9,
                funcionarios.size()
        );

        assertTrue(
                funcionarios.stream()
                        .noneMatch(
                                funcionario ->
                                        funcionario
                                                .getNome()
                                                .equals("João")
                        )
        );
    }

    @Test
    @DisplayName("Não deve alterar a lista ao remover funcionário inexistente")
    void naoDeveAlterarListaAoRemoverFuncionarioInexistente() {

        funcionarioService.removerFuncionarioPorNome(
                funcionarios,
                "Roberto"
        );

        assertEquals(
                10,
                funcionarios.size()
        );
    }

    @Test
    @DisplayName("Deve aplicar aumento de 10%")
    void deveAplicarAumentoDe10PorCento() {

        Funcionario maria =
                funcionarios.stream()
                        .filter(
                                funcionario ->
                                        funcionario
                                                .getNome()
                                                .equals("Maria")
                        )
                        .findFirst()
                        .orElseThrow();

        BigDecimal salarioOriginal =
                maria.getSalario();

        funcionarioService.aplicarAumento(
                funcionarios,
                new BigDecimal("0.10")
        );

        BigDecimal salarioEsperado =
                salarioOriginal
                        .multiply(new BigDecimal("1.10"))
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        assertEquals(
                salarioEsperado,
                maria.getSalario()
        );
    }

    @Test
    @DisplayName("Deve arredondar o salário para duas casas decimais")
    void deveArredondarSalarioParaDuasCasasDecimais() {

        Funcionario maria =
                funcionarios.stream()
                        .filter(
                                funcionario ->
                                        funcionario
                                                .getNome()
                                                .equals("Maria")
                        )
                        .findFirst()
                        .orElseThrow();

        funcionarioService.aplicarAumento(
                funcionarios,
                new BigDecimal("0.10")
        );

        assertEquals(
                new BigDecimal("2210.38"),
                maria.getSalario()
        );
    }

    @Test
    @DisplayName("Não deve permitir percentual de aumento negativo")
    void naoDevePermitirPercentualDeAumentoNegativo() {

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        funcionarioService.aplicarAumento(
                                funcionarios,
                                new BigDecimal("-0.10")
                        )
        );
    }

    @Test
    @DisplayName("Não deve permitir percentual de aumento nulo")
    void naoDevePermitirPercentualDeAumentoNulo() {

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        funcionarioService.aplicarAumento(
                                funcionarios,
                                null
                        )
        );
    }

    @Test
    @DisplayName("Deve aceitar aumento de 0% sem alterar os salários")
    void deveAceitarAumentoDeZeroPorCento() {

        BigDecimal salarioOriginal =
                funcionarios.get(0).getSalario();

        funcionarioService.aplicarAumento(
                funcionarios,
                BigDecimal.ZERO
        );

        assertEquals(
                salarioOriginal,
                funcionarios.get(0).getSalario()
        );
    }

    @Test
    @DisplayName("Deve agrupar funcionários por função")
    void deveAgruparFuncionariosPorFuncao() {

        Map<String, List<Funcionario>> grupos =
                funcionarioService.agruparPorFuncao(
                        funcionarios
                );

        assertNotNull(grupos);

        assertEquals(
                3,
                grupos.get("Operador").size()
        );

        assertEquals(
                2,
                grupos.get("Gerente").size()
        );
    }

    @Test
    @DisplayName("Deve encontrar aniversariantes de outubro e dezembro")
    void deveEncontrarAniversariantesDeOutubroEDezembro() {

        List<Funcionario> aniversariantes =
                funcionarioService.buscarAniversariantes(
                        funcionarios,
                        List.of(10, 12)
                );

        assertEquals(
                2,
                aniversariantes.size()
        );

        assertTrue(
                aniversariantes.stream()
                        .anyMatch(
                                funcionario ->
                                        funcionario
                                                .getNome()
                                                .equals("Maria")
                        )
        );

        assertTrue(
                aniversariantes.stream()
                        .anyMatch(
                                funcionario ->
                                        funcionario
                                                .getNome()
                                                .equals("Miguel")
                        )
        );
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver aniversariantes")
    void deveRetornarListaVaziaQuandoNaoHouverAniversariantes() {

        List<Funcionario> aniversariantes =
                funcionarioService.buscarAniversariantes(
                        funcionarios,
                        List.of(2)
                );

        assertTrue(
                aniversariantes.isEmpty()
        );
    }

    @Test
    @DisplayName("Deve encontrar o funcionário mais velho")
    void deveEncontrarFuncionarioMaisVelho() {

        Funcionario maisVelho =
                funcionarioService.buscarMaisVelho(
                        funcionarios
                );

        assertEquals(
                "Caio",
                maisVelho.getNome()
        );
    }

    @Test
    @DisplayName("Deve rejeitar busca do mais velho em lista vazia")
    void deveRejeitarBuscaDoMaisVelhoEmListaVazia() {

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        funcionarioService.buscarMaisVelho(
                                List.of()
                        )
        );
    }

    @Test
    @DisplayName("Deve ordenar funcionários alfabeticamente")
    void deveOrdenarFuncionariosAlfabeticamente() {

        List<Funcionario> ordenados =
                funcionarioService.ordenarPorNome(
                        funcionarios
                );

        List<String> nomesEsperados =
                Arrays.asList(
                        "Alice",
                        "Arthur",
                        "Caio",
                        "Heitor",
                        "Helena",
                        "Heloísa",
                        "João",
                        "Laura",
                        "Maria",
                        "Miguel"
                );

        List<String> nomesObtidos =
                ordenados.stream()
                        .map(Funcionario::getNome)
                        .toList();

        assertEquals(
                nomesEsperados,
                nomesObtidos
        );
    }

    @Test
    @DisplayName("Não deve alterar a lista original ao ordenar")
    void naoDeveAlterarListaOriginalAoOrdenar() {

        String primeiroFuncionarioAntes =
                funcionarios.get(0).getNome();

        List<Funcionario> ordenados =
                funcionarioService.ordenarPorNome(
                        funcionarios
                );

        assertEquals(
                primeiroFuncionarioAntes,
                funcionarios.get(0).getNome()
        );

        assertEquals(
                "Maria",
                funcionarios.get(0).getNome()
        );

        assertEquals(
                "Alice",
                ordenados.get(0).getNome()
        );
    }

    @Test
    @DisplayName("Deve calcular corretamente o total dos salários")
    void deveCalcularTotalDosSalarios() {

        BigDecimal total =
                funcionarioService.calcularTotalSalarios(
                        funcionarios
                );
        /*
         * O valor abaixo corresponde à soma
         * dos dez salários fornecidos no enunciado.
         * 2.009,44
         * 2.284,38
         * 9.836,14
         * 19.119,88
         * 2.234,68
         * 1.582,72
         * 4.071,84
         * 3.017,45
         * 1.606,85
         * 2.799,93
         * Total = 48.563,31
         */

        BigDecimal totalEsperado =
                new BigDecimal("48563.31");

        assertEquals(
                totalEsperado,
                total
        );
    }

    @Test
    @DisplayName("Não deve permitir salário negativo")
    void naoDevePermitirSalarioNegativo() {

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        new Funcionario(
                                "Teste",
                                LocalDate.of(2000, 1, 1),
                                new BigDecimal("-100.00"),
                                "Operador"
                        )
        );
    }

    @Test
    @DisplayName("Não deve permitir salário nulo")
    void naoDevePermitirSalarioNulo() {

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        new Funcionario(
                                "Teste",
                                LocalDate.of(2000, 1, 1),
                                null,
                                "Operador"
                        )
        );
    }

    @Test
    @DisplayName("Não deve permitir função vazia")
    void naoDevePermitirFuncaoVazia() {

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        new Funcionario(
                                "Teste",
                                LocalDate.of(2000, 1, 1),
                                new BigDecimal("2000.00"),
                                ""
                        )
        );
    }

    @Test
    @DisplayName("Não deve permitir função contendo apenas espaços")
    void naoDevePermitirFuncaoComApenasEspacos() {

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        new Funcionario(
                                "Teste",
                                LocalDate.of(2000, 1, 1),
                                new BigDecimal("2000.00"),
                                "   "
                        )
        );
    }
}