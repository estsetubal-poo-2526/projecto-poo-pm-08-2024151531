package controller;

import exception.InvalidMoveException;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameEngine;
import model.SpecialCard;
import ui.GameView;

/**
 * Controla o ecrã principal do jogo.
 * Liga a interface gráfica ao motor do jogo.
 */
public class GameController {

    private final Stage stage;
    private final GameEngine gameEngine;
    private final GameView gameView;
    private boolean waitingForTurnEnd;

    /**
     * Cria o controlador do jogo.
     *
     * @param stage janela principal da aplicação
     * @param gameEngine motor com a lógica do jogo
     */
    public GameController(Stage stage, GameEngine gameEngine) {
        this.stage = stage;
        this.gameEngine = gameEngine;
        this.gameView = new GameView();
        this.waitingForTurnEnd = false;

        configureActions();
        updateView();
    }

    /**
     * Configura as ações dos botões e das cartas.
     */
    private void configureActions() {
        gameView.setCardClickHandler((row, col) -> play(row, col));
        gameView.setOnNewGameRequested(() -> backToStart());
        gameView.setOnExitRequested(() -> exit());
    }

    /**
     * Mostra a janela do jogo.
     */
    public void show() {
        Scene scene = new Scene(gameView, 900, 880);

        stage.setTitle("Jogo da Memória");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Processa uma jogada feita pelo jogador.
     *
     * @param row linha da carta escolhida
     * @param col coluna da carta escolhida
     */
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
        }
    }

    /**
     * Atualiza a interface com o estado atual do jogo.
     */
    private void updateView() {
        gameView.refresh(
                gameEngine.getBoard(),
                gameEngine.getAttempts(),
                gameEngine.getPairsFound(),
                gameEngine.getTotalPairs()
        );

        gameView.setStatus("Escolhe duas cartas.");
    }

    /**
     * Aplica o efeito de uma carta especial após uma pequena pausa.
     */
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

    /**
     * Termina a jogada após mostrar as duas cartas ao jogador.
     */
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

    /**
     * Verifica se o jogo terminou por vitória ou derrota.
     */
    private void checkEndGame() {
        if (gameEngine.isGameOver()) {
            gameView.setBoardDisabled(true);
            if (gameEngine.isWinner()) {
                gameView.setStatus("Ganhaste! Encontraste todos os pares.");
            } else {
                gameView.setStatus("Perdeste! Ficaste sem tentativas.");
            }
            gameView.showEndGame(gameEngine.isWinner());
        }
    }

    /**
     * Volta ao ecrã inicial.
     */
    private void backToStart() {
        StartController startController = new StartController(stage);
        startController.show();
    }

    /**
     * Fecha a aplicação.
     */
    private void exit() {
        stage.close();
    }
}
