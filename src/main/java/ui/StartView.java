package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.GameDifficulty;

/**
 * Representa o ecrã inicial da aplicação.
 * Permite escolher a dificuldade, iniciar o jogo ou sair.
 */
public class StartView extends BorderPane {

    private final ComboBox<GameDifficulty> difficultyComboBox;
    private final Button startButton;
    private final Button exitButton;
    private StartGameHandler startGameHandler;

    /**
     * Interface usada para avisar que o jogador quer iniciar o jogo.
     */
    public interface StartGameHandler {

        /**
         * Trata o pedido para iniciar o jogo.
         *
         * @param difficulty dificuldade escolhida
         */
        void handle(GameDifficulty difficulty);
    }

    /**
     * Cria a vista inicial.
     */
    public StartView() {
        this.difficultyComboBox = new ComboBox<>();
        this.startButton = new Button("Iniciar jogo");
        this.exitButton = new Button("Sair");

        configureLayout();
        configureActions();
    }

    /**
     * Configura a organização visual do ecrã inicial.
     */
    private void configureLayout() {
        setPadding(new Insets(24));

        Label titleLabel = new Label("Jogo da Memória");
        titleLabel.setFont(Font.font(26));
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label descriptionLabel = new Label("Escolhe a dificuldade.");

        configureDifficultyComboBox();

        VBox content = new VBox(16, titleLabel, descriptionLabel, difficultyComboBox, startButton, exitButton);
        content.setAlignment(Pos.CENTER);

        setCenter(content);
    }

    /**
     * Configura a caixa de seleção da dificuldade.
     */
    private void configureDifficultyComboBox() {
        difficultyComboBox.getItems().add(GameDifficulty.EASY);
        difficultyComboBox.getItems().add(GameDifficulty.MEDIUM);
        difficultyComboBox.getItems().add(GameDifficulty.HARD);
        difficultyComboBox.setValue(GameDifficulty.EASY);
        difficultyComboBox.setMinWidth(180);
    }

    /**
     * Configura as ações dos botões.
     */
    private void configureActions() {
        startButton.setOnAction(event -> startGame());
    }

    /**
     * Inicia o jogo com a dificuldade escolhida.
     */
    private void startGame() {
        GameDifficulty difficulty = difficultyComboBox.getValue();

        if (difficulty == null) {
            showError("Escolhe uma dificuldade.");
        } else if (startGameHandler != null) {
            startGameHandler.handle(difficulty);
        }
    }

    /**
     * Define o comportamento ao iniciar o jogo.
     *
     * @param startGameHandler ação a executar
     */
    public void setStartGameHandler(StartGameHandler startGameHandler) {
        this.startGameHandler = startGameHandler;
    }

    /**
     * Define o comportamento do botão de sair.
     *
     * @param exitHandler ação a executar
     */
    public void setOnExitRequested(Runnable exitHandler) {
        exitButton.setOnAction(event -> {
            if (exitHandler != null) {
                exitHandler.run();
            }
        });
    }

    /**
     * Mostra uma mensagem de erro.
     *
     * @param message mensagem a mostrar
     */
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
