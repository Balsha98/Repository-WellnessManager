import assets.enums.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.*;

public class HomeVBox extends VBox implements IVBoxType {

    // User proxy.
    public IUserValidation userProxy;

    // Main containers.
    private Dialog signUpWindow;
    private VBox homeVBox, inputVBox, signUpVBox;
    private HBox homeHBox, checkHBox;

    // Child elements.
    private ImageView imgView = new ImageView(new Image("./assets/media/icon.png"));
    private Label lblTitle = new Label("Welcome To Your Wellness Manager");
    private TextField signUser, signPass;
    private Label lblUser = new Label("Username:");
    private TextField logUser = new TextField();
    private Label lblPass = new Label("Password:");
    private TextField logPass = new TextField();
    private Button btnLogIn = new Button("Log In");
    private Label lblCheck = new Label("Don't have an account?");
    private Button btnSignUp = new Button("Sign Up");

    // Element arrays.
    private Node[] inputElements = { lblUser, logUser, lblPass, logPass, btnLogIn };
    private TextField[] fields = { logUser, logPass };
    private Button[] btns = { btnLogIn, btnSignUp };
    private List<String> userInputs;

    // Default constructor.
    public HomeVBox() {
        this.getStylesheets().add("./assets/css/style.css");
        this.getStyleClass().add("root_box");
        this.createVBox();
    }

    // Inherited method.
    public VBox getVBox() {
        return this;
    }

    // Getting the buttons.
    public Button[] getVBoxBtns() {
        return btns;
    }

    // Building the Home parent.
    public void createVBox() {
        this.initHomeComponents();
        this.layoutSignUpWindow();
        this.layoutHome();
    }

    // Initializing the child elements.
    private void initHomeComponents() {
        homeVBox = new VBox(50);
        homeHBox = new HBox(100);
        inputVBox = new VBox(35);
        checkHBox = new HBox(10);

        signUpWindow = new Dialog();
        signUpVBox = new VBox(15);
        signUser = new TextField();
        signPass = new TextField();
    }

    // Styling the Labels.
    private void setHomeFonts() {
        lblTitle.setFont(FontEnum.TITLE_FONT.getFont());
        lblTitle.setTextFill(ColorEnum.BLUE.getColor());

        Label[] labels = { lblUser, lblPass, lblCheck };
        for (Label lbl : labels) {
            lbl.setFont(FontEnum.LBL_FONT.getFont());
            lbl.setTextFill(ColorEnum.BLUE.getColor());
        }
    }

    // Styling the TextFields.
    private void styleInputFields() {
        for (TextField field : fields)
            field.setPrefWidth(250);
    }

    // Styling the Buttons.
    private void styleButtons() {
        for (Button btn : btns) {
            btn.setFont(FontEnum.LOG_BTN_FONT.getFont());

            if (btn.getText().contains("In")) {
                btn.setPrefWidth(125);
                btn.setPrefHeight(40);
            } else
                btn.setPrefWidth(100);
        }
    }

    // Setting the layout.
    private void layoutHome() {
        this.setHomeFonts();
        this.styleInputFields();
        this.styleButtons();

        for (int i = 0; i < inputElements.length; i++) {
            if (i < inputElements.length - 1 && i % 2 != 0) {
                VBox innerVBox = new VBox(5);
                innerVBox.setAlignment(Pos.CENTER);
                innerVBox.getChildren().addAll(inputElements[i - 1], inputElements[i]);
                inputVBox.getChildren().add(innerVBox);
            } else
                inputVBox.getChildren().add(inputElements[i]);
        }

        imgView.setFitWidth(400);
        imgView.setFitHeight(400);
        inputVBox.setAlignment(Pos.CENTER);
        homeHBox.setAlignment(Pos.CENTER);
        homeHBox.getChildren().addAll(inputVBox, imgView);

        checkHBox.setAlignment(Pos.CENTER);
        checkHBox.getChildren().addAll(lblCheck, btnSignUp);

        homeVBox.setMinHeight(HEIGHT - 65.0);
        homeVBox.setAlignment(Pos.CENTER);
        homeVBox.setPadding(new Insets(25, 40, 25, 40));
        homeVBox.getChildren().addAll(lblTitle, homeHBox, checkHBox);

        this.getChildren().addAll(homeVBox);
    }

    // Setting the sign up window.
    private void layoutSignUpWindow() {
        signUpWindow.setTitle("SignUp Window");
        signUpWindow.setHeaderText("Please, fill in the fields.");

        signUser.setPromptText("Username...");
        signPass.setPromptText("Password...");
        signUpVBox.setAlignment(Pos.CENTER_RIGHT);
        signUpVBox.getChildren().addAll(signUser, signPass);

        signUpWindow.getDialogPane().setContent(signUpVBox);
        signUpWindow.getDialogPane().setPrefSize(300, 0);
        signUpWindow.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    // ---------- CALLED FROM THE CONTROLLER ---------- //
    // Sign up validation.
    public boolean userSignUp(List<String> dbData) {
        this.signUpWindow.showAndWait();

        userInputs = new ArrayList<>(List.of(signUser.getText(), signPass.getText()));
        if (checkIfEmpty(userInputs))
            return false;

        userProxy = new UserProxy(userInputs.get(0), userInputs.get(1));
        return userProxy.isUnique(dbData);
    }

    // Log in validation.
    public boolean userLogIn(List<String> dbData) {
        userInputs = new ArrayList<>(List.of(logUser.getText(), logPass.getText()));
        if (checkIfEmpty(userInputs))
            return false;

        userProxy = new UserProxy(userInputs.get(0), userInputs.get(1));
        return userProxy.validateUser(dbData);
    }

    // Checking for empty fields.
    private boolean checkIfEmpty(List<String> data) {
        for (String input : data)
            if (input.isBlank())
                return true;

        return false;
    }
}
