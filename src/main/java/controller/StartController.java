package controller;

import exception.InvalidBoardException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Board;
import model.GameDifficulty;
import model.GameEngine;
import ui.StartView;

/**
 * Controla o ecrã inicial da aplicação.
 * Permite escolher a dificuldade e iniciar o jogo.
 */
public class StartController {

    private final Stage stage;
    private final StartView startView;

    /**
     * Cria o controlador do ecrã inicial.
     *
     * @param stage janela principal da aplicação
     */
    public StartController(Stage stage) {
        this.stage = stage;
        this.startView = new StartView();

        configureActions();
    }

    /**
     * Configura as ações dos botões do ecrã inicial.
     */
    private void configureActions() {
        startView.setStartGameHandler(difficulty -> startGame(difficulty));
        startView.setOnExitRequested(() -> exit());
    }

    /**
     * Mostra o ecrã inicial.
     */
    public void show() {
        Scene scene = new Scene(startView, 420, 320);

        stage.setTitle("Jogo da Memória");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cria um novo jogo com a dificuldade escolhida.
     *
     * @param difficulty dificuldade escolhida pelo jogador
     */
    private void startGame(GameDifficulty difficulty) {
        try {
            Board board = new Board(difficulty);
            GameEngine gameEngine = new GameEngine(board);
            GameController gameController = new GameController(stage, gameEngine);
            gameController.show();
        } catch (InvalidBoardException exception) {
            startView.showError(exception.getMessage());
        }
    }

    /**
     * Fecha a aplicação.
     */
    private void exit() {
        stage.close();
    }
}
