import model.GameEngine;
import model.NormalCard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NormalCardTest {

    @Test
    void isCardRevealed() {
        NormalCard card = new NormalCard("A");
        card.reveal(null);
        assertTrue(card.isRevealed());
    }
}