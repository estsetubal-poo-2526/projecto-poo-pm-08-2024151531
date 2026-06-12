import exception.InvalidBoardException;
import model.Board;
import model.GameEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {

    private Board board;
    private GameEngine game;

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
            board = new Board(4, 4);
            game = new GameEngine(board);
        });
    }

    @Test
    void testGameEngineCreation() {
        assertNotNull(game);
    }

    @Test
    void testAddAttempts() {
        int before = game.getAttempts();
        game.addAttempts(3);
        assertEquals(before + 3, game.getAttempts());
    }

    @Test
    void testNullBoardThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new GameEngine(null)
        );
    }
}