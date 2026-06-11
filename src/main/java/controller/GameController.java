package controller;

import exception.InvalidMoveException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.GameEngine;
import ui.GameView;

public class GameController {

    private final Stage stage;
    private final GameEngine gameEngine;
    private final GameView gameView;

    public GameController(Stage stage, GameEngine gameEngine) {
        this.stage = stage;
        this.gameEngine = gameEngine;
        this.gameView = new GameView();

        configureActions();
        updateView();
    }

    private void configureActions() {
        gameView.setCardClickHandler((row, col) -> play(row, col));
        gameView.setOnNewGameRequested(() -> backToStart());
    }

    public void show() {
        Scene scene = new Scene(gameView, 640, 620);

        stage.setTitle("Jogo da Memoria");
        stage.setScene(scene);
        stage.show();
    }

    private void play(int row, int col) {
        try {
            gameEngine.play(row, col);
            updateView();
            checkEndGame();
        } catch (InvalidMoveException exception) {
            gameView.showError(exception.getMessage());
        } catch (IndexOutOfBoundsException exception) {
            gameView.showError("Posicao invalida.");
        }
    }

    private void updateView() {
        gameView.refresh(
                gameEngine.getBoard(),
                gameEngine.getAttempts(),
                gameEngine.getPairsFound(),
                gameEngine.getTotalPairs()
        );

        gameView.setStatus("Escolhe duas cartas.");
    }

    private void checkEndGame() {
        if (gameEngine.isGameOver()) {
            gameView.setBoardDisabled(true);
            gameView.showEndGame(gameEngine.isWinner());
        }
    }

    private void backToStart() {
        StartController startController = new StartController(stage);
        startController.show();
    }
}
