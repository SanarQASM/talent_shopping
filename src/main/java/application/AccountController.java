package application;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AccountController implements Initializable {
    private String messageTemp;//message to error
    private static Stage tempStage;//the account page stage
    private static AccountController aC;//leader controller
    private static enterEmailController eEC;
    private static NotificationsClass nC;//
    private static final Preferences pref = Preferences.userRoot().node("Rememberme");
//    private static final String filePath = "D:/eclipse/IdeaProjects/E-commerce-shopping/src/main/resources/database.txt"; // Path of the file to write to
    private static Validation vClass;
    private static final Routes route=Routes.getInstance();
    private static final String errorColor=Colors.BORDER_ERROR_COLOR.getColor();
    private static final String defaultColor= Colors.BORDER_DEFAULT_COLOR.getColor();
    private static final String cssStyle =References.CSS_STYLE.getImageReference();

    @FXML
    private ImageView registerIcon;

    @FXML
    private Button alreadyHaveAccountButton;

    @FXML
    private AnchorPane anchorForm;

    @FXML
    private Button createNewAccountButton;

    @FXML
    private TextField forgetAnswer;

    @FXML
    private Label forgetAnswerError;

    @FXML
    private ImageView forgetIcon;

    @FXML
    private ComboBox<String> forgetPassCombo;

    @FXML
    private AnchorPane forgetPasswordForm;

    @FXML
    private AnchorPane forgetPasswordWayForm;

    @FXML
    private Label forgetQuestionError;

    @FXML
    private TextField forgetUsername;

    @FXML
    private Label forgetUsernameError;

    @FXML
    private ImageView logInIcon;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private CheckBox rememberMe;

    @FXML
    private ImageView shapeImage;

    @FXML
    private ImageView showPass;

    @FXML
    private ImageView showPass1;

    @FXML
    private Label si_errorPassword;

    @FXML
    private Label si_errorUserName;

    @FXML
    private PasswordField si_passwordPass;

    @FXML
    private TextField si_passwordText;

    @FXML
    private TextField si_username;

    @FXML
    private StackPane stackPane;

    @FXML
    private TextField su_answer;

    @FXML
    private Label su_errorAnswer;

    @FXML
    private Label su_errorQuestion;

    @FXML
    private Label su_errorUserName;

    @FXML
    private Label su_errorUserPass;

    @FXML
    private PasswordField su_passwordPass;

    @FXML
    private TextField su_passwordText;

    @FXML
    private ComboBox<String> su_question;

    @FXML
    private TextField su_username;

    public AccountController(){}

    public AccountController(Stage stage, AccountController aC,NotificationsClass nC) {//from main to there
        tempStage = stage;
        AccountController.aC=aC;
        AccountController.nC=nC;
        vClass=Validation.getInstance(aC,null,null,null);
        tempStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                enterClick();
            }
        });
    }

    @FXML
    void showPassAction(MouseEvent ignoredEvent) {
        showingPassword(showPass, si_passwordPass, si_passwordText);
    }
    @FXML
    void showPassAction1(MouseEvent ignoredEvent) {
        showingPassword(showPass1, su_passwordPass, su_passwordText);
    }
    private void showingPassword(ImageView showPass, PasswordField siPasswordPass, TextField siPasswordText) {
        if (showPass.getImage().getUrl().equals(Objects.requireNonNull(getClass().getResource(References.HIDE_PASS_IMAGE.getImageReference())).toString())) {
            siPasswordText.setVisible(true);
            siPasswordPass.setVisible(false);
            siPasswordText.setText(siPasswordPass.getText());
            Image image = new Image(Objects.requireNonNull(getClass().getResource(References.SHOW_PASS_IMAGE.getImageReference())).toString());
            showPass.setImage(image);
        } else {
            siPasswordPass.setVisible(true);
            siPasswordText.setVisible(false);
            siPasswordPass.setText(siPasswordText.getText());
            Image image = new Image(Objects.requireNonNull(getClass().getResource(References.HIDE_PASS_IMAGE.getImageReference())).toString());
            showPass.setImage(image);
        }
    }
    @FXML
    void createNewAccount(ActionEvent ignoredEvent) {
        TranslateTransition slider = new TranslateTransition();
        slider.setNode(anchorForm);
        slider.setToX(300);
        slider.setDuration(Duration.seconds(0.5));
        alreadyHaveAccountButton.setVisible(true);
        createNewAccountButton.setVisible(false);
        TranslateTransition slider1 = new TranslateTransition();
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), shapeImage);
        rotateTransition.setByAngle(360);
        slider1.setNode(shapeImage);
        slider1.setToX(111);
        slider1.setToY(92);
        slider1.setDuration(Duration.seconds(1));
        ParallelTransition parallelTransition = new ParallelTransition(slider1, rotateTransition, slider);
        parallelTransition.play();
    }
    @FXML
    void alreadyHaveAccount(ActionEvent ignoredEvent) {
        transAction();
    }
    private void transAction() {
        TranslateTransition slider = new TranslateTransition();
        slider.setNode(anchorForm);
        slider.setToX(0);
        slider.setDuration(Duration.seconds(0.5));
        alreadyHaveAccountButton.setVisible(false);
        createNewAccountButton.setVisible(true);
        TranslateTransition slider1 = new TranslateTransition();
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), shapeImage);
        rotateTransition.setByAngle(-360);
        slider1.setNode(shapeImage);
        slider1.setToX(0);
        slider1.setToY(0);
        slider1.setDuration(Duration.seconds(1));
        ParallelTransition parallelTransition = new ParallelTransition(slider1, rotateTransition, slider);
        parallelTransition.play();
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {//run with javafx component
        String user;
        user = pref.get("si_username", null);
        si_username.setText(user);
        String pass;
        pass = pref.get("si_passwordPass", null);//if user sign in again
        si_passwordPass.setText(pass);
        si_passwordText.setText(pass);
        for (SecurityQuestions question : SecurityQuestions.values()) {
            su_question.getItems().add(question.getQuestion());
            forgetPassCombo.getItems().add(question.getQuestion());
        }
//		openDialogButton.setOnAction(e -> {//to required window
//			Stage dialogStage = new Stage();
//			dialogStage.initModality(Modality.APPLICATION_MODAL);
//			dialogStage.initOwner(tempStage);
//			dialogStage.setTitle("Modal Dialog");
//			StackPane dialogLayout = new StackPane(new Button("Close"));
//			dialogLayout.setPrefSize(200, 100);
//			dialogStage.setScene(new Scene(dialogLayout));
//			dialogStage.showAndWait();
//		});
    }

    @FXML
    void si_logIn(ActionEvent ignoredEvent) {
        startLoginCheck();
    }
    private void startLoginCheck(){
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                processLogin();
                return null;
            }
        };
        task.setOnFailed(_ -> handleLoginFailure());
        task.setOnSucceeded(_ -> handleLoginSuccess());
        new Thread(task).start();
        showLoadingStateForLogin();
    }
    private void processLogin() throws Exception {
        si_username.setText(si_username.getText().trim());
        checked(rememberMe.isSelected());
        vClass.checkingUsername_Si(si_username.getText());
    }
    private void handleLoginFailure() {
        Platform.runLater(() -> {
            stackPane.setDisable(false);
            updateLoginIcon(References.LOGIN_IMAGE.getImageReference());
            nC.showNotificationSomethingWrong(messageTemp);
        });
    }
    private void handleLoginSuccess() {
        Platform.runLater(() -> {
            try {
                openMainFrameUser();
            } catch (Exception e) {
                nC.showNotificationSomethingWrong(e.getMessage());
            }
        });
    }
    private void showLoadingStateForLogin() {
        Platform.runLater(() -> {
            updateLoginIcon(References.CHANGE_STATUS_GIF.getImageReference());
            stackPane.setDisable(true);
        });
    }
    private void updateLoginIcon(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        logInIcon.setImage(image);
    }

    @FXML
    void su_SignUp(ActionEvent ignoredEvent) {
        startSignUpCheck();
    }
    private void startSignUpCheck(){
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                processSignUp();
                return null;
            }
        };
        task.setOnSucceeded(_ -> handleSignUpSuccess());
        task.setOnFailed(_ -> handleSignUpFailure());
        new Thread(task).start();
        showLoadingStateForSignup();
    }
    private void processSignUp() throws Exception {
        su_username.setText(su_username.getText().trim());
        vClass.checkingUsername_Su(su_username.getText());
        String finalPassword = gettingFinalPass_Su();
        vClass.checkingPassword_Su(finalPassword);
        vClass.checkComboBoxForSignup(findIndexQuestionForSignup());
        su_answer.setText(su_answer.getText().trim());
        vClass.checkAnswerForSignup(su_answer.getText());
    }
    private void handleSignUpSuccess() {
        Platform.runLater(() -> {
            updateRegisterIcon(References.REGISTER_IMAGE.getImageReference());
            stackPane.setDisable(false);
            try {
                openSuEmail();
            } catch (IOException e) {
                nC.showNotificationSomethingWrong("Failed to open Email page");
            }
        });
    }
    private void handleSignUpFailure() {
        Platform.runLater(() -> {
            updateRegisterIcon(References.REGISTER_IMAGE.getImageReference());
            stackPane.setDisable(false);
            nC.showNotificationFieldEmpty(messageTemp);
        });
    }
    private void showLoadingStateForSignup() {
        Platform.runLater(() -> {
            updateRegisterIcon(References.CHANGE_STATUS_GIF.getImageReference());
            stackPane.setDisable(true);
        });
    }
    private void updateRegisterIcon(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        registerIcon.setImage(image);
    }

    //all these method use for set error and clear all button in account page.
    public void setErrorForUsernameLogin(String uiMessage, String exceptionMessage) throws Exception {
        Platform.runLater(() -> {
            si_errorUserName.setText(uiMessage);
            si_username.setStyle(errorColor);
            messageTemp=exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearErrorForUsernameLogin() {
        Platform.runLater(() -> {
            si_errorUserName.setText("");
            si_username.setStyle(defaultColor);
        });
    }

    public void setErrorForPasswordLogin(String uiMessage, String exceptionMessage) throws Exception {
        Platform.runLater(() -> {
            si_errorPassword.setText(uiMessage);
            si_passwordPass.setStyle(errorColor);
            si_passwordText.setStyle(errorColor);
            messageTemp=exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearErrorForPasswordLogin() {
        Platform.runLater(() -> {
            si_errorPassword.setText("");
            si_passwordPass.setStyle(defaultColor);
            si_passwordText.setStyle(defaultColor);
        });
    }

    public void showErrorForUsernameSignup(String errorText, String exceptionMessage) throws Exception {
        Platform.runLater(() -> {
            su_errorUserName.setText(errorText);
            su_username.setStyle(errorColor);
            messageTemp=exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearErrorForUsernameSignup() {
        Platform.runLater(() -> {
            su_errorUserName.setText("");
            su_username.setStyle(defaultColor);
        });
    }

    public void showErrorForPasswordSignup(String errorText, String exceptionMessage) throws Exception {
        Platform.runLater(() -> {
            su_errorUserPass.setText(errorText);
            su_passwordPass.setStyle(errorColor);
            su_passwordText.setStyle(errorColor);
            messageTemp=exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearErrorForPasswordSignup() {
        Platform.runLater(() -> {
            su_errorUserPass.setText("");
            su_passwordPass.setStyle(defaultColor);
            su_passwordText.setStyle(defaultColor);
        });
    }

    public void showComboBoxErrorForSignup(String errorText, String exceptionMessage) throws Exception {
        Platform.runLater(() -> {
            su_errorQuestion.setText(errorText);
            messageTemp = exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearComboBoxErrorForSignup() {
        Platform.runLater(() -> su_errorQuestion.setText(""));
    }

    public void showAnswerErrorForSignup(String errorText, String exceptionMessage) throws Exception {
        Platform.runLater(() -> {
            su_errorAnswer.setText(errorText);
            su_answer.setStyle(errorColor);
            messageTemp=exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearAnswerErrorForSignup() {
        Platform.runLater(() -> {
            su_errorAnswer.setText("");
            su_answer.setStyle(defaultColor);
        });
    }

    public void showErrorForAnswerFormat(String errorMessage, String tempMessage, String borderColor) throws Exception {
        Platform.runLater(() -> {
            forgetAnswerError.setText(errorMessage);
            forgetAnswer.setStyle("-fx-border-color: " + borderColor + ";");
            messageTemp = tempMessage;
        });
        throw new Exception(messageTemp);
    }
    public void clearErrorForAnswerFormat() {
        Platform.runLater(() -> {
            forgetAnswerError.setText("");
            forgetAnswer.setStyle(defaultColor);
            messageTemp = "";
        });
    }

    public void showErrorForQuestionFormat(String errorMessage, String tempMessage) throws Exception {
        Platform.runLater(() -> {
            forgetQuestionError.setText(errorMessage);
            messageTemp = tempMessage;
        });
        throw new Exception(tempMessage);
    }
    public void clearErrorForQuestionFormat() {
        Platform.runLater(() -> {
            forgetQuestionError.setText("");
            messageTemp = "";
        });
    }

    public void showErrorForUsernameForget(String errorMessage, String tempMessage) throws Exception {
        Platform.runLater(() -> {
            forgetUsernameError.setText(errorMessage);
            forgetUsername.setStyle(errorColor);
            messageTemp = tempMessage;
        });
        throw new Exception(tempMessage);
    }
    public void clearErrorForUsernameForget() {
        Platform.runLater(() -> {
            forgetUsernameError.setText("");
            forgetUsername.setStyle(defaultColor);
            messageTemp = "";
        });
    }

    //open user page
    private void openMainFrameUser() throws IOException {
        route.openStage(//go to home page
                References.MAIN_FRAME_USER.getImageReference(),
                cssStyle,
                References.MAIN_IMAGE.getImageReference(),
                "Talent Shopping",
                primaryStage -> {
                    mainFrame_user controller = (mainFrame_user) route.getController();
                    new mainFrame_user(controller, primaryStage, si_username.getText());
                    primaryStage.setMinWidth(700);
                    primaryStage.setMinHeight(500);
                    primaryStage.setResizable(true);
                    primaryStage.setOnCloseRequest(ignoredEvent -> {
                        ignoredEvent.consume();
                         Main.logout(primaryStage);
                    });
                    primaryStage.widthProperty().addListener((_,_,_) -> new CheckingMethod().toggleTabVisibility(primaryStage, controller));
                    stackPane.setDisable(false);
                    logInIcon.setImage(new Image(References.LOGIN_IMAGE.getImageReference()));
                    tempStage.close();
                }
        );
    }

    //open email to register
    private void openSuEmail() throws IOException {
        route.openStage(//go to email page to signup with email
                References.EMAIL_FRAME.getImageReference(),
                cssStyle,
                References.MAIN_EMAIL_IMAGE.getImageReference(),
                "Enter Email",
                stage -> {
                    eEC = (enterEmailController) route.getController();
                    new enterEmailController(aC,false, stage,eEC,nC);
                    stage.setResizable(false);
                    tempStage.close();
                    stage.setOnCloseRequest(ignoredEvent -> {
                        ignoredEvent.consume();
                        stage.close();
                        tempStage.show();
                        aC=null;
                    });
                }
        );
    }

    //open change password page to change password through email or username
    public void openChangePasswordController(String user) throws IOException {
        route.openStage(
                References.CHANGE_PASSWORD_FRAME.getImageReference(),
                cssStyle,
                References.RESET_PASSWORD_IMAGE.getImageReference(),
                "Change Password",
                stage -> {
                    ChangePasswordController cPC= (ChangePasswordController) route.getController();
                    new ChangePasswordController(stage, aC, user,cPC);
                    tempStage.close();
                    stage.setResizable(false);
                    stage.setOnCloseRequest(ignoredEvent -> {
                        ignoredEvent.consume();
                        Main.logout(stage);
                    });
                }
        );
    }
    //if user want to forget password though email and code
    @FXML
    void email_code(ActionEvent ignoredEvent) throws IOException {
        route.openStage(//open email and code page to change password through email
                References.EMAIL_FRAME.getImageReference(),
                cssStyle,
                References.MAIN_EMAIL_IMAGE.getImageReference(),
                "Change Password",
                stage -> {
                     eEC= (enterEmailController) route.getController();
                    new enterEmailController(aC, true, stage,eEC,nC);
                    tempStage.close();
                    stage.setResizable(false);
                    stage.setOnCloseRequest(ignoredEvent1 -> {
                        ignoredEvent1.consume();
                        Main.logout(stage);
                        aC = null;
                    });
                }
        );
    }

    //when user want to forget password after clicking question_answer method
    @FXML
    void proceed(ActionEvent ignoredEvent) {
        startForget();
    }
    private void startForget(){
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                handleInput();
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(_ -> handleSuccess());
        task.setOnFailed(_ -> handleFailure());
        showLoadingState();
    }
    private void handleInput() throws Exception {
        String trimmedUsername = forgetUsername.getText().trim();
        String trimmedAnswer = forgetAnswer.getText().trim();
        forgetUsername.setText(trimmedUsername);
        forgetAnswer.setText(trimmedAnswer);
        vClass.checkUsernameForget(trimmedUsername,findIndexQuestionFormat(),trimmedAnswer);
    }
    private void handleSuccess() {
        stackPane.setDisable(false);
        updateForgetIcon(References.RESET_PASSWORD_IMAGE.getImageReference());
        try {
            openChangePasswordController(forgetUsername.getText());
        } catch (IOException e) {
            nC.showNotificationSomethingWrong("Failed to Open Change Password Page");
        }
    }
    private void handleFailure() {
        Platform.runLater(() -> {
            updateForgetIcon(References.FORGET_PASSWORD_IMAGE.getImageReference());
            stackPane.setDisable(false);
            nC.showNotificationFieldEmpty(messageTemp);
        });
    }
    private void showLoadingState() {
        Platform.runLater(() -> {
            updateForgetIcon(References.CHANGE_STATUS_GIF.getImageReference());
            stackPane.setDisable(true);
        });
    }
    private void updateForgetIcon(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        forgetIcon.setImage(image);
    }

//if user click the forget password
    @FXML
    void forgetPassword(ActionEvent ignoredEvent) {
        forgetPasswordWayForm.setVisible(true);
        loginForm.setVisible(false);
        si_errorUserName.setText("");
        si_passwordPass.setText("");
        si_passwordText.setText("");
    }
//if user want to go back to forget password way
    @FXML
    void back(ActionEvent ignoredEvent) {
        forgetPasswordForm.setVisible(false);
        forgetPasswordWayForm.setVisible(true);
        forgetAnswer.setText("");
        forgetAnswerError.setText("");
        forgetQuestionError.setText("");
        forgetUsername.setText("");
        forgetUsernameError.setText("");
    }

    //if user want to forget password through username, answer, question
    @FXML
    void question_answer(ActionEvent ignoredEvent) {
        forgetPasswordForm.setVisible(true);
        forgetPasswordWayForm.setVisible(false);
    }

    //if user want to go back to login page
    @FXML
    void backToLoginForm(ActionEvent ignoredEvent) {
        setAllFormVisibility();
    }

    //after forget password go back to login page
    public void setAllFormVisibility() {
        aC.loginForm.setVisible(true);
        aC.forgetPasswordWayForm.setVisible(false);
        aC.forgetPasswordForm.setVisible(false);
    }

    //if user want to go back from enter email page to forget password page though username, answer, question
    public void setAllFormVisibilityToBack() {
        aC.loginForm.setVisible(false);
        aC.forgetPasswordWayForm.setVisible(false);
        aC.forgetPasswordForm.setVisible(true);
    }

    public String gettingFinalPass_Si(){
        if(si_passwordPass.isVisible()){
            return si_passwordPass.getText();
        }
        else{
            return si_passwordText.getText();
        }
    }
    public String gettingFinalPass_Su(){
        if(su_passwordPass.isVisible()){
            return su_passwordPass.getText();
        }
        else{
            return su_passwordText.getText();
        }
    }

    public void saveEmailPass(String email, String pass, boolean rememberStatus) {
        if (!(email == null || pass == null)) {
            pref.put("si_username", email);
            pref.put("si_passwordPass", pass);
            pref.put("si_passwordText", pass);
            rememberMe.setSelected(true);
        } else {
            pref.put("si_username", "");
            pref.put("si_passwordPass", "");
            pref.put("si_passwordText", "");
            rememberMe.setSelected(false);
        }
        pref.putBoolean("rememberMeStatus", rememberStatus); // Store remember me status
    }
    public void checked(boolean remember) {
        if (remember) {
            saveEmailPass(si_username.getText(), si_passwordPass.getText(), true);
        } else {
            saveEmailPass("", "", false);
        }
    }

    public void showStage() {
        tempStage.show();
    }
    public static void showStageInLogout() {
        Platform.runLater(() -> {
            tempStage.show();
        });
    }
    public void registerDone(){
        if(vClass.setAllInformationDatabase(su_username.getText(),su_passwordPass.getText(),eEC.getEmail(), findIndexQuestionForSignup(), su_answer.getText())){
            transAction();
            su_username.setText("");
            su_passwordPass.setText("");
            su_passwordText.setText("");
            su_answer.setText("");
            nC.showNotificationSuccessfullyCreateAccount();
        }
        else {
            nC.showNotificationSomethingWrong("Please Try again, Information is not Set");
        }
    }
    private int findIndexQuestionForSignup() {
        int selectedIndex = su_question.getSelectionModel().getSelectedIndex();
        return selectedIndex + 1;
    }
    private int findIndexQuestionFormat(){
        int selectedIndex = forgetPassCombo.getSelectionModel().getSelectedIndex();
        return selectedIndex + 1;
    }

    private void enterClick(){
        if (aC.createNewAccountButton.isVisible()){
            if(aC.forgetPasswordForm.isVisible()){
                aC.startForget();
            }
            else {
                aC.startLoginCheck();
            }
        } else if (aC.alreadyHaveAccountButton.isVisible()) {
            Platform.runLater(()->aC.startSignUpCheck());

        }

    }

}
