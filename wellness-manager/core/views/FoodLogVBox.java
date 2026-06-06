package core.views;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.*;
import javafx.scene.control.TabPane.*;
import javafx.scene.image.Image;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import java.util.*;
import java.io.*;
import core.interfaces.IVBoxType;
import core.interfaces.IFoodEntryType;
import core.models.logs.FoodLog;
import core.models.tables.RecTableLog;
import core.models.types.BasicType;
import core.models.types.RecipeType;
import core.enums.*;

public class FoodLogVBox extends VBox implements IVBoxType {

    // FoodLog object.
    private FoodLog foodLog;

    // Scene containers.
    private VBox logVBox, inputVBox, recipeVBoxL, recipeVBoxR, checkVBox;
    private HBox itemHBox, recipeHBox, recipeNameHBox;
    private ScrollPane scrollPane;

    // Scene components.
    private Label lblTitle = new Label("Food Log");
    private TabPane foodLogPane;
    private Tab foodTab, recipeTab;

    // Image container.
    private ImageView imgView = new ImageView(new Image("./assets/media/food.png"));

    // Basic food components.
    private Label lblItemName = new Label("Item Name:");
    private TextField tfItemName = new TextField();
    private Label lblCalories = new Label("Enter Calories:");
    private TextField tfCalories = new TextField();
    private Label lblFats = new Label("Enter Fats:");
    private TextField tfFats = new TextField();
    private Label lblCarbs = new Label("Enter Carbs:");
    private TextField tfCarbs = new TextField();
    private Label lblProtein = new Label("Enter Protein:");
    private TextField tfProtein = new TextField();
    private Button btnSaveItem = new Button("Save Item");

    // Recipe components.
    private Label lblHolder = new Label("No items to be added.");
    private TableView<RecTableLog> logTable;
    private TableColumn<RecTableLog, String> itemCol;
    private TableColumn<RecTableLog, Double> countCol;
    private ObservableList<RecTableLog> tableList;

    private List<CheckBox> chBoxList;
    private List<TextField> tfCountList;
    private TextField tfRecipeName = new TextField();
    private Button btnAddRecipe = new Button("Save Recipe");
    private Button btnAddItems = new Button("Add Items");

    // Element arrays.
    private Node[] inputElements = { lblItemName, tfItemName, lblCalories, tfCalories,
            lblFats, tfFats, lblCarbs, tfCarbs, lblProtein, tfProtein, btnSaveItem };
    private Label[] labels = { lblItemName, lblCalories, lblFats, lblCarbs, lblProtein, lblHolder };
    private TextField[] fields = { tfItemName, tfCalories, tfFats, tfCarbs, tfProtein };
    private Button[] btns = { btnSaveItem, btnAddRecipe, btnAddItems };

