import exception.InvalidBoardException;
import model.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void defaultBoardCreation()  {
        Board board = new Board(4, 4);
        assertEquals(4, board.getRows());
        assertEquals(4, board.getCols());
    }

    @Test
    void invalidBoardSizeThrowsException() {
        assertThrows(
                InvalidBoardException.class,
                () -> new Board(-1, 4)
        );
    }

    @Test
    void oddBoardSizeThrowsException() {
        assertThrows(
                InvalidBoardException.class,
                () -> new Board(3, 3)
        );
    }
}
