import model.GameEngine;
import model.NormalCard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NormalCardTest {

    @Test
    public void testRevealCard() {
        NormalCard card = new NormalCard("A");
        card.reveal(null);
        assertTrue(card.isRevealed());
    }
}