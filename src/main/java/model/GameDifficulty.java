package model;

public enum GameDifficulty {
    EASY("Facil 4x4", 4, 4, 2, 12),
    MEDIUM("Medio 6x6", 6, 6, 4, 28),
    HARD("Dificil 8x8", 8, 8, 6, 44);

    private final String description;
    private final int rows;
    private final int cols;
    private final int specialCards;
    private final int initialAttempts;

    GameDifficulty(String description, int rows, int cols, int specialCards, int initialAttempts) {
        this.description = description;
        this.rows = rows;
        this.cols = cols;
        this.specialCards = specialCards;
        this.initialAttempts = initialAttempts;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getSpecialCards() {
        return specialCards;
    }

    public int getInitialAttempts() {
        return initialAttempts;
    }

    @Override
    public String toString() {
        return description;
    }
}