    // Default constructor.
    public FoodLogVBox() {
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int i) {
                // Removes outline from tab buttons.
            }
        }));

        this.getStylesheets().add("./assets/css/style.css");
        this.getStyleClass().add("root_box");

        foodLog = FoodLog.getInstance();
        this.createVBox();
    }

    // Inherited method.
    public VBox getVBox() {
        return this;
    }

    // Getting the Buttons.
    public Button[] getVBoxBtns() {
        return btns;
    }

    // Creating the parent.
    public void createVBox() {
        this.initFoodLogComponents();
        this.layoutFoodLogScene();
    }

    // Initialize child elements.
    private void initFoodLogComponents() {
        logVBox = new VBox(40);
        itemHBox = new HBox(150);
        inputVBox = new VBox(25);

        recipeHBox = new HBox(100);
        recipeVBoxL = new VBox(15);
        logTable = new TableView<>();
        recipeVBoxR = new VBox(25);
        recipeNameHBox = new HBox(15);
        checkVBox = new VBox(10);
        scrollPane = new ScrollPane(checkVBox);

        chBoxList = new ArrayList<>();
        tfCountList = new ArrayList<>();

        foodLogPane = new TabPane();
        foodTab = new Tab("Add Food Item");
        recipeTab = new Tab("Add Recipe Item");
    }

    // Setting up the recipe tab.
    private void setRecipeTable() {
        logTable.setPlaceholder(lblHolder);

        itemCol = new TableColumn<>("Item Name");
        itemCol.setCellValueFactory(data -> data.getValue().getName());

        countCol = new TableColumn<>("Item Count");
        countCol.setCellValueFactory(data -> data.getValue().getCount().asObject());

        TableColumn[] columns = { itemCol, countCol };
        for (TableColumn column : columns) {
            column.setPrefWidth(340.0 / columns.length);
            logTable.getColumns().add(column);
        }

        tableList = FXCollections.observableArrayList();
    }

    // Styling the log pane.
    private void styleFoodPane() {
        foodLogPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        foodLogPane.setPrefSize(WIDTH - 50.0, HEIGHT);
    }

    // Styling the child elements.
    private void setFoodLogFonts() {
        lblTitle.setFont(FontEnum.TITLE_FONT.getFont());
        lblTitle.setTextFill(ColorEnum.BLUE.getColor());

        for (Label lbl : labels) {
            lbl.setFont(FontEnum.LBL_FONT.getFont());
            lbl.setTextFill(ColorEnum.BLUE.getColor());
        }
    }

    // Setting the Basic Food pane.
    private void setBasicTabPane() {
        inputVBox.setFillWidth(false);
        inputVBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < inputElements.length; i++) {
            if (i < inputElements.length - 1 && i % 2 != 0) {
                VBox innerVBox = new VBox(5);
                innerVBox.setMinWidth(250);
                innerVBox.setAlignment(Pos.CENTER);
                innerVBox.getChildren().addAll(inputElements[i - 1], inputElements[i]);
                inputVBox.getChildren().add(innerVBox);
            } else
                inputVBox.getChildren().add(inputElements[i]);
        }

        imgView.setFitWidth(375);
        imgView.setFitHeight(375);
        itemHBox.getChildren().addAll(inputVBox, imgView);
    }

    // Setting the Recipe Food pane.
    private void setRecipeTabPane() {
        this.setRecipeTable();

        scrollPane.getStyleClass().add("scroll_pane");
        scrollPane.setPrefViewportHeight(375);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.updateRecipeTabPane();

        checkVBox.setId("check_vbox");
        recipeVBoxL.setMinWidth(350);
        recipeVBoxL.setAlignment(Pos.CENTER);
        recipeVBoxL.getChildren().addAll(scrollPane, btnAddItems);

        tfRecipeName.setEditable(false);
        tfRecipeName.setPrefWidth(200);
        tfRecipeName.setPromptText("Recipe name...");
        recipeNameHBox.getChildren().addAll(tfRecipeName, btnAddRecipe);

        recipeVBoxR.setAlignment(Pos.CENTER);
        recipeVBoxR.getChildren().addAll(logTable, recipeNameHBox);

        recipeHBox.getChildren().addAll(recipeVBoxL, recipeVBoxR);
    }

    // Dynamically generating HBoxes.
    public void updateRecipeTabPane() {
        if (!checkVBox.getChildren().isEmpty())
            checkVBox.getChildren().clear();

        for (IFoodEntryType fLog : foodLog.getLogEntries()) {
            HBox innerHBox = new HBox();

            CheckBox checkBox = new CheckBox(fLog.getName());
            checkBox.getStyleClass().add("check_box");
            checkBox.setMinWidth(135);
            chBoxList.add(checkBox);

            TextField tfCount = new TextField();
            tfCount.setPrefWidth(100);
            tfCount.setPromptText("Item count...");
            tfCountList.add(tfCount);

            innerHBox.getChildren().addAll(checkBox, tfCount);
            checkVBox.getChildren().add(innerHBox);
        }
    }

    // Styling Buttons.
    private void styleButtons() {
        for (Button btn : btns) {
            btn.setPrefWidth(125);
            btn.setPrefHeight(35);
            btn.setFont(FontEnum.DEF_BTN_FONT.getFont());
            if (btn.getText().contains("Recipe"))
                btnAddRecipe.setDisable(true);
        }
    }

    // Setting the layout.
    private void layoutFoodLogScene() {
        this.styleFoodPane();
        this.setFoodLogFonts();
        this.setBasicTabPane();
        this.setRecipeTabPane();
        this.styleButtons();

        Tab[] tabs = { foodTab, recipeTab };
        for (Tab tab : tabs)
            tab.getStyleClass().add("collection_tab");

        foodTab.setContent(itemHBox);
        recipeTab.setContent(recipeHBox);
        foodLogPane.getStyleClass().add("floating");
        foodLogPane.getTabs().addAll(foodTab, recipeTab);

        logVBox.setPadding(new Insets(25, 40, 25, 40));
        logVBox.getChildren().addAll(lblTitle, foodLogPane);

        this.getChildren().addAll(logVBox);
    }

    // ---------- CALLED FROM THE CONTROLLER ---------- //
    // Saving a Basic Food.
    public BasicType saveItemToDb() {
        if (checkIfEmpty())
            return null;

        String itemName = tfItemName.getText();
        double[] userInputs = {
                Double.parseDouble(tfCalories.getText()),
                Double.parseDouble(tfFats.getText()),
                Double.parseDouble(tfCarbs.getText()),
                Double.parseDouble(tfProtein.getText())
        };

        BasicType basicItem = new BasicType(itemName, userInputs[0],
                userInputs[1], userInputs[2], userInputs[3]);

        if (foodLog.doesItemExist(basicItem))
            return null;

        this.resetBasicFields();

        return basicItem;
    }

    // Resetting the fields.
    private void resetBasicFields() {
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

    // Adding items to the table.
    public void addItemsToTable() {
        if (!tableList.isEmpty())
            tableList.clear();

        if (!checksAreChecked())
            return;

        tfRecipeName.setEditable(true);
        tfRecipeName.requestFocus();
        btnAddRecipe.setDisable(false);

        for (int i = 0; i < chBoxList.size(); i++)
            if (chBoxList.get(i).isSelected()) {
                String countInput = tfCountList.get(i).getText();
                if (!countInput.isBlank()) {
                    double count = Double.parseDouble(countInput);
                    tableList.add(new RecTableLog(chBoxList.get(i).getText(), count));
                }
            }

        this.resetRecipeFields();
        logTable.setItems(tableList);
    }

    // Saving a Recipe Item.
    public RecipeType saveRecipeToDb() {
        String newRecipe = tfRecipeName.getText();

        if (newRecipe.isBlank())
            return null;

        for (IFoodEntryType entryType : foodLog.getLogEntries())
            if (newRecipe.equals(entryType.getName()))
                return null;

        tfRecipeName.setEditable(false);
        tfRecipeName.setText("");
        btnAddRecipe.setDisable(true);
        scrollPane.requestFocus();

        RecipeType recipeItem = new RecipeType(newRecipe, "");

        if (foodLog.doesItemExist(recipeItem))
            return null;

        for (RecTableLog tableLog : tableList)
            recipeItem.addItem(tableLog.getName().get(), tableLog.getCount().get());

        tableList.clear();
        return recipeItem;
    }

    // Resetting the fields.
    private void resetRecipeFields() {
        for (int i = 0; i < chBoxList.size(); i++)
            if (chBoxList.get(i).isSelected())
                if (!tfCountList.get(i).getText().isBlank()) {
                    chBoxList.get(i).setSelected(false);
                    tfCountList.get(i).setText("");
                }
    }

    // Checking for checked items.
    private boolean checksAreChecked() {
        int checked = 0;
        for (int i = 0; i < chBoxList.size(); i++)
            if (chBoxList.get(i).isSelected()) {
                checked++;
                if (tfCountList.get(i).getText().isBlank())
                    return false;
            }

        return checked != 0;
    }
}
