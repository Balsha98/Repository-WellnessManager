import javafx.application.Application;
import javafx.stage.Stage;
import core.models.Model;
import core.views.View;
import core.controllers.Controller;

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
        new Controller(model, view);
    }
}
