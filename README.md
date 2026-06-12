# 🧠 Jogo da Memória

Projeto desenvolvido no âmbito da unidade curricular de Programação Orientada a Objetos.

## 👥 Grupo

Grupo: `PM.08.2024151531`

- Rafael Junqueira - 2024151531
- Diogo Brito - 2024151350

Docente de Laboratório: Patrícia Macedo  
Aula PL: PL8

## 🕹️ Descrição

O projeto consiste num jogo da memória desenvolvido em Java com interface gráfica em JavaFX.

O objetivo do jogador é encontrar todos os pares de cartas antes de ficar sem tentativas. O jogo inclui cartas normais, representadas por imagens, e cartas especiais que alteram o estado da partida.

## 📜 Regras do Jogo

- O jogador escolhe uma dificuldade no ecrã inicial.
- O tabuleiro é criado automaticamente de acordo com a dificuldade.
- Em cada jogada, o jogador seleciona duas cartas normais.
- Se as duas cartas forem iguais, o par é encontrado e as cartas desaparecem do tabuleiro.
- Se as cartas forem diferentes, voltam a ficar viradas para baixo.
- O jogador perde uma tentativa por cada par de cartas normais selecionado.
- As cartas especiais são ativadas com apenas um clique.
- O jogo termina quando todos os pares forem encontrados ou quando as tentativas chegarem a zero.

## 🎚️ Dificuldades

| Dificuldade | Tabuleiro | Cartas especiais | Tentativas |
| :--- | :---: | :---: | :---: |
| Fácil | 4x4 | 2 | 25 |
| Médio | 6x6 | 4 | 60 |
| Difícil | 8x8 | 6 | 110 |

## 🃏 Cartas Especiais

| Carta | Efeito |
| :---: | :--- |
| `!` | Bónus: adiciona 3 tentativas ao jogador. |
| `?` | Baralhar: baralha as cartas que ainda não foram fixadas. |

## 🧩 Conceitos de POO Utilizados

- 🔹 Abstração: a classe `Card` representa o conceito geral de carta.
- 🔹 Herança: `NormalCard` e `SpecialCard` herdam de `Card`.
- 🔹 Polimorfismo: o método `reveal` é implementado de forma específica nas subclasses.
- 🔹 Encapsulamento: os atributos das classes são privados e acedidos através de métodos.
- 🔹 Composição: `Board` contém as cartas do jogo.
- 🔹 Associação: `GameEngine` usa um `Board` para aplicar as regras do jogo.
- 🔹 Exceções: `InvalidBoardException` e `InvalidMoveException` tratam erros do domínio.

## 📁 Estrutura do Projeto

```text
src/main/java
├── Main.java
├── controller
│   ├── GameController.java
│   └── StartController.java
├── exception
│   ├── InvalidBoardException.java
│   └── InvalidMoveException.java
├── model
│   ├── Board.java
│   ├── Card.java
│   ├── GameDifficulty.java
│   ├── GameEngine.java
│   ├── NormalCard.java
│   └── SpecialCard.java
└── ui
    ├── GameView.java
    └── StartView.java
```

As imagens usadas nas cartas estão em:

```text
src/main/resources/images
```

Os testes unitários estão em:

```text
src/test/java
```

## ✅ Requisitos

- Java 17
- Maven
- JavaFX

## ▶️ Como Executar

Na raiz do projeto, executar:

```bash
mvn javafx:run
```

Também é possível executar pelo IntelliJ IDEA, correndo a classe `Main`.

## 🧪 Como Correr os Testes

Na raiz do projeto, executar:

```bash
mvn test
```

Os testes incidem sobre o modelo do domínio, incluindo:

- criação e validação do tabuleiro;
- estado das cartas;
- cartas normais;
- cartas especiais;
- motor do jogo;
- jogadas inválidas.

## 🖥️ Interface Gráfica

A interface gráfica foi implementada com JavaFX.

Principais ecrãs:

- `StartView`: ecrã inicial, onde o jogador escolhe a dificuldade.
- `GameView`: ecrã principal, onde o jogador interage com o tabuleiro.

O controlo da aplicação é feito por:

- `StartController`: controla o ecrã inicial.
- `GameController`: controla o jogo e liga a interface ao modelo.

## 🏆 Condições de Vitória e Derrota

- 🟢 Vitória: todos os pares normais são encontrados.
- 🔴 Derrota: as tentativas chegam a zero antes de todos os pares serem encontrados.

## 📝 Observações

A lógica do jogo está separada da interface gráfica. Isto permite testar o modelo sem depender da JavaFX, cumprindo a separação entre modelo, interface e controlo da aplicação.
