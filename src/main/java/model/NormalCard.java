package model;

/**
 * Representa uma carta normal do jogo.
 * Pode formar par com outra carta normal com o mesmo símbolo.
 */
public class NormalCard extends Card {

    /**
     * Cria uma carta normal.
     *
     * @param symbol símbolo da carta
     */
    public NormalCard(String symbol) {
        super(symbol);
    }

    /**
     * Revela a carta normal.
     *
     * @param gameEngine motor do jogo
     */
    @Override
    public void reveal(GameEngine gameEngine) {
        setRevealed(true);
    }
}
