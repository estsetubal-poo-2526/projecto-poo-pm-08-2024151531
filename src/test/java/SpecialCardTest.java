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
    void testBonusCardAddsAttempts() throws InvalidBoardException {
        Board board = new Board(4, 4);
        GameEngine game = new GameEngine(board);
        int before = game.getAttempts();
        SpecialCard card = new SpecialCard("!", SpecialCard.EffectType.BONUS);
        card.reveal(game);

        assertEquals(before + 3, game.getAttempts());
    }
}
