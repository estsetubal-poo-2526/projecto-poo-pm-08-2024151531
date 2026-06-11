package model;

import exception.InvalidBoardException;
import exception.InvalidMoveException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    public Board(int rows, int cols) throws InvalidBoardException {
        this(rows, cols, 2, 15);
    }

    public Board(GameDifficulty difficulty) throws InvalidBoardException {
        this(
                difficulty.getRows(),
                difficulty.getCols(),
                difficulty.getSpecialCards(),
                difficulty.getInitialAttempts()
        );
    }

    private Board(int rows, int cols, int specialCards, int initialAttempts) throws InvalidBoardException {
        if (rows <= 0 || cols <= 0) {
            throw new InvalidBoardException("Dimensao do tabuleiro tem que ser positiva.");
        }
        if ((rows * cols) % 2 != 0) {
            throw new InvalidBoardException("Tabuleiro tem que ter um numero par de posicoes.");
        }
        if (specialCards <= 0 || specialCards % 2 != 0) {
            throw new InvalidBoardException("Numero de cartas especiais invalido.");
        }
        if (specialCards >= rows * cols) {
            throw new InvalidBoardException("O tabuleiro precisa de cartas normais.");
        }
        if (getTotalNormalPairs(rows, cols, specialCards) > SYMBOLS.length) {
            throw new InvalidBoardException("Nao existem imagens suficientes para este tabuleiro.");
        }

        this.rows = rows;
        this.cols = cols;
        this.specialCards = specialCards;
        this.initialAttempts = initialAttempts;
        this.cards = new Card[rows][cols];

        createCards();
    }

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

    public Card getCard(int row, int col) throws InvalidMoveException {
        validatePosition(row, col);
        return cards[row][col];
    }

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

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private void validatePosition(int row, int col) throws InvalidMoveException {
        if (!isValidPosition(row, col)) {
            throw new InvalidMoveException("Posicao invalida.");
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTotalNormalPairs() {
        return getTotalNormalPairs(rows, cols, specialCards);
    }

    public int getInitialAttempts() {
        return initialAttempts;
    }

    private static int getTotalNormalPairs(int rows, int cols, int specialCards) {
        return ((rows * cols) - specialCards) / 2;
    }
}
