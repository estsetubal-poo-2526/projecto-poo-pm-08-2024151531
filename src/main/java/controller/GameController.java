package controller;

import exception.InvalidMoveException;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameEngine;
import model.SpecialCard;
import ui.GameView;

public class GameController {

    private final Stage stage;
    private final GameEngine gameEngine;
    private final GameView gameView;
    private boolean waitingForTurnEnd;

    public GameController(Stage stage, GameEngine gameEngine) {
        this.stage = stage;
        this.gameEngine = gameEngine;
        this.gameView = new GameView();
        this.waitingForTurnEnd = false;

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
        if (waitingForTurnEnd) {
            return;
        }

        try {
            gameEngine.play(row, col);
            updateView();

            if (gameEngine.hasTwoSelectedCards()) {
                finishTurnWithDelay();
            } else if (gameEngine.hasSelectedSpecialCard()) {
                finishSpecialCardWithDelay();
            } else {
                checkEndGame();
            }
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

    private void finishSpecialCardWithDelay() {
        waitingForTurnEnd = true;
        gameView.setBoardDisabled(true);

        if (gameEngine.getSelectedSpecialEffectType() == SpecialCard.EffectType.SHUFFLE) {
            gameView.setStatus("Carta especial: vai baralhar o tabuleiro.");
        } else {
            gameView.setStatus("Carta especial: ganhas 3 tentativas.");
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            gameEngine.finishSpecialCard();
            waitingForTurnEnd = false;
            updateView();
            checkEndGame();
        });
        pause.play();
    }

    private void finishTurnWithDelay() {
        waitingForTurnEnd = true;
        gameView.setBoardDisabled(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            gameEngine.finishTurn();
            waitingForTurnEnd = false;
            updateView();
            checkEndGame();
        });
        pause.play();
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
