package exception;

/**
 * Exceção usada quando o tabuleiro tem uma configuração inválida.
 */
public class InvalidBoardException extends RuntimeException {

    /**
     * Cria uma exceção com uma mensagem explicativa.
     *
     * @param message mensagem do erro
     */
    public InvalidBoardException(String message) {
        super(message);
    }
}
