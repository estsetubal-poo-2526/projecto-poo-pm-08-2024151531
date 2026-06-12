package model;

/**
 * Representa uma carta especial do jogo.
 * Ao ser ativada, aplica um efeito sobre o estado da partida.
 */
public class SpecialCard extends Card {

    /**
     * Tipos de efeito que uma carta especial pode ter.
     */
    public enum EffectType {
        BONUS,
        SHUFFLE
    }

    private final EffectType effectType;
    private boolean effectApplied;

    /**
     * Cria uma carta especial.
     *
     * @param symbol símbolo da carta
     * @param effectType tipo de efeito da carta
     */
    public SpecialCard(String symbol, EffectType effectType) {
        super(symbol);

        if (effectType == null) {
            throw new IllegalArgumentException("Tipo de efeito não pode ser nulo.");
        }

        this.effectType = effectType;
        this.effectApplied = false;
    }

    /**
     * Revela a carta especial.
     *
     * @param gameEngine motor do jogo
     */
    @Override
    public void reveal(GameEngine gameEngine) {
        setRevealed(true);
    }

    /**
     * Aplica o efeito da carta especial uma única vez.
     *
     * @param gameEngine motor do jogo
     */
    public void applyEffect(GameEngine gameEngine) {
        if (!effectApplied) {
            executeEffect(gameEngine);
            effectApplied = true;
        }
    }

    /**
     * Executa o efeito da carta especial.
     *
     * @param gameEngine motor do jogo
     */
    private void executeEffect(GameEngine gameEngine) {
        if (effectType == EffectType.BONUS) {
            gameEngine.addAttempts(3);
        } else if (effectType == EffectType.SHUFFLE) {
            gameEngine.shuffleUnfixedCards();
        }
    }

    /**
     * Devolve o tipo de efeito da carta.
     *
     * @return tipo de efeito
     */
    public EffectType getEffectType() {
        return effectType;
    }
}
