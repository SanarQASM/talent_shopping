package application;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.StackPane;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
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

public class accountController  implements Initializable {
    private  String messageTemp;
    private static Stage tempStage;
    private static accountController aC;
    private static final NotificationsClass nC=new NotificationsClass();
    private final Preferences pref = Preferences.userRoot().node("Rememberme");
    private static final String filePath = "D:/eclipse/IdeaProjects/E-commerce-shopping/src/main/resources/database.txt"; // Path of the file to write to
    private static enterEmailController eEC;
    private static Validation validate;
    private static final Routes route=new Routes();

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

    public accountController(){}
    public accountController(Stage stage,accountController aC) {
        tempStage = stage;
        accountController.aC = aC;
        validate =new Validation(aC);
    }
    @FXML
    void showPassAction(MouseEvent event) {
        showingPassword(showPass, si_passwordPass, si_passwordText);
    }
    @FXML
    void showPassAction1(MouseEvent event) {
        showingPassword(showPass1, su_passwordPass, su_passwordText);
    }
    private void showingPassword(ImageView showPass, PasswordField siPasswordPass, TextField siPasswordText) {
        if (showPass.getImage().getUrl().equals(Objects.requireNonNull(getClass().getResource("/image/hidePass.png")).toString())) {
            siPasswordText.setVisible(true);
            siPasswordPass.setVisible(false);
            siPasswordText.setText(siPasswordPass.getText());
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/image/showPass.png")).toString());
            showPass.setImage(image);
        } else {
            siPasswordPass.setVisible(true);
            siPasswordText.setVisible(false);
            siPasswordPass.setText(siPasswordText.getText());
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/image/hidePass.png")).toString());
            showPass.setImage(image);
        }
    }
    @FXML
    void createNewAccount(ActionEvent event) {
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
    void alreadyHaveAccount(ActionEvent event) {
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
    void si_logIn(ActionEvent event) {
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
        validate.checkingUsername_Si(si_username.getText());
    }
    private void handleLoginFailure() {
        Platform.runLater(() -> {
            stackPane.setDisable(false);
            updateLoginIcon("/image/login.png");
            nC.showNotificationSomethingWrong(messageTemp);
        });
    }
    private void handleLoginSuccess() {
        Platform.runLater(() -> {
            try {
                openMainFrameUser();
            } catch (Exception e) {
                e.printStackTrace();
                nC.showNotificationSomethingWrong(e.getMessage());
            }
        });
    }
    private void showLoadingStateForLogin() {
        Platform.runLater(() -> {
            updateLoginIcon("/image/changeToLoading.gif");
            stackPane.setDisable(true);
        });
    }
    private void updateLoginIcon(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        logInIcon.setImage(image);
    }

    @FXML
    void su_SignUp(ActionEvent event) {
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
        validate.checkingUsername_Su(su_username.getText());
        String finalPassword = gettingFinalPass_Su();
        validate.checkingPassword_Su(finalPassword);
        validate.checkComboBoxForSignup(findIndexQuestionForSignup());
        su_answer.setText(su_answer.getText().trim());
        validate.checkAnswerForSignup(su_answer.getText());
    }
    private void handleSignUpSuccess() {
        Platform.runLater(() -> {
            updateRegisterIcon("/image/register.png");
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
            updateRegisterIcon("/image/register.png");
            stackPane.setDisable(false);
            nC.showNotificationFieldEmpty(messageTemp);
        });
    }
    private void showLoadingStateForSignup() {
        Platform.runLater(() -> {
            updateRegisterIcon("/image/changeToLoading.gif");
            stackPane.setDisable(true);
        });
    }
    private void updateRegisterIcon(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        registerIcon.setImage(image);
    }

//    private void openMainFrameuser() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/mainFrame-user.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        Stage primaryStage=new Stage();
//        primaryStage.setTitle("Talent Shopping");
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fxmlFile/application.css")).toExternalForm());
//        primaryStage.setScene(scene);
//        Image image = new Image(Objects.requireNonNull(getClass().getResource("/image/mainImage.png")).toString());
//        primaryStage.getIcons().add(image);
//        mainFrame_user mF=loader.getController();
//        primaryStage.setResizable(false);
//        new mainFrame_user(mF,primaryStage,si_username.getText());
//        primaryStage.show();
//        tempStage.close();
//        primaryStage.widthProperty().addListener((observable, oldWidth, newWidth) -> {
//            checkingMethod cM=new checkingMethod();
//            cM.toggleTabVisibility(primaryStage,mF);
//        });
//        primaryStage.setMinWidth(600);
//        primaryStage.setMinHeight(400);
//        primaryStage.setOnCloseRequest(event1 -> {
//            event1.consume();
//            Main m=new Main();
//            m.logout(primaryStage);
//        });
//        stackPane.setDisable(false);
//        image = new Image(Objects.requireNonNull(getClass().getResource(
//                "/image/login.png")).toString());
//        logInIcon.setImage(image);
//    }

    public void setErrorForUsernameLogin(String uiMessage, String exceptionMessage) throws Exception {
        Platform.runLater(() -> {
            si_errorUserName.setText(uiMessage);
            si_username.setStyle("-fx-border-color: red;");
            messageTemp=exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearErrorForUsernameLogin() {
        Platform.runLater(() -> {
            si_errorUserName.setText("");
            si_username.setStyle("-fx-border-color: #0077b6;");
        });
    }
    public void setErrorForPasswordLogin(String uiMessage, String exceptionMessage) throws Exception {
        Platform.runLater(() -> {
            si_errorPassword.setText(uiMessage);
            si_passwordPass.setStyle("-fx-border-color: red;");
            si_passwordText.setStyle("-fx-border-color: red;");
            messageTemp=exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearErrorForPasswordLogin() {
        Platform.runLater(() -> {
            si_errorPassword.setText("");
            si_passwordPass.setStyle("-fx-border-color: #0077b6;");
            si_passwordText.setStyle("-fx-border-color: #0077b6;");
        });
    }
    public void showErrorForUsernameSignup(String errorText, String exceptionMessage) throws Exception {
        Platform.runLater(() -> {
            su_errorUserName.setText(errorText);
            su_username.setStyle("-fx-border-color: red;");
            messageTemp=exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearErrorForUsernameSignup() {
        Platform.runLater(() -> {
            su_errorUserName.setText("");
            su_username.setStyle("-fx-border-color: #0077b6;");
        });
    }
    public void showErrorForPasswordSignup(String errorText, String exceptionMessage) throws Exception {
        Platform.runLater(() -> {
            su_errorUserPass.setText(errorText);
            su_passwordPass.setStyle("-fx-border-color: red;");
            su_passwordText.setStyle("-fx-border-color: red;");
            messageTemp=exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearErrorForPasswordSignup() {
        Platform.runLater(() -> {
            su_errorUserPass.setText("");
            su_passwordPass.setStyle("-fx-border-color: #0077b6;");
            su_passwordText.setStyle("-fx-border-color: #0077b6;");
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
            su_answer.setStyle("-fx-border-color: red;");
            messageTemp=exceptionMessage;
        });
        throw new Exception(exceptionMessage);
    }
    public void clearAnswerErrorForSignup() {
        Platform.runLater(() -> {
            su_errorAnswer.setText("");
            su_answer.setStyle("-fx-border-color: #0077b6;");
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
            forgetAnswer.setStyle("-fx-border-color: #0077b6;");
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
            forgetUsername.setStyle("-fx-border-color: red;");
            messageTemp = tempMessage;
        });
        throw new Exception(tempMessage);
    }
    public void clearErrorForUsernameForget() {
        Platform.runLater(() -> {
            forgetUsernameError.setText("");
            forgetUsername.setStyle("-fx-border-color: #0077b6;");
            messageTemp = "";
        });
    }


    private void openMainFrameUser() throws IOException {
        route.openStage(//go to home page
                "/fxmlFile/mainFrame-user.fxml",
                "/fxmlFile/application.css",
                "/image/mainImage.png",
                "Talent Shopping",
                primaryStage -> {
                    mainFrame_user controller = (mainFrame_user) route.getController();
                    new mainFrame_user(controller, primaryStage, si_username.getText());
                    primaryStage.setMinWidth(600);
                    primaryStage.setMinHeight(400);
                    primaryStage.setResizable(true);
                    primaryStage.setOnCloseRequest(event -> {
                        event.consume();
                        new Main().logout(primaryStage);
                    });
                    primaryStage.widthProperty().addListener((observable, oldWidth, newWidth) -> {
                        new checkingMethod().toggleTabVisibility(primaryStage, controller);
                    });
                    stackPane.setDisable(false);
                    logInIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/login.png")).toString()));
                    tempStage.close();
                }
        );
    }
    private void openSuEmail() throws IOException {
        route.openStage(//go to email page to signup with email
                "/fxmlFile/enterEmailController.fxml",
                "/fxmlFile/application.css",
                "/image/emailMain.png",
                "Enter Email",
                stage -> {
                    eEC = (enterEmailController) route.getController();
                    new enterEmailController(aC,false, stage,eEC);
                    stage.setResizable(false);
                    tempStage.hide();
                    stage.setOnCloseRequest(event -> {
                        event.consume();
                        stage.close();
                        tempStage.show();
                    });
                }
        );
    }
    public void openChangePasswordController(String user) throws IOException {
        route.openStage(//open change password page to change password through email or username
                "/fxmlFile/changePassword.fxml",
                "/fxmlFile/application.css",
                "/image/reset-password.png",
                "Change Password",
                stage -> {
                    changePasswordController cPC= (changePasswordController) route.getController();
                    new changePasswordController(stage, aC, user,cPC);
                    tempStage.hide();
                    stage.setResizable(false);
                    stage.setOnCloseRequest(event -> {
                        event.consume();
                        new Main().logout(stage);
                        aC = null;
                    });
                }
        );
    }
    @FXML
    void email_code(ActionEvent event) throws IOException {
        route.openStage(//open email and code page to change password through email
                "/fxmlFile/enterEmailController.fxml",
                "/fxmlFile/application.css",
                "/image/emailMain.png",
                "Change Password",
                stage -> {
                     eEC= (enterEmailController) route.getController();
                    new enterEmailController(aC, true, stage,eEC);
                    tempStage.close();
                    stage.setResizable(false);
                    stage.setOnCloseRequest(event1 -> {
                        event1.consume();
                        new Main().logout(stage);
                        aC = null;
                    });
                }
        );
    }

//    private void openSu_email() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/enterEmailController.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fxmlFile/application.css")).toExternalForm());
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.setTitle("Enter Email");
//        eEC=loader.getController();
//        new enterEmailController(aC,stage,nC);
//        tempStage.hide();
//        Image image =new Image(Objects.requireNonNull(getClass().getResource("/image/emailMain.png")).toString());
//        stage.getIcons().add(image);
//        stage.show();
//        stage.setOnCloseRequest(event1 -> {
//            event1.consume();
//            stage.close();
//            tempStage.show();
//        });
//    }
//    private void checkUsername(String suUsername) throws Exception {
//        if (!(suUsername.isEmpty())) {
//            DatabaseConnection databaseCon=new DatabaseConnection(nC);
//            boolean strong = checkUserToHaveDigit(suUsername);
//            if (suUsername.length() >= 35) {
//                Platform.runLater(() -> {
//                    su_errorUserName.setText("Too long!!!");
//                    su_username.setStyle("-fx-border-color: red;");
//                    messageTemp="Please Enter Short Name";
//                });
//                throw new Exception(messageTemp);
//            }
//            else if(databaseCon.checkUsername(suUsername)){
//                Platform.runLater(() -> {
//                    su_errorUserName.setText("Username Is Takin!!!");
//                    su_username.setStyle("-fx-border-color: red;");
//                    messageTemp="Please Enter different Username";
//                });
//                throw new Exception(messageTemp);
//            }
//            else if (strong) {
//                Platform.runLater(() -> {
//                    su_errorUserName.setText("");
//                    su_username.setStyle("-fx-border-color:#0077b6;");
//                });
//
//            }
//
//            else {
//                Platform.runLater(() -> {
//                    su_errorUserName.setText("letter & Digit!");
//                    messageTemp="Username Must Contain Letter and digit";
//                });
//                throw new Exception(messageTemp);
//            }
//        } else {
//            Platform.runLater(() -> {
//                su_errorUserName.setText("Empty!");
//                su_username.setStyle("-fx-border-color: red;");
//                messageTemp="Username Can not Empty";
//            });
//            throw new Exception(messageTemp);
//        }
//    }

//    @FXML
//    void proceed(ActionEvent event) throws Exception {
//        Task<Void> task = new Task<>() {
//            @Override
//            protected Void call() throws Exception {
//                forgetUsername.setText(forgetUsername.getText().trim());
//                route.checkUsernameForget(forgetUsername.getText());
//                route.checkQuestionFormat(forgetPassCombo.getValue() == null);
//                forgetAnswer.setText(forgetAnswer.getText().trim());
//                route.checkAnswerFormat(forgetAnswer.getText());
//                    return null;
//              }
//            };
//            new Thread(task).start();
//            task.setOnSucceeded(_ -> {
//                stackPane.setDisable(false);
//                Image image = new Image(Objects.requireNonNull(getClass().getResource("/image/forgot-password.png")).toString());
//                forgetIcon.setImage(image);
//                        forgetPasswordForm.setVisible(false);
//                        try {//if true then check through username else check through email
//                            openChangePasswordController(forgetUsername.getText(), cM.usernameOrEmail(forgetUsername.getText()));
//                        } catch (IOException e) {
//                            nC.showNotificaitonSomethingWrong("Filed to Open Change Password Page");
//                        }
//            });
//            task.setOnFailed(_ -> {
//                Platform.runLater(() -> {
//                    Image image = new Image(Objects.requireNonNull(getClass().getResource(
//                            "/image/forgot-password.png")).toString());
//                    stackPane.setDisable(false);
//                    forgetIcon.setImage(image);
//                    nC.showNotificationFieldEmpty(messageTemp);
//                });
//            });
//            Platform.runLater(() -> {
//                Image image = new Image(Objects.requireNonNull(getClass().getResource("/image/changeToLoading.gif")).toString());
//                forgetIcon.setImage(image);
//                stackPane.setDisable(true);
//            });
//        }

    @FXML
    void proceed(ActionEvent event) throws Exception {
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
        validate.checkUsernameForget(trimmedUsername,findIndexQuestionFormat(),trimmedAnswer);
    }
    private void handleSuccess() {
        stackPane.setDisable(false);
        updateForgetIcon("/image/forgot-password.png");
        try {
            openChangePasswordController(forgetUsername.getText());
        } catch (IOException e) {
            nC.showNotificationSomethingWrong("Failed to Open Change Password Page");
        }
    }
    private void handleFailure() {
        Platform.runLater(() -> {
            updateForgetIcon("/image/forgot-password.png");
            stackPane.setDisable(false);
            nC.showNotificationFieldEmpty(messageTemp);
        });
    }
    private void showLoadingState() {
        Platform.runLater(() -> {
            updateForgetIcon("/image/changeToLoading.gif");
            stackPane.setDisable(true);
        });
    }
    private void updateForgetIcon(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        forgetIcon.setImage(image);
    }

    //    public void openChangePasswordController(String user,boolean isusername) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/changePassword.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fxmlFile/application.css")).toExternalForm());
//        stage.setScene(scene);
//        stage.setTitle("Change Password");
//        Image image =new Image(Objects.requireNonNull(getClass().getResource("/image/reset-password.png")).toString());
////        notificationsClass nCController=new notificationsClass();
////        changePasswordController cPC=new changePasswordController(Gemail,stage,nCController);
//        new changePasswordController(stage,aC,user,isusername);
//        tempStage.hide();
//        stage.getIcons().add(image);
//        stage.show();
//        stage.setOnCloseRequest(event -> {
//            event.consume();
//            Main main = new Main();
//            main.logout(stage);
//            aC=null;
//        });
//    }
    //    @FXML
//    void email_code(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/enterEmailController.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fxmlFile/application.css")).toExternalForm());
//        stage.setScene(scene);
//        stage.setTitle("Change Password");
//        Image image =new Image(Objects.requireNonNull(getClass().getResource("/image/emailMain.png")).toString());
//        new enterEmailController(aC,true,stage);
//        tempStage.close();
//        stage.getIcons().add(image);
//        stage.show();
//        stage.setOnCloseRequest(event1 -> {
//            event1.consume();
//            Main main = new Main();
//            main.logout(stage);
//            aC=null;
//        });
//    }

    @FXML
    void forgetPassword(ActionEvent event) {
        forgetPasswordWayForm.setVisible(true);
        loginForm.setVisible(false);
        si_errorUserName.setText("");
        si_passwordPass.setText("");
        si_passwordText.setText("");
    }
    @FXML
    void back(ActionEvent event) {
        forgetPasswordForm.setVisible(false);
        forgetPasswordWayForm.setVisible(true);
        forgetAnswer.setText("");
        forgetAnswerError.setText("");
        forgetQuestionError.setText("");
        forgetUsername.setText("");
        forgetUsernameError.setText("");
    }
    @FXML
    void question_answer(ActionEvent event) {
        forgetPasswordForm.setVisible(true);
        forgetPasswordWayForm.setVisible(false);
    }
    @FXML
    void backToLoginForm(ActionEvent event) {
        forgetPasswordWayForm.setVisible(false);
        loginForm.setVisible(true);
    }

    public void setAllFormVisibility() {
        aC.loginForm.setVisible(true);
        aC.forgetPasswordWayForm.setVisible(false);
        aC.forgetPasswordForm.setVisible(false);
        aC.forgetUsername.setText("");
        aC.forgetPassCombo.getSelectionModel().clearSelection();
        aC.forgetAnswer.setText("");
    }
    public void setAllFormVisabiliityToBack() {
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
        } else {
            pref.put("si_username", "");
            pref.put("si_passwordPass", "");
            pref.put("si_passwordText", "");
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
    public void registerDone() throws Exception{
        if(validate.setAllInformationIndatabase(su_username.getText(),su_passwordPass.getText(),eEC.getEmail(), findIndexQuestionForSignup(), su_answer.getText())){
            alreadyHaveAccountButton.setVisible(false);
            createNewAccountButton.setVisible(true);
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

    public void showForgetPasswordPage() {
        Platform.runLater(() -> {
            forgetPasswordForm.setVisible(true);
            loginForm.setVisible(false);
        });
    }
}
