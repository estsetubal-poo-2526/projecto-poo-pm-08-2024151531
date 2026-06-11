package model;

import exception.InvalidMoveException;

public class GameEngine {

    private final Board board;
    private int attempts;
    private int pairsFound;
    private Card firstSelected;
    private Card secondSelected;
    private SpecialCard selectedSpecialCard;

    public GameEngine(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Tabuleiro não pode ser nulo.");
        }

        this.board = board;
        this.attempts = board.getInitialAttempts();
        this.pairsFound = 0;
        this.selectedSpecialCard = null;
    }

    public void play(int row, int col) throws InvalidMoveException {
        if (isGameOver()) {
            throw new InvalidMoveException("O jogo já termimou.");
        }

        Card selectedCard = board.getCard(row, col);

        if (selectedCard.isFixed()) {
            throw new InvalidMoveException("Esta carta já foi selecionada.");
        }

        if (selectedCard.isRevealed()) {
            throw new InvalidMoveException("Esta carta já foi revelada.");
        }

        if (selectedCard instanceof SpecialCard && firstSelected != null) {
            firstSelected.hide();
            firstSelected = null;
        }

        selectedCard.reveal(this);

        if (selectedCard instanceof SpecialCard) {
            selectedSpecialCard = (SpecialCard) selectedCard;
        } else if (firstSelected == null) {
            firstSelected = selectedCard;
        } else {
            secondSelected = selectedCard;
            attempts--;
        }
    }

    public boolean hasTwoSelectedCards() {
        return firstSelected != null && secondSelected != null;
    }

    public boolean hasSelectedSpecialCard() {
        return selectedSpecialCard != null;
    }

    public SpecialCard.EffectType getSelectedSpecialEffectType() {
        if (selectedSpecialCard == null) {
            return null;
        }

        return selectedSpecialCard.getEffectType();
    }

    public void finishSpecialCard() {
        if (selectedSpecialCard == null) {
            return;
        }

        selectedSpecialCard.applyEffect(this);
        selectedSpecialCard.fix();
        selectedSpecialCard = null;
    }

    public void finishTurn() {
        if (!hasTwoSelectedCards()) {
            return;
        }

        if (firstSelected.getSymbol().equals(secondSelected.getSymbol()) && firstSelected instanceof NormalCard && secondSelected instanceof NormalCard) {

            firstSelected.fix();
            secondSelected.fix();
            pairsFound++;
        } else {
            firstSelected.hide();
            secondSelected.hide();
        }

        firstSelected = null;
        secondSelected = null;
    }

    public void addAttempts(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Tentativas tem que ser um valor > 0.");
        }
        attempts += value;
    }

    public void shuffleUnfixedCards() {
        board.shuffleUnfixedCards();
    }

    public boolean isWinner() {
        return pairsFound == board.getTotalNormalPairs();
    }

    public boolean isGameOver() {
        return attempts <= 0 || isWinner();
    }

    public Board getBoard() {
        return board;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getPairsFound() {
        return pairsFound;
    }

    public int getTotalPairs() {
        return board.getTotalNormalPairs();
    }
}
