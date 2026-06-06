package model;

public class SpecialCard extends Card {

    public enum EffectType {
        BONUS,
        SHUFFLE
    }

    private final EffectType effectType;
    private boolean effectApplied;

    public SpecialCard(String symbol, EffectType effectType) {
        super(symbol);

        if (effectType == null) {
            throw new IllegalArgumentException("Tipo de Efeito não pode ser nulo.");
        }

        this.effectType = effectType;
        this.effectApplied = false;
    }

    @Override
    public void reveal(GameEngine gameEngine) {
        setRevealed(true);

        if (!effectApplied) {
            applyEffect(gameEngine);
            effectApplied = true;
        }
    }

    private void applyEffect(GameEngine gameEngine) {
        if (effectType == EffectType.BONUS) {
            gameEngine.addAttempts(3);
        } else if (effectType == EffectType.SHUFFLE) {
            gameEngine.shuffleUnfixedCards();
        }
    }

    public EffectType getEffectType() {
        return effectType;
    }
}