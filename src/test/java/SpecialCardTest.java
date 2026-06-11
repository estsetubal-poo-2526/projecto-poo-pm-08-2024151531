import exception.InvalidBoardException;
import model.Board;
import model.GameEngine;
import model.SpecialCard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpecialCardTest {

    @Test
    void testNullEffectThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SpecialCard("!", null)
        );
    }

    @Test
    public void testEffectTypeCannotBeNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SpecialCard("!", null)
        );
    }
}
