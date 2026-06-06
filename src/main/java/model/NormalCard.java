package model;

public class NormalCard extends Card {

    public NormalCard(String symbol) {
        super(symbol);
    }

    @Override
    public void reveal(GameEngine gameEngine) {
        setRevealed(true);
    }
}