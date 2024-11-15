package application;

import java.io.IOException;
import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class enterEmailController {

    private static Stage tempStage;
    private static AccountController aC;
    private static enterEmailController eEC;
    private static NotificationsClass nC;
    private static SettingController sC;
    private static final Routes route=Routes.getInstance();
    private static Validation validate;
    private static String username;
    private int remainingSeconds = 60;
    private static int countResend = 1;
    private static String messageTemp;
    private static boolean forgetPage;
    private static final String errorColor=Colors.BORDER_ERROR_COLOR.getColor();
    private static final String defaultColor=Colors.BORDER_DEFAULT_COLOR.getColor();
    private static final String cssStyle = References.CSS_STYLE.getImageReference();

    @FXML
    private TextField code;

    @FXML
    private Label codeError;

    @FXML
    private TextField email;

    @FXML
    private Label emailError;

    @FXML
    private Button processButtonToSendCode;

    @FXML
    private Button sendCodeButton;

    @FXML
    private AnchorPane emailCodeFrame;

    @FXML
    private ImageView emailCodeIcon;

    @FXML
    private Label time;

    public enterEmailController(AccountController aC, boolean forgetPage, Stage stage, enterEmailController eEC,NotificationsClass nC){//open from signup or change password
        enterEmailController.aC = aC;
        enterEmailController.forgetPage = forgetPage;//if true then it is in forget password through email else it is to register(signup)
        tempStage = stage;
        enterEmailController.eEC = eEC;
        enterEmailController.nC=nC;
        setEnterKeyword();
        validate = Validation.getInstance(aC,enterEmailController.eEC,null,null);
    }
    public enterEmailController(){}
    public enterEmailController(enterEmailController eEC, Stage stage, SettingController sC, String username) {//open from setting
        tempStage=stage;
        enterEmailController.eEC=eEC;
        enterEmailController.sC=sC;
        enterEmailController.username=username;
        nC= NotificationsClass.getInstance(tempStage);
        setEnterKeyword();
        validate = Validation.getInstance(null,enterEmailController.eEC,null,null);
    }

    //back to recent page
    @FXML
    void back(ActionEvent ignoredEvent) {
        tempStage.close();
        if(aC!=null){
            if(forgetPage) {//back to login page
                aC.showStage();
                aC = null;
            }else {//back to signup page
                aC.showStage();
                aC.setAllFormVisibilityToBack();
                aC=null;
            }
        }
        else{
            sC.showStage();
            sC=null;
        }
    }

    //code verification according to user task
    @FXML
    void process(ActionEvent ignoredEvent) {
        startProcess();
    }
    private void startProcess() {
        Task<Void> validationTask = createValidationTask();
        setTaskHandlers(validationTask);
        startTask(validationTask);
        updateUI(References.CHANGE_STATUS_GIF.getImageReference(),false);
    }
    private Task<Void> createValidationTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                code.setText(code.getText().trim());
                validate.checkCodeInEnterEmailController(code.getText());
                return null;
            }
        };
    }

    private void setTaskHandlers(Task<Void> task) {
        task.setOnFailed(_ -> Platform.runLater(() -> {
            updateUI(References.FORGET_EMAIL_IMAGE.getImageReference(),true);
            nC.showNotificationSomethingWrong(messageTemp);
        }));
        task.setOnSucceeded(_ -> Platform.runLater(() -> {
            updateUI(References.FORGET_EMAIL_IMAGE.getImageReference(),true);
            handleSuccessfulValidation();
        }));
    }
    private void startTask(Task<Void> task) {
        Platform.runLater(()->{
            Image image = new Image(Objects.requireNonNull(getClass().getResource(References.CHANGE_STATUS_GIF.getImageReference())).toString());
            emailCodeIcon.setImage(image);
            new Thread(task).start();
        });
    }
    private void updateUI(String image,boolean disability){
        Image imageToChange = new Image(image);
        emailCodeIcon.setImage(imageToChange);
        emailCodeFrame.setDisable(disability);
    }
    private void handleSuccessfulValidation() {
        try {
            if (aC != null) {
                if (forgetPage) {
                    tempStage.close();
                    openChangePassword(email.getText());
                } else {
                    navigateToSignup();
                }
            } else {
                navigateToSettings();
            }
        } catch (Exception e) {
            nC.showNotificationSomethingWrong(e.getMessage());
        }
    }
    private void navigateToSignup(){
        tempStage.close();
        aC.showStage();
        aC.registerDone();
        aC = null;
    }
    private void navigateToSettings() {
        if(validate.setNewEmailToOldEmail(username, email.getText())){
            sC.showStage();
            nC.showNotificationNewEmailSetSuccessfully();
            tempStage.close();
        }else{
            nC.showNotificationSomethingWrong("Can not Set New Email");
        }
    }
    private void openChangePassword(String user) throws IOException {//open change password page
        route.openStage(
                String.valueOf(References.CHANGE_PASSWORD_FRAME),
                cssStyle,
                String.valueOf(References.RESET_PASSWORD_IMAGE),
                "Change Password",
                stage -> {
                    ChangePasswordController cPC= (ChangePasswordController) route.getController();
                    new ChangePasswordController(stage, eEC, user,cPC);
                    tempStage.close();
                    stage.setResizable(false);
                    stage.setOnCloseRequest(event -> {
                        event.consume();
                        tempStage.show();
                        stage.show();
                    });
                }
        );
    }
    public void handleErrorForCode(String errorText, String errorMessage, String style) throws Exception {
        Platform.runLater(() -> {
            codeError.setText(errorText);
            code.setStyle(style);
            messageTemp = errorMessage;
        });
        throw new Exception(errorMessage);
    }
    public void clearErrorForCode()  {
        Platform.runLater(() -> {
            codeError.setText("");
            code.setStyle(defaultColor);
            messageTemp = "";
        });
    }

    //send code for user email
    @FXML
    void sendCode(ActionEvent ignoredEvent) {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    processSendCode();
                    return null;
                }
            };
            task.setOnFailed(_ -> handleSendCodeFailure());
            task.setOnSucceeded(_ -> handleSendCodeSuccess());
            showLoadingState(task);
    }
    private void processSendCode() throws Exception {
        code.setDisable(true);
        processButtonToSendCode.setDisable(true);
        email.setText(email.getText().trim());
        checkEmailToHave(email.getText());
    }
    private void handleSendCodeSuccess() {
        Platform.runLater(() -> {
           updateEmailCodeIcon(References.FORGET_EMAIL_IMAGE.getImageReference(),false);
           nC.showNotificationCheckYourEmail();
        });
    }
    private void handleSendCodeFailure() {
        Platform.runLater(() -> {
            updateEmailCodeIcon(References.FORGET_EMAIL_IMAGE.getImageReference(),false);
            nC.showNotificationSomethingWrong(messageTemp);
        });
    }
    private void showLoadingState(Task<Void> task) {
        new Thread(task).start();
        Platform.runLater(() -> updateEmailCodeIcon(References.CHANGE_STATUS_GIF.getImageReference(), true));
    }
    private void updateEmailCodeIcon(String imagePath,boolean disability) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        emailCodeFrame.setDisable(disability);
        emailCodeIcon.setImage(image);
    }

    //checking email as input and in database
    private void checkEmailToHave(String Gmail) throws Exception {
        validate.checkEmailInEnterEmailController(Gmail);
        if (aC != null) {
            handleAccountControllerCase(Gmail);
        } else {
            handleSettingsCase(Gmail);
        }
    }
    private void handleAccountControllerCase(String emailText) throws Exception {
        boolean emailExists = validate.checkEmailInDatabase(emailText);
        if (forgetPage) { // Forget password case
            if (emailExists) {
                setEmailSuccess();
                sendEmail();
            } else {
                setEmailError("Not Found!", "Please enter a correct email.");
            }
        } else { // Sign-up case
            if (!emailExists) {
                setEmailSuccess();
                sendEmail();
            } else {
                setEmailError("Already Have!", "Please enter a different email.");
            }
        }
    }

    private void handleSettingsCase(String emailText) throws Exception {
        if (!validate.checkEmailInDatabase(emailText)) {
            setEmailSuccess();
            sendEmail();
        } else {
            setEmailError("Not Found!!!", "Please enter a correct email!");
        }
    }
    public void setEmailError(String errorMessage, String tempMessage) throws Exception {
        Platform.runLater(() -> {
            emailError.setText(errorMessage);
            email.setStyle(errorColor);
            messageTemp = tempMessage;
        });
        throw new Exception(messageTemp);
    }
    public void setEmailSuccess() {
        Platform.runLater(() -> {
            emailError.setText("");
            email.setStyle(defaultColor);
            messageTemp = "";
        });
    }

    //send code process
    private void sendEmail() throws Exception {
        resetEmailUI();
        emailSender emailSender = new emailSender();
        int result = emailSender.startSendMail(email.getText());
        switch (result) {
            case 1 -> handleNoInternetError();
            case 2 -> handleEmailFailure();
            default -> enableCodeEntry();
        }
    }
    private void resetEmailUI() {
        Platform.runLater(() -> {
            emailError.setText("");
            email.setStyle(defaultColor);
        });
    }
    private void handleNoInternetError() throws Exception {
        Platform.runLater(() -> {
            nC.showNotificationNoInternet();
            messageTemp = "Internet is required to send code!";
        });
        throw new Exception(messageTemp);
    }
    private void handleEmailFailure() throws Exception {
        Platform.runLater(() -> messageTemp = "Failed to send to your inbox!");
        throw new Exception(messageTemp);
    }
    private void enableCodeEntry() {
        Platform.runLater(() -> {
            code.setDisable(false);
            processButtonToSendCode.setDisable(false);
            sendCodeButton.setDisable(true);
            startTime();
        });
    }
    private void startTime() {
        countResend++;
        if (countResend <= 3) {
            remainingSeconds=60;
            final Timeline timeline = new Timeline();
            time.setText("01:00");
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), _ -> {
                remainingSeconds--;
                int minutes = remainingSeconds / 60;
                int seconds = remainingSeconds % 60;
                String formattedTime = String.format("%02d:%02d", minutes, seconds);
                time.setText(formattedTime);
                if (remainingSeconds == 0) {
                    timeline.stop();
                    sendCodeButton.setDisable(false);
                    code.setDisable(true);
                    processButtonToSendCode.setDisable(true);
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    //other method
    public String getEmail(){
        return email.getText();
    }
    public void showStage() {
        tempStage.show();
    }
    private void setEnterKeyword(){
        tempStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if(!eEC.processButtonToSendCode.isDisable()){
                    startProcess();
                }
            }
        });
    }
}
