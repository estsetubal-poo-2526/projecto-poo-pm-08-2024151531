package exception;

/**
 * Exceção usada quando uma jogada não é válida.
 */
public class InvalidMoveException extends RuntimeException {

    /**
     * Cria uma exceção com uma mensagem explicativa.
     *
     * @param message mensagem do erro
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
