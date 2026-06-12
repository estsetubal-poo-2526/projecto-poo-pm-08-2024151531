import exception.InvalidBoardException;
import model.Board;
import model.GameEngine;
import model.SpecialCard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpecialCardTest {

    @Test
    void nullEffectThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SpecialCard("!", null)
        );
    }

    @Test
    void effectTypeCannotBeNullThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SpecialCard("!", null)
        );
    }
}
