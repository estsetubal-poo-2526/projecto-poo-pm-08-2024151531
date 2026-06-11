package controller;

import exception.InvalidBoardException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Board;
import model.GameEngine;
import ui.StartView;

public class StartController {

    private final Stage stage;
    private final StartView startView;

    public StartController(Stage stage) {
        this.stage = stage;
        this.startView = new StartView();

        configureActions();
    }

    private void configureActions() {
        startView.setStartGameHandler((rows, cols) -> startGame(rows, cols));
        startView.setOnExitRequested(() -> exit());
    }

    public void show() {
        Scene scene = new Scene(startView, 420, 320);

        stage.setTitle("Jogo da Memoria");
        stage.setScene(scene);
        stage.show();
    }

    private void startGame(int rows, int cols) {
        try {
            Board board = new Board(rows, cols);
            GameEngine gameEngine = new GameEngine(board);
            GameController gameController = new GameController(stage, gameEngine);
            gameController.show();
        } catch (InvalidBoardException exception) {
            startView.showError(exception.getMessage());
        }
    }

    private void exit() {
        stage.close();
    }
}
