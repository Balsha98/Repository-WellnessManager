package core.controllers;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import core.interfaces.IEntryType;
import core.interfaces.IFoodEntryType;
import core.models.entries.*;
import core.models.types.*;
import core.models.Model;
import core.views.HomeVBox;
import core.views.DailyLogVBox;
import core.views.FoodLogVBox;
import core.views.ExerciseLogVBox;
import core.views.View;

public class Controller {

    // View & Model instances.
    private Model model;
    private View view;

    /**
     * Parameterized constructor.
     * 
     * @param view - App View.
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.setAppControls();
    }

    // Setting the App controls.
    private void setAppControls() {
        this.setViewStageControls();
        this.setHomeSceneControls();
        this.setDLogSceneControls();
        this.setFLogSceneControls();
        this.setELogSceneControls();
    }

    // ---------- CALLING VIEW METHODS ---------- //
    // Adding events to menu buttons.
    private void setViewStageControls() {
        this.enableMenuBar("DISABLE");
        for (Button btn : this.view.getViewBtns()) {
            btn.setOnMouseEntered(event -> btn.setCursor(Cursor.HAND));
            btn.addEventHandler(
                    MouseEvent.MOUSE_PRESSED,
                    event -> {
                        String btnText = btn.getText();
                        if (this.view.currRoot.equals("H"))
                            return;
                        else if (btnText.contains("Daily"))
                            this.view.switchToDailyLog();
                        else if (btnText.contains("Food"))
                            this.view.switchToFoodLog();
                        else if (btnText.contains("Exercise"))
                            this.view.switchToExerciseLog();
                    });
        }
    }

    // Disabling/Enabling the Menu buttons.
    private void enableMenuBar(String enDis) {
        for (Button btn : this.view.getViewBtns()) {
            if (enDis.equals("ENABLE"))
                btn.setDisable(btn.getText().contains("Home"));
            else
                btn.setDisable(!btn.getText().contains("Home"));
        }
    }

    // ---------- CALLING HOME_VBOX METHODS ---------- //
    // Working with Home buttons.
    private void setHomeSceneControls() {
        HomeVBox homeVBox = (HomeVBox) this.view.homeVBox;
        for (Button btn : homeVBox.getVBoxBtns()) {
            btn.setOnAction(event -> this.areCredentialsValid(btn.getText()));
            btn.setOnMouseEntered(event -> btn.setCursor(Cursor.HAND));
        }
    }

    // Checking whether user credentials are valid.
    private void areCredentialsValid(String inUp) {
        HomeVBox homeVBox = (HomeVBox) this.view.homeVBox;
        if (inUp.equals("Sign Up")) {
            if (!homeVBox.userSignUp(model.getData("users")))
                return;
            else
                model.addNewUserToDb(homeVBox.userProxy.getUser());
        } else {
            if (!homeVBox.userLogIn(model.getData("users")))
                return;
        }

        this.view.switchToDailyLog();
        this.enableMenuBar("ENABLE");
    }

    // ---------- CALLING DAILY_LOG_VBOX METHODS ---------- //
    // Working with DailyLog buttons.
    private void setDLogSceneControls() {
        DailyLogVBox dLogVBox = (DailyLogVBox) this.view.dailyLogVBox;
        for (Button btn : dLogVBox.getVBoxBtns()) {
            btn.setOnMouseEntered(event -> btn.setCursor(Cursor.HAND));
            btn.setOnAction(event -> {
                String btnText = btn.getText();
                if (btnText.equals("Load"))
                    dLogVBox.loadDailyLogData();
                else if (btnText.contains("Delete")) {
                    Object[] paramArr = dLogVBox.deleteEntry();
                    if (paramArr.length == 0)
                        System.out.println("Cannot delete the selected table entry.");
                    else {
                        model.removeDLogEntry(paramArr);
                        dLogVBox.loadDailyLogData();
                    }
                } else if (btnText.equals("Reset"))
                    dLogVBox.resetFields();
                else {
                    boolean toAppend = true;
                    String dbName = "log", type = "SAVE";
                    LocalDate pickedDate = dLogVBox.getDatePicker().getValue();

                    // Checking the validity of the FoodEntry.
                    FoodEntry fEntry = dLogVBox.saveFoodCount(pickedDate);
                    if (fEntry == null) {
                        System.out.println("Cannot save Food Consumed to the log.");
                        exerciseEntryHelper(dLogVBox, pickedDate);
                    } else {
                        model.saveOrRefreshDLogDb(dbName, type, toAppend, fEntry);
                        exerciseEntryHelper(dLogVBox, pickedDate);
                    }

                    // Saving the Weight & CalorieLimit Entries to the log.
                    IEntryType[] entries = dLogVBox.saveWeightCalories(pickedDate);
                    for (IEntryType entry : entries)
                        model.saveOrRefreshDLogDb(dbName, type, toAppend, entry);

                    dLogVBox.resetFields();
                    dLogVBox.loadDailyLogData();
                }
            });
        }
    }

    // ---------- CALLING FOOD_LOG_VBOX METHODS ---------- //
    // Working with FoodLog buttons.
    private void setFLogSceneControls() {
        DailyLogVBox dailyLogVBox = (DailyLogVBox) this.view.dailyLogVBox;
        FoodLogVBox fLogVBox = (FoodLogVBox) this.view.foodLogVBox;
        for (Button btn : fLogVBox.getVBoxBtns()) {
            btn.setOnMouseEntered(event -> btn.setCursor(Cursor.HAND));
            btn.setOnAction(event -> {
                String btnText = btn.getText();
                if (btnText.equals("Save Item")) {
                    BasicType newItem = fLogVBox.saveItemToDb();
                    if (newItem == null)
                        System.out.println("Cannot save BasicType item.");
                    else
                        this.updateFoodSelectors(fLogVBox, dailyLogVBox, newItem);
                } else if (btnText.contains("Add"))
                    fLogVBox.addItemsToTable();
                else {
                    RecipeType newItem = fLogVBox.saveRecipeToDb();
                    if (newItem == null)
                        System.out.println("Cannot save RecipeType item.");
                    else
                        this.updateFoodSelectors(fLogVBox, dailyLogVBox, newItem);
                }
            });
        }
    }

    /**
     * Controller's helper function for cleaner code.
     * 
     * @param fVBox   - FoodVBox object.
     * @param dVBox   - DailyLogVBox object.
     * @param newItem - new item object.
     */
    private void updateFoodSelectors(FoodLogVBox fVBox, DailyLogVBox dVBox, IFoodEntryType newItem) {
        model.addFLogToDb(newItem);
        fVBox.updateRecipeTabPane();
        dVBox.fillFoodBox();
    }

