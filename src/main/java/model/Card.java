package model;

/**
 * Representa uma carta genérica do jogo da memória.
 * Guarda o símbolo e o estado da carta.
 */
public abstract class Card {

    private final String symbol;
    private boolean revealed;
    private boolean fixed;

    /**
     * Cria uma carta com um símbolo.
     *
     * @param symbol símbolo da carta
     */
    public Card(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Símbolo não pode ser nulo.");
        }

        this.symbol = symbol;
        this.revealed = false;
        this.fixed = false;
    }

    /**
     * Revela a carta.
     *
     * @param gameEngine motor do jogo
     */
    public abstract void reveal(GameEngine gameEngine);

    /**
     * Esconde a carta se ainda não estiver fixa.
     */
    public void hide() {
        if (!fixed) {
            revealed = false;
        }
    }

    /**
     * Fixa a carta no tabuleiro.
     */
    public void fix() {
        fixed = true;
        revealed = true;
    }

    /**
     * Devolve o símbolo da carta.
     *
     * @return símbolo da carta
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Indica se a carta está revelada.
     *
     * @return true se a carta estiver revelada
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * Indica se a carta está fixa.
     *
     * @return true se a carta estiver fixa
     */
    public boolean isFixed() {
        return fixed;
    }

    /**
     * Altera o estado de revelação da carta.
     *
     * @param revealed novo estado da carta
     */
    protected void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
