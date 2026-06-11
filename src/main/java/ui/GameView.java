package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Board;
import model.Card;
import model.SpecialCard;

public class GameView extends BorderPane {

    private static final String HIDDEN_SYMBOL = "*";
    private static final int CARD_SIZE = 82;

    private final Label attemptsLabel;
    private final Label pairsLabel;
    private final Label statusLabel;
    private final GridPane boardGrid;
    private final Button newGameButton;
    private final Button exitButton;

    private Button[][] cardButtons;
    private CardClickHandler cardClickHandler;

    public interface CardClickHandler {
        void handle(int row, int col);
    }

    public GameView() {
        this.attemptsLabel = new Label();
        this.pairsLabel = new Label();
        this.statusLabel = new Label("Escolhe duas cartas.");
        this.boardGrid = new GridPane();
        this.newGameButton = new Button("Novo jogo");
        this.exitButton = new Button("Sair");

        configureLayout();
    }

    private void configureLayout() {
        setPadding(new Insets(18));
        setTop(createHeader());
        setCenter(boardGrid);
        setBottom(createFooter());

        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(10);
        boardGrid.setVgap(10);
        BorderPane.setMargin(boardGrid, new Insets(20, 0, 20, 0));
    }

    private VBox createHeader() {
        Label titleLabel = new Label("Jogo da Memoria");
        titleLabel.setFont(Font.font(24));
        titleLabel.setStyle("-fx-font-weight: bold;");

        HBox statsBox = new HBox(18, attemptsLabel, pairsLabel);
        statsBox.setAlignment(Pos.CENTER_LEFT);

        HBox headerActions = new HBox(10, newGameButton, exitButton);
        headerActions.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(headerActions, Priority.ALWAYS);

        HBox headerRow = new HBox(18, titleLabel, statsBox, headerActions);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        VBox header = new VBox(10, headerRow, statusLabel);
        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    private HBox createFooter() {
        Label helpLabel = new Label("Encontra todos os pares antes das tentativas acabarem.");
        helpLabel.setStyle("-fx-text-fill: #555555;");

        HBox footer = new HBox(helpLabel);
        footer.setAlignment(Pos.CENTER);
        return footer;
    }

    public void setCardClickHandler(CardClickHandler cardClickHandler) {
        this.cardClickHandler = cardClickHandler;
    }

    public void setOnNewGameRequested(Runnable newGameHandler) {
        newGameButton.setOnAction(event -> {
            if (newGameHandler != null) {
                newGameHandler.run();
            }
        });
    }

    public void setOnExitRequested(Runnable exitHandler) {
        exitButton.setOnAction(event -> {
            if (exitHandler != null) {
                exitHandler.run();
            }
        });
    }

    public void refresh(Board board, int attempts, int pairsFound, int totalPairs) {
        attemptsLabel.setText("Tentativas: " + attempts);
        pairsLabel.setText("Pares: " + pairsFound + "/" + totalPairs);

        if (mustRebuildBoard(board)) {
            buildBoard(board);
        }

        updateBoard(board);
    }

    private boolean mustRebuildBoard(Board board) {
        return cardButtons == null
                || cardButtons.length != board.getRows()
                || cardButtons[0].length != board.getCols();
    }

    private void buildBoard(Board board) {
        boardGrid.getChildren().clear();
        cardButtons = new Button[board.getRows()][board.getCols()];

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Button cardButton = createCardButton(row, col);
                cardButtons[row][col] = cardButton;
                boardGrid.add(cardButton, col, row);
            }
        }
    }

    private Button createCardButton(int row, int col) {
        Button cardButton = new Button(HIDDEN_SYMBOL);
        cardButton.setMinSize(CARD_SIZE, CARD_SIZE);
        cardButton.setPrefSize(CARD_SIZE, CARD_SIZE);
        cardButton.setMaxSize(CARD_SIZE, CARD_SIZE);
        cardButton.setFont(Font.font(28));
        cardButton.setFocusTraversable(false);
        cardButton.setOnAction(event -> {
            if (cardClickHandler != null) {
                cardClickHandler.handle(row, col);
            }
        });
        return cardButton;
    }

    private void updateBoard(Board board) {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                updateCardButton(cardButtons[row][col], board.getCard(row, col));
            }
        }
    }

    private void updateCardButton(Button button, Card card) {
        if (card.isFixed()) {
            if (card instanceof SpecialCard) {
                button.setText(card.getSymbol());
                button.setStyle("-fx-background-color: #fef3c7; -fx-border-color: #f59e0b; -fx-border-width: 2;");
            } else {
                button.setText("");
                button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            }
            button.setDisable(true);
        } else if (card.isRevealed()) {
            button.setText(card.getSymbol());
            button.setDisable(false);
            if (card instanceof SpecialCard) {
                button.setStyle("-fx-background-color: #fef3c7; -fx-border-color: #f59e0b; -fx-border-width: 2;");
            } else {
                button.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #2563eb; -fx-border-width: 2;");
            }
        } else {
            button.setText(HIDDEN_SYMBOL);
            button.setDisable(false);
            button.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white;");
        }
    }

    public void setStatus(String message) {
        statusLabel.setText(message);
    }

    public void setBoardDisabled(boolean disabled) {
        if (cardButtons == null) {
            return;
        }

        for (Button[] rowButtons : cardButtons) {
            for (Button button : rowButtons) {
                button.setDisable(disabled || !HIDDEN_SYMBOL.equals(button.getText()));
            }
        }
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Jogada invalida");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showEndGame(boolean won) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fim do jogo");
        alert.setHeaderText(won ? "Vitoria!" : "Derrota!");
        alert.setContentText(won
                ? "Encontraste todos os pares."
                : "Ficaste sem tentativas.");
        alert.showAndWait();
    }
}
