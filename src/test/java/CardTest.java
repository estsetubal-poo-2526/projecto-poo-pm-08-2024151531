import model.Card;
import model.NormalCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private Card card;

    @BeforeEach
    public void setUp() {
        card = new NormalCard("A");
    }

    @Test
    public void testNormalCardStartsHidden() {
        assertFalse(card.isRevealed());
        assertFalse(card.isFixed());
    }

    @Test
    public void testFixCard() {
        card.fix();
        assertTrue(card.isFixed());
        assertTrue(card.isRevealed());
    }

    @Test
    public void testInvalidSymbol() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new NormalCard("")
        );
    }
}