# 📝 Fase 1 – Jogo da Memória

### 👥 Grupo `PM.08.2024151531`
*   👨‍💻 **Rafael Junqueira (Leader)** — 2024151531
*   👨‍💻 **Diogo Brito** — 2024151350

**Docente de Laboratório:** Patrícia Macedo <br>
**Aula PL:** PL8

---

### 🕹️ 1. Descrição do Jogo
O objetivo do jogo é encontrar todos os pares de símbolos escondidos numa grelha. Ao contrário da versão tradicional, o jogo inclui cartas especiais com comportamentos dinâmicos que alteram o fluxo da partida.

*   **🔤 Símbolos:** As cartas utilizam identificadores simples (ex: "A", "B", "C").
    *   **Consola:** Os símbolos são apresentados como texto simples.
    *   **JavaFX:** A representação visual é feita através de **Emojis** (ex: 🍎, 🍌, 🍒).
*   **🃏 Cartas Especiais:** 
    *   `[!]` **Bónus:** Ao ser revelada, esta carta adiciona 3 tentativas ao contador do jogador.
    *   `[?]` **Baralhar:** Ao ser revelada, baralha todas as cartas que ainda não foram fixadas no tabuleiro.
*   **🏆 Condições de Vitória/Derrota:**
    *   **Vitória:** Encontrar todos os pares existentes no tabuleiro.
    *   **Derrota:** O contador de tentativas chegar a zero antes de todos os pares serem encontrados (limite inicial de 15 jogadas).

---

### 🧠 2. Modelação do Domínio

A lógica do sistema foi estruturada para garantir a separação entre as regras de negócio e a interface com o utilizador, permitindo que o jogo corra tanto em modo Consola como em JavaFX.

#### 2.1. Identificação de Entidades e Responsabilidades

| | Entidade | Responsabilidade Principal |
| :--- | :--- | :--- |
| 🎮 | **MotorJogo** | Atua como o controlador central. Gere as regras, valida se as cartas escolhidas formam par, controla as tentativas e verifica o fim do jogo. |
| 🗺️ | **Tabuleiro** | Responsável por organizar a grelha, criar a coleção de cartas, baralhar as posições e fornecer acesso às cartas em coordenadas específicas. |
| 🃏 | **Carta (Abstrata)** | Define o conceito base de uma peça: guarda o símbolo e controla se a carta está escondida, revelada ou já fixa no tabuleiro. |
| 🃏 | **CartaNormal** | Representa uma peça comum do jogo, focada apenas na lógica clássica de formar pares. |
| ✨ | **CartaEspecial** | Especialização que contém o método `aplicarEfeito()` para modificar variáveis globais do jogo ao ser ativada. |

#### 2.2. Relações Estruturais (POO)

*   **🔼 Herança:** As classes `CartaNormal` e `CartaEspecial` herdam de `Carta`. Esta hierarquia permite tratar todas as peças de forma genérica no tabuleiro.
*   **💎 Composição:** O `Tabuleiro` mantém uma relação de composição com a `Carta`. O tabuleiro é o "todo" responsável por criar e gerir o ciclo de vida das cartas.
*   **🔌 Associação:** O `MotorJogo` mantém uma referência ao `Tabuleiro`. O motor utiliza esta ligação para consultar ou alterar o estado das peças durante as jogadas.

---

### 📊 3. Modelo de Classes (UML)

O diagrama seguinte detalha a arquitetura do sistema e a organização das classes:

![Diagrama UML](uml_parcial.png)

*   **Hierarquia:** Organização baseada numa classe abstrata e subclasses concretas para especializar comportamentos.
*   **Encapsulamento:** Os atributos estão definidos como privados (`-`) para proteção dos dados, sendo o acesso feito apenas por métodos públicos.
*   **Polimorfismo:** O método `revelar()` é redefinido nas subclasses para que o comportamento específico de cada carta seja executado automaticamente em tempo de execução.

---

### 🖥️ 4. Protótipo da Interface Gráfica
A interação na consola é feita através de coordenadas numéricas para selecionar as cartas.

#### 4.1. Interface de Consola
```text
Tentativas: 12 | Pares: 1/8
   0  1  2  3
0 [?] [*] [*] [!]
1 [*] [A] [A] [*]
Escolha a linha e coluna (ex: 0 1): 
```
*   `[*]` Carta virada para baixo.
*   `[A/B/C]` Carta normal revelada.
*   `[! / ?]` Carta especial revelada e efeito ativado.

#### 4.2. Interface JavaFX (Gráfica)
*   **Visual:** Utilização de um `GridPane` com botões interativos.
*   **Feedback:** Ao clicar, o botão revela o **Emoji** correspondente (🍎, 🍌, etc.).
*   **Estado:** Painel informativo no topo com Labels dinâmicas para tentativas e pares.
*   **Mensagens:** Uso de janelas de alerta para comunicar o desfecho da partida.

![JavaFX](javafx1.jpg)
![JavaFX](javafx2.jpg)
