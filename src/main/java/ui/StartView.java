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

public class StartView extends BorderPane {

    private final ComboBox<GameDifficulty> difficultyComboBox;
    private final Button startButton;
    private final Button exitButton;
    private StartGameHandler startGameHandler;

    public interface StartGameHandler {
        void handle(GameDifficulty difficulty);
    }

    public StartView() {
        this.difficultyComboBox = new ComboBox<>();
        this.startButton = new Button("Iniciar jogo");
        this.exitButton = new Button("Sair");

        configureLayout();
        configureActions();
    }

    private void configureLayout() {
        setPadding(new Insets(24));

        Label titleLabel = new Label("Jogo da Memoria");
        titleLabel.setFont(Font.font(26));
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label descriptionLabel = new Label("Escolhe a dificuldade.");

        configureDifficultyComboBox();

        VBox content = new VBox(16, titleLabel, descriptionLabel, difficultyComboBox, startButton, exitButton);
        content.setAlignment(Pos.CENTER);

        setCenter(content);
    }

    private void configureDifficultyComboBox() {
        difficultyComboBox.getItems().add(GameDifficulty.EASY);
        difficultyComboBox.getItems().add(GameDifficulty.MEDIUM);
        difficultyComboBox.getItems().add(GameDifficulty.HARD);
        difficultyComboBox.setValue(GameDifficulty.EASY);
        difficultyComboBox.setMinWidth(180);
    }

    private void configureActions() {
        startButton.setOnAction(event -> startGame());
    }

    private void startGame() {
        GameDifficulty difficulty = difficultyComboBox.getValue();

        if (difficulty == null) {
            showError("Escolhe uma dificuldade.");
        } else if (startGameHandler != null) {
            startGameHandler.handle(difficulty);
        }
    }

    public void setStartGameHandler(StartGameHandler startGameHandler) {
        this.startGameHandler = startGameHandler;
    }

    public void setOnExitRequested(Runnable exitHandler) {
        exitButton.setOnAction(event -> {
            if (exitHandler != null) {
                exitHandler.run();
            }
        });
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