    // ---------- CALLING EXERCISE_LOG_VBOX METHODS ---------- //
    // Working with ExerciseLog buttons.
    private void setELogSceneControls() {
        DailyLogVBox dailyLogVBox = (DailyLogVBox) this.view.dailyLogVBox;
        ExerciseLogVBox exerciseLogVBox = (ExerciseLogVBox) this.view.exerciseLogVBox;

        // Button event handler.
        exerciseLogVBox.getBtn().setOnMouseEntered(
                event -> exerciseLogVBox.getBtn().setCursor(Cursor.HAND));
        exerciseLogVBox.getBtn().setOnAction(event -> {
            ExerciseType newExercise = exerciseLogVBox.saveExerciseToDb();
            if (newExercise == null)
                System.out.println("Cannot save ExerciseType item.");
            else
                model.addELogToDb(newExercise);

            dailyLogVBox.fillExerciseBox();
            exerciseLogVBox.updateExerciseVBoxPane();
        });
    }

    private void exerciseEntryHelper(DailyLogVBox dLogVBox, LocalDate pickedDate) {
        // Checking the validity of the ExerciseEntry.
        ExerciseEntry eEntry = dLogVBox.saveExerciseLog(pickedDate);
        if (eEntry == null)
            System.out.println("Cannot save Exercise Completed to the log.");
        else
            model.addEEntryToDb(eEntry);
    }
}
