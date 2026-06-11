package model;

import exception.InvalidBoardException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {

    private static final String[] SYMBOLS = {
            "🍎", "🍌", "🍒", "🍇", "🍓", "🍍", "🥝", "🍉",
            "⭐", "🌙", "☀", "⚽", "🎲", "🎵", "🚗", "🚀"
    };

    private final int rows;
    private final int cols;
    private final Card[][] cards;

    public Board(int rows, int cols) throws InvalidBoardException {
        if (rows <= 0 || cols <= 0) {
            throw new InvalidBoardException("Dimensao do tabuleiro tem que ser positiva.");
        }
        if ((rows * cols) % 2 != 0) {
            throw new InvalidBoardException("Tabuleiro tem que ter um numero par de posicoes.");
        }
        if (getTotalNormalPairs(rows, cols) > SYMBOLS.length) {
            throw new InvalidBoardException("Nao existem emojis suficientes para este tabuleiro.");
        }

        this.rows = rows;
        this.cols = cols;
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

        cardList.add(new SpecialCard("!", SpecialCard.EffectType.BONUS));
        cardList.add(new SpecialCard("?", SpecialCard.EffectType.SHUFFLE));

        Collections.shuffle(cardList, new Random());

        int index = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cards[row][col] = cardList.get(index);
                index++;
            }
        }
    }

    public Card getCard(int row, int col) {
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

    private void validatePosition(int row, int col) {
        if (!isValidPosition(row, col)) {
            throw new IndexOutOfBoundsException("Posicao invalida.");
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTotalNormalPairs() {
        return getTotalNormalPairs(rows, cols);
    }

    public int getInitialAttempts() {
        return 15;
    }

    private static int getTotalNormalPairs(int rows, int cols) {
        return ((rows * cols) - 2) / 2;
    }
}
