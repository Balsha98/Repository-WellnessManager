import assets.enums.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.*;
import javafx.scene.image.Image;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.*;

public class ExerciseLogVBox extends VBox implements IVBoxType {

    // FoodLog object.
    private ExerciseLog exerciseLog;

    // Scene containers.
    private VBox logVBox, leftVBox, inputVBox, rightVBox, exerciseVBox;
    private HBox mainHBox;
    private ScrollPane scrollPane;

    // Scene components.
    private Label lblTitle = new Label("Exercise Log");

    // Image container.
    private ImageView imgView = new ImageView(new Image("./assets/media/calendar.png"));

    // Exercise components.
    private Label lblExerciseName = new Label("Exercise Name:");
    private TextField tfExerciseName = new TextField();
    private Label lblCalories = new Label("Enter Calories:");
    private TextField tfCalories = new TextField();
    private Label lblExerciseList = new Label("List of available exercises.");
    private Button btnAddExercise = new Button("Save Exercise");

    // Element arrays.
    private Node[] inputElements = { lblExerciseName, tfExerciseName, lblCalories, tfCalories, btnAddExercise };
    private Label[] labels = { lblExerciseName, lblCalories, lblExerciseList };
    private TextField[] fields = { tfExerciseName, tfCalories };

    // Default constructor.
    public ExerciseLogVBox() {
        this.getStylesheets().add("./assets/css/style.css");
        this.getStyleClass().add("root_box");

        exerciseLog = ExerciseLog.getInstance();
        this.createVBox();
    }

    // Inherited method.
    public VBox getVBox() {
        return this;
    }

    /**
     * Getting the only button.
     * 
     * @return - the Add button.
     */
    public Button getBtn() {
        return btnAddExercise;
    }

    // Creating the parent.
    public void createVBox() {
        this.initExerciseLogComponents();
        this.layoutExerciseLogScene();
    }

    // Initialize child elements.
    private void initExerciseLogComponents() {
        logVBox = new VBox(40);
        mainHBox = new HBox(150);

        leftVBox = new VBox(50);
        inputVBox = new VBox(25);

        rightVBox = new VBox(10);
        exerciseVBox = new VBox(10);
        scrollPane = new ScrollPane(exerciseVBox);
    }

    // Styling the child elements.
    private void setExerciseLogFonts() {
        lblTitle.setFont(FontEnum.TITLE_FONT.getFont());
        lblTitle.setTextFill(ColorEnum.BLUE.getColor());

        for (Label lbl : labels) {
            lbl.setFont(FontEnum.LBL_FONT.getFont());
            lbl.setTextFill(ColorEnum.BLUE.getColor());
        }
    }

    // Setting the Exercise Input VBox.
    private void setInputVBox() {
        imgView.setFitWidth(250);
        imgView.setFitHeight(250);

        inputVBox.setFillWidth(false);
        inputVBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < inputElements.length; i++) {
            if (i < inputElements.length - 1 && i % 2 != 0) {
                VBox innerVBox = new VBox(10);
                innerVBox.setMinWidth(250);
                innerVBox.setAlignment(Pos.CENTER);
                innerVBox.getChildren().addAll(inputElements[i - 1], inputElements[i]);
                inputVBox.getChildren().add(innerVBox);
            } else
                inputVBox.getChildren().add(inputElements[i]);
        }

        leftVBox.setAlignment(Pos.TOP_CENTER);
        leftVBox.getChildren().addAll(imgView, inputVBox);
        mainHBox.getChildren().add(leftVBox);
    }

    // Setting the Exercise VBox pane.
    private void setExerciseVBox() {
        exerciseVBox.setId("exercise_vbox");
        scrollPane.getStyleClass().add("scroll_pane");
        scrollPane.setPrefViewportHeight(475);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.updateExerciseVBoxPane();

        rightVBox.setAlignment(Pos.CENTER_RIGHT);
        rightVBox.getChildren().addAll(scrollPane, lblExerciseList);
        mainHBox.getChildren().add(rightVBox);
    }

    // Dynamically generating HBoxes.
    public void updateExerciseVBoxPane() {
        if (!exerciseVBox.getChildren().isEmpty())
            exerciseVBox.getChildren().clear();

        for (IExerciseType eLog : exerciseLog.getLogEntries()) {
            HBox innerHBox = new HBox(10);

            Label lblName = new Label(eLog.getName());
            lblName.getStyleClass().add("exercise_label");
            lblName.setMinWidth(150);

            TextField dynamicTfCalories = new TextField();
            dynamicTfCalories.setPrefWidth(60);
            dynamicTfCalories.setAlignment(Pos.CENTER);
            dynamicTfCalories.setText(String.format("%.1f", eLog.getCalories()));
            dynamicTfCalories.setEditable(false);

            Label dynamicLblCalories = new Label("calories.");
            dynamicLblCalories.getStyleClass().add("exercise_label");
            dynamicLblCalories.setPrefWidth(50);

            innerHBox.getChildren().addAll(lblName, dynamicTfCalories, dynamicLblCalories);
            exerciseVBox.getChildren().add(innerHBox);
        }
    }

    // Styling the button.
    private void styleButton() {
        btnAddExercise.setPrefWidth(125);
        btnAddExercise.setPrefHeight(35);
        btnAddExercise.setFont(FontEnum.DEF_BTN_FONT.getFont());
    }

    // Setting the layout.
    private void layoutExerciseLogScene() {
        this.setExerciseLogFonts();
        this.setInputVBox();
        this.setExerciseVBox();
        this.styleButton();

        logVBox.setPadding(new Insets(25, 40, 25, 40));
        logVBox.getChildren().addAll(lblTitle, mainHBox);

        this.getChildren().addAll(logVBox);
    }

    // ---------- CALLED FROM THE CONTROLLER ---------- //
    // Saving an Exercise Entry.
    public ExerciseType saveExerciseToDb() {
        if (checkIfEmpty())
            return null;

        String exerciseName = tfExerciseName.getText();
        double exerciseCalories = Double.parseDouble(tfCalories.getText());

        ExerciseType exerciseType = new ExerciseType(exerciseName, exerciseCalories);
        if (exerciseLog.doesExerciseExist(exerciseType))
            return null;

        this.resetFields();

        return exerciseType;
    }

    // Resetting the fields.
    private void resetFields() {
        for (TextField field : fields)
            field.setText("");
    }

    // Checking for empty fields.
    private boolean checkIfEmpty() {
        for (TextField field : fields)
            if (field.getText().isBlank())
                return true;

        return false;
    }
}
