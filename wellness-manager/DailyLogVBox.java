import assets.enums.*;
import assets.logs.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.util.*;

public class DailyLogVBox extends VBox implements IVBoxType {

    // Singletons.
    private DailyLog dailyLog;
    private FoodLog foodLog;
    private ExerciseLog exerciseLog;

    // Scene containers.
    private VBox leftVBox, rightVBox, bottomVBox, calorieVBox;
    private HBox mainHBox, dateHBox, bottomHBox1, bottomHBox2, caloriesHBox;

    // Scene components.
    private Label lblTitle = new Label("Daily Log");
    private DatePicker datePicker;
    private Button btnLoad = new Button("Load");
    private Button btnDelete = new Button("Delete Entry");

    // Table components.
    private TableView<DlyTableLog> logTable;
    private ObservableList<DlyTableLog> tableList;

    // TextFields & Labels.
    private Label lblHolder = new Label("No entry found for the selected date.");
    private Label lblSelectFood = new Label("Food:");
    private ComboBox<String> foodBox = new ComboBox<>();
    private Label lblFoodCount = new Label("Food Count:");
    private TextField tfFoodCount = new TextField();
    private Label lblEnterWeight = new Label("Weight:");
    private TextField tfWeight = new TextField();
    private Label lblDesiredCalories = new Label("Calories:");
    private TextField tfCalories = new TextField();

    // Exercise related components.
    private Label lblSelectExercise = new Label("Exercise:");
    private ComboBox<String> exerciseBox = new ComboBox<>();
    private Label lblExDuration = new Label("Duration:");
    private TextField tfExDuration = new TextField();

    // Daily log buttons.
    private Button btnReset = new Button("Reset");
    private Button btnSaveFood = new Button("Save Log");

    // Calories calculation elements.
    private TextField tfCalConsumed = new TextField();
    private Label lblMinus = new Label("-");
    private TextField tfCalBurnt = new TextField();
    private Label lblEquals = new Label("=");
    private TextField tfTotalCalories = new TextField();
    private Label lblCalorieIntake = new Label("Calories intake overview.");
    private String[] tfPlaceholder = { "Consumed", "Burnt", "Total" };

    // Element arrays.
    private Node[] inputElements = { lblSelectFood, foodBox, lblFoodCount, tfFoodCount,
            lblEnterWeight, tfWeight, lblDesiredCalories, tfCalories };
    private Node[] exerciseElements = { lblSelectExercise, exerciseBox, lblExDuration, tfExDuration };
    private Node[] caloriesElements = { tfCalConsumed, lblMinus, tfCalBurnt, lblEquals, tfTotalCalories };
    private Label[] labels = { lblHolder, lblSelectFood, lblFoodCount, lblEnterWeight, lblDesiredCalories,
            lblEnterWeight, lblSelectExercise, lblExDuration, lblMinus, lblEquals, lblCalorieIntake };
    private TextField[] fields = { tfFoodCount, tfWeight, tfCalories,
            tfExDuration, tfCalConsumed, tfCalBurnt, tfTotalCalories };
    private Button[] btns = { btnLoad, btnDelete, btnReset, btnSaveFood };

    // Chart components.
    private Label chartTitle = new Label("Overall Nutrients");
    private PieChart pieChart;
    private ObservableList<PieChart.Data> chartData;
    private String[] chartSections = { "Fats", "Carbs", "Protein" };

    // Default constructor.
    public DailyLogVBox() {
        this.getStylesheets().add("./assets/css/style.css");
        this.getStyleClass().add("root_box");

        dailyLog = DailyLog.getInstance();
        foodLog = FoodLog.getInstance();
        exerciseLog = ExerciseLog.getInstance();

        this.createVBox();
    }

    // Returning the parent.
    public VBox getVBox() {
        return this;
    }

    // Getting the buttons.
    public Button[] getVBoxBtns() {
        return btns;
    }

    // Getting the Date picker.
    public DatePicker getDatePicker() {
        return datePicker;
    }

    // Initializing the parent.
    public void createVBox() {
        this.initDailyLogComponents();
        this.customizeDatePicker();
        this.initLogTable();
        this.layoutDailyLog();
    }

