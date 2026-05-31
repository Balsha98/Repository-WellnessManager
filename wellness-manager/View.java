import java.util.*;
import assets.enums.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class View extends Stage implements ISceneManager {

    // Switchable parents.
    public String currRoot;
    public IVBoxType homeVBox, dailyLogVBox, foodLogVBox, exerciseLogVBox;

    // DAILY LOG layout attributes.
    private VBox root;
    private HBox menuHBox;

    // Menu items.
    private ToolBar menuBar;
    private Button menuHome, menuDailyLog, menuFoodLog, menuExerciseLog;
    private Button[] btns = { menuHome, menuDailyLog, menuFoodLog, menuExerciseLog };

    // Default constructor.
    public View() {
        this.getIcons().add(new Image("./assets/media/icon.png"));
        this.setTitle("Wellness Manager App");
        Locale.setDefault(Locale.ENGLISH);

        currRoot = "H";
        homeVBox = new HomeVBox();
        dailyLogVBox = new DailyLogVBox();
        foodLogVBox = new FoodLogVBox();
        exerciseLogVBox = new ExerciseLogVBox();

        this.menuBar = this.initMenuBar();
        this.setScene(new Scene(this.initRootWithBar(homeVBox.getVBox()), WIDTH, HEIGHT));
        this.setResizable(false);
        this.show();
    }

    // Getting the menu bar.
    public ToolBar getMenuBar() {
        return menuBar;
    }

    // Getting the buttons.
    public Button[] getViewBtns() {
        return btns;
    }

    // Initializing the ToolBar.
    private VBox initRootWithBar(VBox currVBox) {
        root = new VBox();
        root.getStylesheets().add("./assets/css/style.css");
        root.getChildren().addAll(menuBar, currVBox);
        return root;
    }

    // Initializing the menu bar.
    private ToolBar initMenuBar() {
        menuHBox = new HBox(100);
        menuBar = new ToolBar(menuHBox);
        menuHome = new Button("Home");
        menuDailyLog = new Button("Daily Log");
        menuFoodLog = new Button("Food Log");
        menuExerciseLog = new Button("Exercise Log");

        final String[] btnNames = { "Home", "Daily Log", "Food Log", "Exercise Log" };
        for (int i = 0; i < btnNames.length; i++)
            btns[i] = new Button(btnNames[i]);

        this.layoutMenuBar();

        return menuBar;
    }

    // Setting the layout.
    private void layoutMenuBar() {
        HBox.setHgrow(menuHBox, Priority.ALWAYS);
        menuBar.setMinHeight(75);
        menuBar.setId("menu_bar");
        for (Button btn : btns) {
            btn.setPrefWidth(125);
            btn.setPrefHeight(35);
            btn.setFont(FontEnum.LOG_BTN_FONT.getFont());
            btn.getStyleClass().add("menu_btn");
            menuHBox.getChildren().add(btn);
        }
    }

    // ---------- CALLED FROM THE CONTROLLER ---------- //
    // Switch to Daily Log parent.
    public void switchToDailyLog() {
        if (currRoot.equals("D"))
            return;

        this.setScene(new Scene(this.initRootWithBar(dailyLogVBox.getVBox()), WIDTH, HEIGHT));
        currRoot = "D";
    }

    // Switch to Food Log parent.
    public void switchToFoodLog() {
        if (currRoot.equals("F"))
            return;

        this.setScene(new Scene(this.initRootWithBar(foodLogVBox.getVBox()), WIDTH, HEIGHT));
        currRoot = "F";
    }

    // Switch to Exercise Log parent.
    public void switchToExerciseLog() {
        if (currRoot.equals("E"))
            return;

        this.setScene(new Scene(this.initRootWithBar(exerciseLogVBox.getVBox()), WIDTH, HEIGHT));
        currRoot = "E";
    }
}
