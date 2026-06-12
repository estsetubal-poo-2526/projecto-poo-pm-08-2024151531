import controller.StartController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principal da aplicação JavaFX.
 * Inicia o ecrã inicial do jogo.
 */
public class Main extends Application {

    /**
     * Método chamado pelo JavaFX para iniciar a aplicação.
     *
     * @param stage janela principal da aplicação
     */
    @Override
    public void start(Stage stage) {
        StartController startController = new StartController(stage);
        startController.show();
    }

    /**
     * Ponto de entrada da aplicação.
     *
     * @param args argumentos da linha de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}
