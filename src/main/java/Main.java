import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Board;
import model.GameEngine;
import ui.GameView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Board board = new Board(4, 4);
        GameEngine gameEngine = new GameEngine(board);
        GameView gameView = new GameView();

        gameView.refresh(
                board,
                gameEngine.getAttempts(),
                gameEngine.getPairsFound(),
                gameEngine.getTotalPairs()
        );

        Scene scene = new Scene(gameView, 600, 600);

        stage.setTitle("Jogo da Memoria");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}