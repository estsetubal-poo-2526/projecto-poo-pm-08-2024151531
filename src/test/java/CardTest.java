import model.Card;
import model.NormalCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private Card card;

    @BeforeEach
    void setUp() {
        card = new NormalCard("A");
    }

    @Test
    void isNormalCardStartsHidden() {
        assertFalse(card.isRevealed());
        assertFalse(card.isFixed());
    }

    @Test
    void isFixCard() {
        card.fix();
        assertTrue(card.isFixed());
        assertTrue(card.isRevealed());
    }

    @Test
    void invalidSymbolThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new NormalCard("")
        );
    }
}