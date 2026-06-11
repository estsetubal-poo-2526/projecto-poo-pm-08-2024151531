package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StartView extends BorderPane {

    private final TextField rowsField;
    private final TextField colsField;
    private final Button startButton;
    private final Button exitButton;
    private StartGameHandler startGameHandler;

    public interface StartGameHandler {
        void handle(int rows, int cols);
    }

    public StartView() {
        this.rowsField = new TextField("4");
        this.colsField = new TextField("4");
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

        Label descriptionLabel = new Label("Escolhe o tamanho do tabuleiro.");

        GridPane form = createForm();

        VBox content = new VBox(16, titleLabel, descriptionLabel, form, startButton, exitButton);
        content.setAlignment(Pos.CENTER);

        setCenter(content);
    }

    private GridPane createForm() {
        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(10);
        form.setVgap(10);

        rowsField.setMaxWidth(80);
        colsField.setMaxWidth(80);

        form.add(new Label("Linhas:"), 0, 0);
        form.add(rowsField, 1, 0);
        form.add(new Label("Colunas:"), 0, 1);
        form.add(colsField, 1, 1);

        return form;
    }

    private void configureActions() {
        startButton.setOnAction(event -> startGame());
    }

    private void startGame() {
        try {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());

            if (startGameHandler != null) {
                startGameHandler.handle(rows, cols);
            }
        } catch (NumberFormatException exception) {
            showError("As linhas e colunas devem ser numeros inteiros.");
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
