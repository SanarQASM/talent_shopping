package application;

import java.util.Objects;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChangePasswordController {

    @FXML
    private TextField confirmationPassText;

    @FXML
    private Label confirmationPasswordError;

    @FXML
    private PasswordField confirmationPasswordPass;

    @FXML
    private Label newPasswordError;

    @FXML
    private PasswordField newPasswordPass;

    @FXML
    private TextField newPasswordText;

    @FXML
    private ImageView showPasswordInChange;

    @FXML
    private ImageView changePassowrdImage;


    private static enterEmailController eEC;
    private static String user;
    private static String messageTemp;
    private static Stage tempStage;
    private static AccountController aC;
    private static Validation validate;
    private static NotificationsClass nC;
    private static SettingController sC;
    private static ChangePasswordController cPC;
    private static final String errorColor=Colors.BORDER_ERROR_COLOR.getColor();
    private static final String defaultColor= Colors.BORDER_DEFAULT_COLOR.getColor();

    public ChangePasswordController(){}
    public ChangePasswordController(Stage tempStage, AccountController aC, String user, ChangePasswordController cPC) {//from forget password in login page
        ChangePasswordController.tempStage = tempStage;
        ChangePasswordController.aC = aC;
        ChangePasswordController.user=user;
        ChangePasswordController.cPC=cPC;
        ChangePasswordController.validate=Validation.getInstance(aC,null,cPC,null);
        nC=NotificationsClass.getInstance(tempStage);
        setEnterKeyword();
    }
    public ChangePasswordController(Stage tempStage, enterEmailController eEC, String user, ChangePasswordController cPC) {
        ChangePasswordController.tempStage = tempStage;
        ChangePasswordController.eEC = eEC;
        ChangePasswordController.user=user;
        ChangePasswordController.cPC=cPC;
        ChangePasswordController.validate=Validation.getInstance(null,eEC,cPC,null);
        nC=NotificationsClass.getInstance(tempStage);
        setEnterKeyword();
    }
    public ChangePasswordController(Stage tempStage, ChangePasswordController cPC,SettingController sC, String user) {
        ChangePasswordController.tempStage = tempStage;
        ChangePasswordController.user=user;
        ChangePasswordController.sC=sC;
        ChangePasswordController.cPC=cPC;
        ChangePasswordController.validate=Validation.getInstance(null,null,cPC,null);
        nC=NotificationsClass.getInstance(tempStage);
        setEnterKeyword();
    }

    @FXML
    void backFromChangePassword(ActionEvent ignoredEvent) {
        tempStage.close();
        if (eEC!=null){//back to email page
            returnToEmailPage();
            System.out.println("back because eEC!= null");
        }
        else if(aC!=null) {//back to change login form
            aC.showStage();
            aC.setAllFormVisibilityToBack();
            System.out.println("back because aC!= null");
            aC=null;
        }
        else {//back to setting
            returnToSettingPage();
            System.out.println("back because aC!= null");
        }
    }

    //process to change password
    @FXML
    void changePassword(ActionEvent ignoredEvent) {
        startChangePassword();
    }
    private void startChangePassword() {
        Task<Void> changePasswordTask = createChangePasswordTask();
        changePasswordTask.setOnSucceeded(_ -> onTaskSuccess());
        changePasswordTask.setOnFailed(_ -> onTaskFailure());
        new Thread(changePasswordTask).start();
        setChangePasswordImage(References.CHANGE_STATUS_GIF.getImageReference());

    }
    private Task<Void> createChangePasswordTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                validate.checkNewPassword(getNewPassword(),getConfirmPassword(),user);
                return null;
            }
        };
    }
    private void onTaskSuccess() {
        Platform.runLater(() -> {
            setChangePasswordImage(References.RESET_PASSWORD_IMAGE.getImageReference());
            tempStage.close();
            if (eEC != null) {//return to email page
                returnToEmailPage();
            }
            else if (aC != null) {
                returnToLoginPage();//return to login page
            }  else {
                returnToSettingPage();
            }
        });
    }
    private void onTaskFailure() {
        Platform.runLater(() -> {
            setChangePasswordImage(References.RESET_PASSWORD_IMAGE.getImageReference());
            nC.showNotificationSomethingWrong(messageTemp);
        });
    }
    private void setChangePasswordImage(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        changePassowrdImage.setImage(image);
    }
    private void returnToLoginPage() {
        aC.showStage();
        nC.showNotificationPasswordChangeSuccessfully();
        aC.setAllFormVisibility();
        aC = null;  // Release the controller reference after use
    }
    private void returnToEmailPage() {
        eEC.showStage();
        eEC=null;
    }
    private void returnToSettingPage(){
        sC.showStage();
        sC=null;
    }
    private String getNewPassword(){
        if(newPasswordPass.isVisible()){
            newPasswordPass.setText(newPasswordPass.getText().trim());
            return newPasswordPass.getText();
        }
        else{
            newPasswordText.setText(newPasswordText.getText().trim());
            return newPasswordText.getText();
        }
    }
    private String getConfirmPassword(){
        if(confirmationPasswordPass.isVisible()){
            confirmationPasswordPass.setText(confirmationPasswordPass.getText().trim());
            return confirmationPasswordPass.getText();
        }
        else{
            confirmationPassText.setText(confirmationPassText.getText().trim());
            return confirmationPassText.getText();
        }
    }

    public void showPasswordErrorForNew(String errorText, String message) throws Exception {
        Platform.runLater(() -> {
            newPasswordError.setText(errorText);
            newPasswordPass.setStyle(errorColor);
            newPasswordText.setStyle(errorColor);
        });
        messageTemp = message;
        throw new Exception(messageTemp);
    }
    public void clearPasswordErrorStylesForNew() {
        Platform.runLater(() -> {
            newPasswordError.setText("");
            newPasswordPass.setStyle(defaultColor);
            newPasswordText.setStyle(defaultColor);
            messageTemp="";
        });
    }
    public void showPasswordErrorForConfirmation(String errorText, String message) throws Exception {
        Platform.runLater(() -> {
            confirmationPasswordError.setText(errorText);
            confirmationPasswordPass.setStyle(errorColor);
            confirmationPassText.setStyle(errorColor);
        });
        messageTemp = message;
        throw new Exception(messageTemp);
    }
    public void clearPasswordErrorStylesForConfirmation() {
        Platform.runLater(() -> {
            confirmationPasswordError.setText("");
            confirmationPasswordPass.setStyle(defaultColor);
            confirmationPassText.setStyle(defaultColor);
            messageTemp="";
        });
    }

    @FXML
    void newShowPass(MouseEvent ignoredEvent) {
        if (showPasswordInChange.getImage().getUrl().equals(Objects.requireNonNull(getClass().getResource(References.HIDE_PASS_IMAGE.getImageReference())).toString())) {
            newPasswordPass.setVisible(false);
            newPasswordText.setVisible(true);
            confirmationPasswordPass.setVisible(false);
            confirmationPassText.setVisible(true);
            newPasswordText.setText(newPasswordPass.getText());
            confirmationPassText.setText(confirmationPasswordPass.getText());
            Image image = new Image(Objects.requireNonNull(getClass().getResource(References.SHOW_PASS_IMAGE.getImageReference())).toString());
            showPasswordInChange.setImage(image);
        } else {
            newPasswordPass.setVisible(true);
            newPasswordText.setVisible(false);
            confirmationPasswordPass.setVisible(true);
            confirmationPassText.setVisible(false);
            confirmationPasswordPass.setText(confirmationPassText.getText());
            newPasswordPass.setText(newPasswordText.getText());
            Image image = new Image(Objects.requireNonNull(getClass().getResource(References.HIDE_PASS_IMAGE.getImageReference())).toString());
            showPasswordInChange.setImage(image);
        }
    }
    private void setEnterKeyword(){
        tempStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                startChangePassword();
            }
        });
    }
}