    // Initializing the child elements.
    private void initDailyLogComponents() {
        leftVBox = new VBox(25);
        rightVBox = new VBox(15);
        bottomVBox = new VBox(15);
        mainHBox = new HBox(50);
        dateHBox = new HBox(25);

        bottomHBox1 = new HBox(35);
        bottomHBox2 = new HBox(35);

        datePicker = new DatePicker(LocalDate.now());
        logTable = new TableView<>();
        pieChart = new PieChart();

        calorieVBox = new VBox(10);
        caloriesHBox = new HBox(25);
    }

    // Initializing the log table.
    private void initLogTable() {
        logTable.setPlaceholder(lblHolder);
        logTable.setPrefHeight(337.5);

        TableColumn<DlyTableLog, LocalDate> dateCol = new TableColumn<>("Entry Date");
        dateCol.setCellValueFactory(data -> data.getValue().getDate());

        TableColumn<DlyTableLog, String> typeCol = new TableColumn<>("Entry Type");
        typeCol.setCellValueFactory(data -> data.getValue().getType());

        TableColumn<DlyTableLog, String> nameCol = new TableColumn<>("Entry Name");
        nameCol.setCellValueFactory(data -> data.getValue().getName());

        TableColumn<DlyTableLog, Double> amountCol = new TableColumn<>("Entry Value");
        amountCol.setCellValueFactory(data -> data.getValue().getAmount().asObject());

        TableColumn[] columns = { dateCol, typeCol, nameCol, amountCol };
        for (TableColumn column : columns) {
            column.setPrefWidth(600.0 / columns.length);
            logTable.getColumns().add(column);
        }

        tableList = FXCollections.observableArrayList();
    }

    // Customizing the Date picker.
    private void customizeDatePicker() {
        datePicker.setPrefWidth(215);
        datePicker.getEditor().setEditable(false);
        datePicker.getEditor().setAlignment(Pos.CENTER);
        datePicker.getEditor().setStyle("-fx-display-caret: false;");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                }

