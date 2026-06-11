import controller.StartController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        StartController startController = new StartController(stage);
        startController.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
