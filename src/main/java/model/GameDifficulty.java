package model;

/**
 * Representa as dificuldades disponíveis no jogo.
 * Cada dificuldade define o tamanho do tabuleiro, cartas especiais e tentativas.
 */
public enum GameDifficulty {
    EASY("Fácil 4x4", 4, 4, 2, 25),
    MEDIUM("Médio 6x6", 6, 6, 4, 60),
    HARD("Difícil 8x8", 8, 8, 6, 110);

    private final String description;
    private final int rows;
    private final int cols;
    private final int specialCards;
    private final int initialAttempts;

    /**
     * Cria uma dificuldade com os seus valores de configuração.
     *
     * @param description texto mostrado ao jogador
     * @param rows número de linhas do tabuleiro
     * @param cols número de colunas do tabuleiro
     * @param specialCards número de cartas especiais
     * @param initialAttempts número inicial de tentativas
     */
    GameDifficulty(String description, int rows, int cols, int specialCards, int initialAttempts) {
        this.description = description;
        this.rows = rows;
        this.cols = cols;
        this.specialCards = specialCards;
        this.initialAttempts = initialAttempts;
    }

    /**
     * Devolve o número de linhas da dificuldade.
     *
     * @return número de linhas
     */
    public int getRows() {
        return rows;
    }

    /**
     * Devolve o número de colunas da dificuldade.
     *
     * @return número de colunas
     */
    public int getCols() {
        return cols;
    }

    /**
     * Devolve o número de cartas especiais.
     *
     * @return número de cartas especiais
     */
    public int getSpecialCards() {
        return specialCards;
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
     * Devolve a descrição da dificuldade.
     *
     * @return descrição da dificuldade
     */
    @Override
    public String toString() {
        return description;
    }
}
