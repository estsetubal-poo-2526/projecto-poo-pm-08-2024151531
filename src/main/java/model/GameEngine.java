package model;

import exception.InvalidMoveException;

/**
 * Representa o motor principal do jogo da memória.
 * Gere jogadas, tentativas, pares encontrados e estado final do jogo.
 */
public class GameEngine {

    private final Board board;
    private int attempts;
    private int pairsFound;
    private Card firstSelected;
    private Card secondSelected;
    private SpecialCard selectedSpecialCard;

    /**
     * Cria o motor do jogo com um tabuleiro.
     *
     * @param board tabuleiro do jogo
     */
    public GameEngine(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Tabuleiro não pode ser nulo.");
        }

        this.board = board;
        this.attempts = board.getInitialAttempts();
        this.pairsFound = 0;
        this.selectedSpecialCard = null;
    }

    /**
     * Processa a escolha de uma carta no tabuleiro.
     *
     * @param row linha da carta escolhida
     * @param col coluna da carta escolhida
     * @throws InvalidMoveException se a jogada não for válida
     */
    public void play(int row, int col) throws InvalidMoveException {
        if (isGameOver()) {
            throw new InvalidMoveException("O jogo já terminou.");
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

    /**
     * Indica se existem duas cartas normais escolhidas.
     *
     * @return true se duas cartas estiverem selecionadas
     */
    public boolean hasTwoSelectedCards() {
        return firstSelected != null && secondSelected != null;
    }

    /**
     * Indica se existe uma carta especial escolhida.
     *
     * @return true se uma carta especial estiver selecionada
     */
    public boolean hasSelectedSpecialCard() {
        return selectedSpecialCard != null;
    }

    /**
     * Devolve o tipo de efeito da carta especial escolhida.
     *
     * @return tipo de efeito da carta especial
     */
    public SpecialCard.EffectType getSelectedSpecialEffectType() {
        if (selectedSpecialCard == null) {
            return null;
        }

        return selectedSpecialCard.getEffectType();
    }

    /**
     * Aplica o efeito da carta especial selecionada.
     */
    public void finishSpecialCard() {
        if (selectedSpecialCard == null) {
            return;
        }

        selectedSpecialCard.applyEffect(this);
        selectedSpecialCard.fix();
        selectedSpecialCard = null;
    }

    /**
     * Termina uma jogada com duas cartas normais.
     */
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

    /**
     * Adiciona tentativas ao jogador.
     *
     * @param value número de tentativas a adicionar
     */
    public void addAttempts(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Tentativas tem que ser um valor > 0.");
        }
        attempts += value;
    }

    /**
     * Baralha as cartas ainda não fixadas.
     */
    public void shuffleUnfixedCards() {
        board.shuffleUnfixedCards();
    }

    /**
     * Indica se o jogador ganhou.
     *
     * @return true se todos os pares foram encontrados
     */
    public boolean isWinner() {
        return pairsFound == board.getTotalNormalPairs();
    }

    /**
     * Indica se o jogo terminou.
     *
     * @return true se o jogo terminou
     */
    public boolean isGameOver() {
        return attempts <= 0 || isWinner();
    }

    /**
     * Devolve o tabuleiro do jogo.
     *
     * @return tabuleiro do jogo
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Devolve o número de tentativas restantes.
     *
     * @return tentativas restantes
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Devolve o número de pares encontrados.
     *
     * @return pares encontrados
     */
    public int getPairsFound() {
        return pairsFound;
    }

    /**
     * Devolve o número total de pares.
     *
     * @return total de pares
     */
    public int getTotalPairs() {
        return board.getTotalNormalPairs();
    }
}
