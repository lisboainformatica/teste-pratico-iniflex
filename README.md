# Teste Prático - Iniflex

## Desafio Prático de Programação Java

Projeto desenvolvido para resolução de um desafio prático de programação utilizando Java, com aplicação de conceitos de orientação a objetos, coleções, processamento de dados e testes automatizados.

## Agradecimento

Agradeço à Prothera pela oportunidade de participar do processo seletivo e de apresentar este projeto.

---

## Tecnologias utilizadas

* **Java 21**
* **Maven**
* **JUnit 5**
* `BigDecimal`
* `LocalDate`
* Collections
* Stream API
* Lambda Expressions

---

## Objetivo

O projeto representa uma aplicação simples para gerenciamento e processamento de funcionários de uma indústria.

A aplicação contempla:

* cadastro inicial de funcionários;
* remoção de funcionário;
* aplicação de aumento salarial;
* agrupamento de funcionários por função;
* identificação de aniversariantes;
* identificação do funcionário mais velho;
* ordenação alfabética;
* cálculo do total dos salários;
* representação dos salários em quantidade de salários mínimos.

---

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── industria/
│   │           ├── Pessoa.java
│   │           ├── Funcionario.java
│   │           ├── FuncionarioService.java
│   │           └── Principal.java
│   │
│   └── resources/
│
└── test/
    └── java/
        └── com/
            └── industria/
                └── PrincipalTest.java
```

---

## Organização das classes

### `Pessoa`

Classe base que representa os dados comuns de uma pessoa.

Possui:

* `nome`
* `dataNascimento`

A classe é utilizada como base para `Funcionario`, aplicando o conceito de **herança**.

---

### `Funcionario`

Especialização de `Pessoa`.

Além dos atributos herdados, possui:

* `salario`
* `funcao`

Também contém validações relacionadas ao domínio do funcionário, como:

* salário não pode ser nulo;
* salário não pode ser negativo;
* função não pode ser vazia;
* função não pode conter apenas espaços.

Para valores monetários foi utilizado `BigDecimal`, evitando problemas de precisão associados ao uso de tipos de ponto flutuante.

---

### `FuncionarioService`

Classe responsável pelas operações relacionadas aos funcionários.

Entre as operações implementadas estão:

* remoção por nome;
* aplicação de aumento salarial;
* agrupamento por função;
* busca de aniversariantes;
* identificação do funcionário mais velho;
* cálculo de idade;
* ordenação por nome;
* cálculo do total dos salários.

A separação dessas operações permite manter o `Principal` focado no fluxo da aplicação e na apresentação dos resultados.

---

### `Principal`

Classe responsável pela execução da aplicação.

Suas principais responsabilidades são:

* iniciar o programa;
* carregar os funcionários utilizados no exercício;
* chamar as operações do `FuncionarioService`;
* apresentar os resultados no console;
* formatar datas e valores monetários para exibição.

---

## Funcionários utilizados

A aplicação inicia com os seguintes funcionários:

| Nome    | Data de nascimento |      Salário | Função        |
| ------- | ------------------ | -----------: | ------------- |
| Maria   | 18/10/2000         |  R$ 2.009,44 | Operador      |
| João    | 12/05/1990         |  R$ 2.284,38 | Operador      |
| Caio    | 02/05/1961         |  R$ 9.836,14 | Coordenador   |
| Miguel  | 14/10/1988         | R$ 19.119,88 | Diretor       |
| Alice   | 05/01/1995         |  R$ 2.234,68 | Recepcionista |
| Heitor  | 19/11/1999         |  R$ 1.582,72 | Operador      |
| Arthur  | 31/03/1993         |  R$ 4.071,84 | Contador      |
| Laura   | 08/07/1994         |  R$ 3.017,45 | Gerente       |
| Heloísa | 24/05/2003         |  R$ 1.606,85 | Eletricista   |
| Helena  | 02/09/1996         |  R$ 2.799,93 | Gerente       |

---

## Regras implementadas

### Remoção

O funcionário **João** é removido da lista antes das demais operações.

### Aumento salarial

É aplicado um aumento de **10%** aos funcionários restantes.

O cálculo utiliza `BigDecimal` e arredondamento para duas casas decimais.

### Agrupamento

Os funcionários são agrupados de acordo com sua função.

### Aniversariantes

São identificados os funcionários que fazem aniversário nos meses de **outubro e dezembro**.

### Funcionário mais velho

A aplicação identifica o funcionário cuja data de nascimento é a mais antiga e calcula sua idade.

### Ordenação

Os funcionários são apresentados em ordem alfabética pelo nome.

### Total dos salários

A aplicação calcula a soma dos salários dos funcionários após a aplicação do aumento.

### Salários mínimos

Os salários são apresentados também como quantidade de salários mínimos, conforme o valor definido no exercício.

---

## Testes automatizados

O projeto possui testes automatizados utilizando JUnit 5
para validar as principais regras e comportamentos da aplicação.

Resultado:

**20 testes executados,
20 testes aprovados,
0 falhas,
0 erros**


```text
src/test/java/com/industria/PrincipalTest.java
```

Os testes verificam as principais regras e comportamentos da aplicação, incluindo:

* carregamento dos funcionários;
* remoção de funcionário;
* comportamento quando o funcionário não existe;
* aumento salarial;
* arredondamento dos salários;
* validação de percentual de aumento;
* aumento de 0%;
* agrupamento por função;
* busca de aniversariantes;
* ausência de aniversariantes;
* identificação do funcionário mais velho;
* comportamento com lista vazia;
* ordenação alfabética;
* preservação da lista original durante a ordenação;
* cálculo do total dos salários;
* validações de salário;
* validações de função.

### Resultado dos testes

```text
Tests run: 20
Failures: 0
Errors: 0
Skipped: 0
```

**Todos os testes estão passando.**

---

## Como executar

### Pré-requisitos

* Java JDK 21 ou superior
* Maven

Verifique as instalações:

```bash
java -version
mvn -version
```

### Compilar o projeto

```bash
mvn clean compile
```

### Executar os testes

```bash
mvn test
```

### Executar a aplicação

Após a compilação, execute a classe:

```text
com.industria.Principal
```

No IntelliJ IDEA, também é possível executar diretamente pelo método `main()` da classe `Principal`.

---

## Conceitos de programação utilizados

O projeto aplica conceitos fundamentais de Java, incluindo:

* Programação Orientada a Objetos;
* encapsulamento;
* herança;
* construtores;
* getters e setters;
* validação de dados;
* `List`;
* `Map`;
* `Stream API`;
* Lambda Expressions;
* `Comparator`;
* `BigDecimal`;
* `LocalDate`;
* `Period`;
* tratamento de exceções;
* testes automatizados com JUnit 5.

---

## Resultado

O projeto atende às operações propostas no desafio e possui testes automatizados para validar suas principais regras de funcionamento.

```text
Java 21
   │
   ├── Pessoa
   │
   ├── Funcionario
   │
   ├── FuncionarioService
   │
   └── Principal
          │
          └── PrincipalTest
                └── 20 testes
```

---
