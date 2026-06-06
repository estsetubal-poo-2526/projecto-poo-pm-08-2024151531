package model;

public abstract class Card {

    private final String symbol;
    private boolean revealed;
    private boolean fixed;

    public Card(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Símbolo não pode ser nulo.");
        }

        this.symbol = symbol;
        this.revealed = false;
        this.fixed = false;
    }

    public abstract void reveal(GameEngine gameEngine);

    public void hide() {
        if (!fixed) {
            revealed = false;
        }
    }

    public void fix() {
        fixed = true;
        revealed = true;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isFixed() {
        return fixed;
    }

    protected void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}