                return "";
            }

            @Override
            public LocalDate fromString(String str) {
                return (str != null && !str.isEmpty()) ? LocalDate.parse(str, dateFormatter) : null;
            }
        });
    }

    // Setting fonts of the child elements.
    private void setDailyLogFonts() {
        lblTitle.setFont(FontEnum.TITLE_FONT.getFont());
        lblTitle.setTextFill(ColorEnum.BLUE.getColor());

        lblHolder.setFont(FontEnum.LBL_FONT.getFont());
        lblHolder.setTextFill(ColorEnum.BLUE.getColor());

        for (int i = 1; i < labels.length; i++) {
            labels[i].setFont(FontEnum.LBL_FONT.getFont());
            labels[i].setTextFill(ColorEnum.WHITE.getColor());
        }
    }

    // Styling the TextFields.
    private void styleInputFields() {
        for (int i = 0; i < fields.length; i++) {
            if (i >= 4 && i <= fields.length - 1)
                fields[i].setPromptText(tfPlaceholder[i - 4]);

            fields[i].setPrefWidth(120);
        }
    }

    // Styling the buttons.
    private void styleButtons() {
        for (Button btn : btns) {
            btn.setPrefWidth(120);
            btn.setPrefHeight(27.5);
            btn.setFont(FontEnum.DEF_BTN_FONT.getFont());
        }
    }

    // Setting the layout.
    private void layoutDailyLog() {
        this.setDailyLogFonts();
        this.styleInputFields();
        this.styleButtons();

        dateHBox.getChildren().addAll(datePicker, btnLoad, btnDelete);

        this.fillFoodBox();
        this.fillExerciseBox();
        ComboBox[] comboBoxes = { foodBox, exerciseBox };
        for (ComboBox box : comboBoxes) {
            box.setPrefWidth(120);
            box.setVisibleRowCount(3);
        }

        HBox.setHgrow(bottomHBox1, Priority.ALWAYS);
        for (int i = 0; i <= inputElements.length; i++) {
            if (i != 0 && i % 2 != 0) {
                VBox innerVBox = new VBox(5);
                innerVBox.setAlignment(Pos.CENTER);
                innerVBox.getChildren().addAll(inputElements[i - 1], inputElements[i]);
                bottomHBox1.getChildren().add(innerVBox);
            }
        }

        for (int i = 0; i <= exerciseElements.length; i++) {
            if (i != 0 && i % 2 != 0) {
                VBox innerVBox = new VBox(5);
                innerVBox.setAlignment(Pos.CENTER);
                innerVBox.getChildren().addAll(exerciseElements[i - 1], exerciseElements[i]);
                bottomHBox2.getChildren().add(innerVBox);
            }
        }

        for (Button btn : btns) {
            String btnText = btn.getText();
            if (!btnText.equals("Load") && !btnText.contains("Delete"))
                bottomHBox2.getChildren().add(btn);
        }

        bottomVBox.setAlignment(Pos.CENTER);
        bottomVBox.setPadding(new Insets(25));
        bottomVBox.getStyleClass().add("dlog_vbox_hbox");
        bottomVBox.getChildren().addAll(bottomHBox1, bottomHBox2);

        leftVBox.setAlignment(Pos.TOP_LEFT);
        leftVBox.getChildren().addAll(
                lblTitle,
                dateHBox,
                logTable,
                bottomVBox);

        chartData = FXCollections.observableArrayList();
        this.setChartData(new double[] { 33.0, 33.0, 33.0 });

        chartTitle.setFont(FontEnum.SUB_TITLE_FONT.getFont());
        chartTitle.setTextFill(ColorEnum.BLUE.getColor());
        pieChart.setClockwise(false);
        pieChart.getStyleClass().add("pie_chart");
        pieChart.setData(chartData);

        for (Node calNode : caloriesElements) {
            if (calNode instanceof TextField) {
                TextField tfNode = (TextField) calNode;
                tfNode.setPrefWidth(100);
                tfNode.setAlignment(Pos.CENTER);
                tfNode.setEditable(false);

                caloriesHBox.getChildren().add(tfNode);
            } else
                caloriesHBox.getChildren().add(calNode);
        }

        caloriesHBox.getStyleClass().add("dlog_vbox_hbox");
        lblCalorieIntake.setTextFill(ColorEnum.BLUE.getColor());

        calorieVBox.setId("calories_vbox");
        calorieVBox.getChildren().addAll(caloriesHBox, lblCalorieIntake);

        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.getChildren().addAll(chartTitle, pieChart, calorieVBox);

        mainHBox.setPadding(new Insets(25, 40, 25, 40));
        mainHBox.getChildren().addAll(leftVBox, rightVBox);

        this.getChildren().addAll(mainHBox);
    }

    // ---------- CALLED FROM THE CONTROLLER ---------- //
    // Loading the Log data.
    public void loadDailyLogData() {
        this.fillFoodBox();
        this.fillExerciseBox();

        if (!tableList.isEmpty())
            tableList.clear();

        LocalDate currDate = datePicker.getValue();
        for (IEntryType entry : dailyLog.getLogEntries()) {
            String[] logParts = entry.toString().trim().split(",");
            LocalDate logDate = entry.getDate();

            if (!currDate.toString().equals(logDate.toString()))
                continue;

            if (logParts.length == 5)
                tableList.add(new DlyTableLog(
                        currDate, entry.getType(), "/", entry.getAmount()));
            else {
                if (entry instanceof FoodEntry) {
                    FoodEntry fEntry = (FoodEntry) entry;
                    tableList.add(new DlyTableLog(
                            currDate, fEntry.getType(), fEntry.getFoodName(), fEntry.getAmount()));
                } else {
                    ExerciseEntry eEntry = (ExerciseEntry) entry;
                    tableList.add(new DlyTableLog(
                            currDate, eEntry.getType(), eEntry.getExerciseName(), eEntry.getAmount()));
                }
            }
        }

        this.calcCalorieIntake();

        int foodEntryCounter = 0;
        for (DlyTableLog entry : tableList)
            if (!entry.getName().get().equals("/"))
                if (entry.getType().get().equals("Food Consumed"))
                    foodEntryCounter++;

        if (foodEntryCounter > 0) {
            chartData.clear();
            this.setChartData(dailyLog.calcNutrients(currDate));
            pieChart.setData(chartData);
        }

        logTable.setItems(tableList);
    }

    // Deleting an entry.
    public Object[] deleteEntry() {
        DlyTableLog selectedEntry = logTable.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            tableList.remove(selectedEntry);

            String entryName = selectedEntry.getName().get();
            Double entryAmount = selectedEntry.getAmount().get();
            if (entryName.equals("/"))
                return new Object[] { selectedEntry.getType().get(), entryAmount };
            else
                return new Object[] { entryName, entryAmount };
        }

        return new Object[] {};
    }

    // Setting up the chart.
    private void setChartData(double[] chartValues) {
        for (int i = 0; i < chartSections.length; i++) {
            double chartValue = chartValues[i];
            chartData.add(new PieChart.Data(
                    String.format("%s (%.1f)", chartSections[i], chartValue),
                    chartValue));
        }
    }

    // Filling the Food Combo Box.
    public void fillFoodBox() {
        if (!foodBox.getItems().isEmpty())
            foodBox.getItems().clear();

        for (IFoodEntryType entryType : foodLog.getLogEntries())
            foodBox.getItems().add(entryType.getName());
    }

    // Saving a food item.
    public FoodEntry saveFoodCount(LocalDate date) {
        String foodInput = tfFoodCount.getText();

        if (foodBox.getSelectionModel().getSelectedItem() != null)
            if (foodBox.getSelectionModel().getSelectedItem().isBlank())
                return null;

        if (foodInput.isBlank() || !foodInput.matches("[\\d]+"))
            return null;

        return new FoodEntry(date, foodBox.getValue(), Double.parseDouble(foodInput));
    }

    // Saving both weight & calorie inputs.
    public IEntryType[] saveWeightCalories(LocalDate date) {
        String[] inputs = { tfWeight.getText(), tfCalories.getText() };
        double wInput, cInput;

        for (String input : inputs)
            if (!input.matches("[\\d]+") && !input.isBlank())
                return new IEntryType[] {};

        // Checking weight input.
        if (inputs[0].isBlank())
            wInput = dailyLog.getRecentWeight(date);
        else
            wInput = Double.parseDouble(inputs[0]);

        // Checking calorie input.
        if (inputs[1].isBlank())
            cInput = dailyLog.getRecentCalories(date);
        else
            cInput = Double.parseDouble(inputs[1]);

        return new IEntryType[] { new WeightEntry(date, wInput), new CalorieLimitEntry(date, cInput) };
    }

    // Saving the Exercise input.
    public ExerciseEntry saveExerciseLog(LocalDate date) {
        String exerciseInput = tfExDuration.getText();

        if (exerciseBox.getSelectionModel().getSelectedItem() != null)
            if (exerciseBox.getSelectionModel().getSelectedItem().isBlank())
                return null;

        if (exerciseInput.isBlank() || !exerciseInput.matches("[\\d]+"))
            return null;

        return new ExerciseEntry(date, exerciseBox.getValue(), Double.parseDouble(exerciseInput));
    }

    // Calculating the user's calorie intake.
    private void calcCalorieIntake() {
        double[] calIntake = new double[3];

        for (DlyTableLog dLog : tableList) {
            if (dLog.getType().get().contains("Food")) {
                calIntake[0] += dailyLog.getNutrient(dLog.getName().get(), dLog.getAmount().get(), 2);
            } else if (dLog.getType().get().contains("Exercise")) {
                calIntake[1] += exerciseLog.getCaloriesBurnt(dLog.getName().get(), dLog.getAmount().get());
            }
        }

        calIntake[2] = calIntake[0] - calIntake[1];
        for (int i = 0; i < fields.length; i++)
            if (i >= 4 && i <= fields.length - 1)
                fields[i].setText(String.format("%.1f", calIntake[i - 4]));
    }

    // Filling the Exercise Combo Box.
    public void fillExerciseBox() {
        if (!exerciseBox.getItems().isEmpty())
            exerciseBox.getItems().clear();

        for (IExerciseType eEntry : exerciseLog.getLogEntries())
            exerciseBox.getItems().add(eEntry.getName());
    }

    // Resetting the fields.
    public void resetFields() {
        foodBox.setValue("");
        foodBox.requestFocus();
        for (TextField field : fields)
            field.setText("");
    }
}
