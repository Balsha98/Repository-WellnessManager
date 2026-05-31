import javafx.application.Application;
import javafx.stage.Stage;

// Main for running the application.
public class Main extends Application {

    // Main method.
    public static void main(String[] args) {
        launch(args);
    }

    // Starting the application.
    public void start(Stage stage) {
        Model model = Model.getInstance();
        View view = new View();
        Controller controller = new Controller(model, view);
    }
}
