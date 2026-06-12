package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Board;
import model.Card;
import model.SpecialCard;

import java.io.InputStream;

/**
 * Representa a interface gráfica principal do jogo.
 * Mostra o tabuleiro, tentativas, pares e mensagens ao jogador.
 */
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

    /**
     * Interface usada para avisar quando uma carta é clicada.
     */
    public interface CardClickHandler {

        /**
         * Trata o clique numa carta.
         *
         * @param row linha da carta
         * @param col coluna da carta
         */
        void handle(int row, int col);
    }

    /**
     * Cria a vista principal do jogo.
     */
    public GameView() {
        this.attemptsLabel = new Label();
        this.pairsLabel = new Label();
        this.statusLabel = new Label("Escolhe duas cartas.");
        this.boardGrid = new GridPane();
        this.newGameButton = new Button("Novo jogo");
        this.exitButton = new Button("Sair");

        configureLayout();
    }

    /**
     * Configura a organização visual da interface.
     */
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

    /**
     * Cria o cabeçalho da interface.
     *
     * @return cabeçalho da janela
     */
    private VBox createHeader() {
        Label titleLabel = new Label("Jogo da Memória");
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

    /**
     * Cria o rodapé da interface.
     *
     * @return rodapé da janela
     */
    private HBox createFooter() {
        Label helpLabel = new Label("Encontra todos os pares antes das tentativas acabarem.");
        helpLabel.setStyle("-fx-text-fill: #555555;");

        HBox footer = new HBox(helpLabel);
        footer.setAlignment(Pos.CENTER);
        return footer;
    }

    /**
     * Define o comportamento ao clicar numa carta.
     *
     * @param cardClickHandler controlador do clique
     */
    public void setCardClickHandler(CardClickHandler cardClickHandler) {
        this.cardClickHandler = cardClickHandler;
    }

    /**
     * Define o comportamento do botão de novo jogo.
     *
     * @param newGameHandler ação a executar
     */
    public void setOnNewGameRequested(Runnable newGameHandler) {
        newGameButton.setOnAction(event -> {
            if (newGameHandler != null) {
                newGameHandler.run();
            }
        });
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
     * Atualiza a interface com o estado atual do jogo.
     *
     * @param board tabuleiro do jogo
     * @param attempts tentativas restantes
     * @param pairsFound pares encontrados
     * @param totalPairs total de pares
     */
    public void refresh(Board board, int attempts, int pairsFound, int totalPairs) {
        attemptsLabel.setText("Tentativas: " + attempts);
        pairsLabel.setText("Pares: " + pairsFound + "/" + totalPairs);

        if (mustRebuildBoard(board)) {
            buildBoard(board);
        }

        updateBoard(board);
    }

    /**
     * Indica se o tabuleiro visual tem de ser reconstruído.
     *
     * @param board tabuleiro do jogo
     * @return true se for necessário reconstruir
     */
    private boolean mustRebuildBoard(Board board) {
        return cardButtons == null
                || cardButtons.length != board.getRows()
                || cardButtons[0].length != board.getCols();
    }

    /**
     * Cria os botões das cartas.
     *
     * @param board tabuleiro do jogo
     */
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

    /**
     * Cria um botão para uma carta.
     *
     * @param row linha da carta
     * @param col coluna da carta
     * @return botão da carta
     */
    private Button createCardButton(int row, int col) {
        Button cardButton = new Button(HIDDEN_SYMBOL);
        cardButton.setMinSize(CARD_SIZE, CARD_SIZE);
        cardButton.setPrefSize(CARD_SIZE, CARD_SIZE);
        cardButton.setMaxSize(CARD_SIZE, CARD_SIZE);
        cardButton.setFont(Font.font("Segoe UI Emoji", 28));
        cardButton.setStyle("-fx-padding: 0;");
        cardButton.setFocusTraversable(false);
        cardButton.setOnAction(event -> {
            if (cardClickHandler != null) {
                cardClickHandler.handle(row, col);
            }
        });
        return cardButton;
    }

    /**
     * Atualiza todos os botões do tabuleiro.
     *
     * @param board tabuleiro do jogo
     */
    private void updateBoard(Board board) {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                updateCardButton(cardButtons[row][col], board.getCard(row, col));
            }
        }
    }

    /**
     * Atualiza a aparência de uma carta.
     *
     * @param button botão da carta
     * @param card carta do modelo
     */
    private void updateCardButton(Button button, Card card) {
        if (card.isFixed()) {
            if (card instanceof SpecialCard) {
                button.setText(card.getSymbol());
                button.setGraphic(null);
                button.setStyle("-fx-padding: 0; -fx-background-color: #fef3c7; -fx-border-color: #f59e0b; -fx-border-width: 2;");
            } else {
                button.setText("");
                button.setGraphic(null);
                button.setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-border-color: transparent;");
            }
            button.setDisable(true);
        } else if (card.isRevealed()) {
            if (card instanceof SpecialCard) {
                button.setText(card.getSymbol());
                button.setGraphic(null);
            } else {
                ImageView cardImage = createCardImage(card.getSymbol());
                button.setGraphic(cardImage);
                button.setText(cardImage == null ? card.getSymbol() : "");
            }
            button.setDisable(false);
            if (card instanceof SpecialCard) {
                button.setStyle("-fx-padding: 0; -fx-background-color: #fef3c7; -fx-border-color: #f59e0b; -fx-border-width: 2;");
            } else {
                button.setStyle("-fx-padding: 0; -fx-background-color: #f8fafc; -fx-border-color: #2563eb; -fx-border-width: 2;");
            }
        } else {
            button.setText(HIDDEN_SYMBOL);
            button.setGraphic(null);
            button.setDisable(false);
            button.setStyle("-fx-padding: 0; -fx-background-color: #2563eb; -fx-text-fill: white;");
        }
    }

    /**
     * Cria a imagem de uma carta normal.
     *
     * @param imageName nome da imagem
     * @return imagem da carta
     */
    private ImageView createCardImage(String imageName) {
        InputStream imageStream = getClass().getResourceAsStream("/images/" + imageName + ".png");

        if (imageStream == null) {
            return null;
        }

        Image image = new Image(imageStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(44);
        imageView.setFitHeight(44);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Atualiza a mensagem de estado.
     *
     * @param message mensagem a mostrar
     */
    public void setStatus(String message) {
        statusLabel.setText(message);
    }

    /**
     * Ativa ou desativa o tabuleiro.
     *
     * @param disabled true para desativar
     */
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

    /**
     * Mostra uma mensagem de erro.
     *
     * @param message mensagem a mostrar
     */
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Jogada inválida");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Mostra a mensagem final do jogo.
     *
     * @param won true se o jogador ganhou
     */
    public void showEndGame(boolean won) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fim do jogo");
        alert.setHeaderText(won ? "Vitória!" : "Derrota!");
        alert.setContentText(won
                ? "Encontraste todos os pares."
                : "Ficaste sem tentativas.");
        alert.show();
    }
}
