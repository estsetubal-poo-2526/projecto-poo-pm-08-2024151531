package model;

import exception.InvalidBoardException;
import exception.InvalidMoveException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Representa o tabuleiro do jogo da memória.
 * Guarda as cartas e controla a sua distribuição.
 */
public class Board {

    private static final String[] SYMBOLS = {
            "apple", "banana", "cherries", "grapes", "strawberry", "pineapple", "kiwi_fruit", "watermelon",
            "star", "crescent_moon", "sun", "soccer_ball", "game_die", "musical_note", "car", "rocket",
            "dog_face", "cat_face", "panda", "lion", "frog", "monkey_face", "penguin", "fox",
            "sunflower", "tulip", "cactus", "four_leaf_clover", "fire", "droplet", "snowflake", "high_voltage",
            "pizza", "hamburger", "french_fries", "doughnut", "cookie", "chocolate_bar", "popcorn", "pretzel",
            "video_game", "guitar", "bullseye", "basketball", "trophy", "gem_stone", "key", "wrapped_gift"
    };

    private final int rows;
    private final int cols;
    private final int specialCards;
    private final int initialAttempts;
    private final Card[][] cards;

    /**
     * Cria um tabuleiro com duas cartas especiais e 15 tentativas iniciais.
     *
     * @param rows número de linhas
     * @param cols número de colunas
     * @throws InvalidBoardException se a configuração do tabuleiro for inválida
     */
    public Board(int rows, int cols) throws InvalidBoardException {
        this(rows, cols, 2, 15);
    }

    /**
     * Cria um tabuleiro de acordo com a dificuldade escolhida.
     *
     * @param difficulty dificuldade do jogo
     * @throws InvalidBoardException se a configuração do tabuleiro for inválida
     */
    public Board(GameDifficulty difficulty) throws InvalidBoardException {
        this(
                difficulty.getRows(),
                difficulty.getCols(),
                difficulty.getSpecialCards(),
                difficulty.getInitialAttempts()
        );
    }

    /**
     * Cria um tabuleiro com configuração completa.
     *
     * @param rows número de linhas
     * @param cols número de colunas
     * @param specialCards número de cartas especiais
     * @param initialAttempts número inicial de tentativas
     * @throws InvalidBoardException se a configuração do tabuleiro for inválida
     */
    private Board(int rows, int cols, int specialCards, int initialAttempts) throws InvalidBoardException {
        if (rows <= 0 || cols <= 0) {
            throw new InvalidBoardException("Dimensão do tabuleiro tem que ser positiva.");
        }
        if ((rows * cols) % 2 != 0) {
            throw new InvalidBoardException("Tabuleiro tem que ter um número par de posições.");
        }
        if (specialCards <= 0 || specialCards % 2 != 0) {
            throw new InvalidBoardException("Número de cartas especiais inválido.");
        }
        if (specialCards >= rows * cols) {
            throw new InvalidBoardException("O tabuleiro precisa de cartas normais.");
        }
        if (getTotalNormalPairs(rows, cols, specialCards) > SYMBOLS.length) {
            throw new InvalidBoardException("Não existem imagens suficientes para este tabuleiro.");
        }

        this.rows = rows;
        this.cols = cols;
        this.specialCards = specialCards;
        this.initialAttempts = initialAttempts;
        this.cards = new Card[rows][cols];

        createCards();
    }

    /**
     * Cria e distribui as cartas no tabuleiro.
     */
    private void createCards() {
        List<Card> cardList = new ArrayList<>();

        int numberOfPairs = getTotalNormalPairs();

        for (int i = 0; i < numberOfPairs; i++) {
            String symbol = SYMBOLS[i];

            cardList.add(new NormalCard(symbol));
            cardList.add(new NormalCard(symbol));
        }

        for (int i = 0; i < specialCards; i++) {
            if (i % 2 == 0) {
                cardList.add(new SpecialCard("!", SpecialCard.EffectType.BONUS));
            } else {
                cardList.add(new SpecialCard("?", SpecialCard.EffectType.SHUFFLE));
            }
        }

        Collections.shuffle(cardList, new Random());

        int index = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cards[row][col] = cardList.get(index);
                index++;
            }
        }
    }

    /**
     * Devolve a carta que está numa posição do tabuleiro.
     *
     * @param row linha da carta
     * @param col coluna da carta
     * @return carta na posição indicada
     * @throws InvalidMoveException se a posição for inválida
     */
    public Card getCard(int row, int col) throws InvalidMoveException {
        validatePosition(row, col);
        return cards[row][col];
    }

    /**
     * Baralha apenas as cartas que ainda não foram fixadas.
     */
    public void shuffleUnfixedCards() {
        List<Card> unfixedCards = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!cards[row][col].isFixed()) {
                    unfixedCards.add(cards[row][col]);
                }
            }
        }

        Collections.shuffle(unfixedCards, new Random());

        int index = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!cards[row][col].isFixed()) {
                    cards[row][col] = unfixedCards.get(index);
                    index++;
                }
            }
        }
    }

    /**
     * Verifica se uma posição pertence ao tabuleiro.
     *
     * @param row linha a verificar
     * @param col coluna a verificar
     * @return true se a posição for válida
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * Valida uma posição do tabuleiro.
     *
     * @param row linha a validar
     * @param col coluna a validar
     * @throws InvalidMoveException se a posição for inválida
     */
    private void validatePosition(int row, int col) throws InvalidMoveException {
        if (!isValidPosition(row, col)) {
            throw new InvalidMoveException("Posição inválida.");
        }
    }

    /**
     * Devolve o número de linhas do tabuleiro.
     *
     * @return número de linhas
     */
    public int getRows() {
        return rows;
    }

    /**
     * Devolve o número de colunas do tabuleiro.
     *
     * @return número de colunas
     */
    public int getCols() {
        return cols;
    }

    /**
     * Devolve o número total de pares normais.
     *
     * @return número de pares normais
     */
    public int getTotalNormalPairs() {
        return getTotalNormalPairs(rows, cols, specialCards);
    }

    /**
     * Devolve o número inicial de tentativas.
     *
     * @return tentativas iniciais
     */
    public int getInitialAttempts() {
        return initialAttempts;
    }

    /**
     * Calcula o número de pares normais para uma configuração.
     *
     * @param rows número de linhas
     * @param cols número de colunas
     * @param specialCards número de cartas especiais
     * @return número de pares normais
     */
    private static int getTotalNormalPairs(int rows, int cols, int specialCards) {
        return ((rows * cols) - specialCards) / 2;
    }
}
