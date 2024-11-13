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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class changePasswordController{

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
    private static accountController aC;
//    private static final NotificationsClass nC=NotificationsClass.getInstance();
    private static final checkingMethod cM = new checkingMethod();
    private static Validation validate;
    private static changePasswordController cPC;
    private static final DatabaseConnection dB=new DatabaseConnection();

    public changePasswordController(){}
    public changePasswordController(Stage tempStage, accountController aC,String user,changePasswordController cPC) {//from forget password in login page
        changePasswordController.tempStage = tempStage;
        changePasswordController.aC = aC;
        changePasswordController.user=user;
        changePasswordController.cPC=cPC;
        changePasswordController.validate=new Validation(aC,cPC);
    }
    public changePasswordController(Stage tempStage,enterEmailController eEC, String user,changePasswordController cPC) {
        changePasswordController.tempStage = tempStage;
        changePasswordController.eEC = eEC;
        changePasswordController.user=user;
        changePasswordController.cPC=cPC;
        changePasswordController.validate=new Validation(eEC,cPC);
    }

    @FXML
    void backFromChangePassword(ActionEvent event) {
        tempStage.close();
        if (eEC!=null){//back to email page
            eEC.showStage();
            System.out.println("back because eEC!= null");
        }
        else if(aC!=null) {//back to change login form
            aC.showStage();
            aC.setAllFormVisibilityToBack();
            System.out.println("back because aC!= null");
        }

        else {//back to setting
//            sC.showStage();
        }
    }

    //process to change password
    @FXML
    void changePassword(ActionEvent event) {
        Task<Void> changePasswordTask = createChangePasswordTask();
        changePasswordTask.setOnSucceeded(_ -> onTaskSuccess());
        changePasswordTask.setOnFailed(_ -> onTaskFailure());
        new Thread(changePasswordTask).start();
        setChangePasswordImage("/image/changeToLoading.gif");
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
            setChangePasswordImage("/image/reset-password.png");
            closeTemporaryStage();
            tempStage.close();
            if (eEC != null) {//return to email page
                returnToLoginPage();
            }
            else if (aC != null) {
                returnToLoginPage();//return to login page
            }  else {
                // back to setting page
            }
        });
    }
    private void onTaskFailure() {
        Platform.runLater(() -> {
            setChangePasswordImage("/image/reset-password.png");
//            nC.showNotificationSomethingWrong(messageTemp);
        });
    }
    private void setChangePasswordImage(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        changePassowrdImage.setImage(image);
    }
    private void closeTemporaryStage() {
        if (tempStage != null) {
            tempStage.close();
        }
    }
    private void returnToLoginPage() {
        aC.showStage();
//        nC.showNotificationPasswordChangeSuccessfully();
        aC.setAllFormVisibility();
        aC = null;  // Release the controller reference after use
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
            newPasswordPass.setStyle("-fx-border-color: red;");
            newPasswordText.setStyle("-fx-border-color: red;");
        });
        messageTemp = message;
        throw new Exception(messageTemp);
    }
    public void clearPasswordErrorStylesForNew() {
        Platform.runLater(() -> {
            newPasswordError.setText("");
            newPasswordPass.setStyle("-fx-border-color: #0077b6;");
            newPasswordText.setStyle("-fx-border-color: #0077b6;");
            messageTemp="";
        });
    }
    public void showPasswordErrorForConfirmation(String errorText, String message) throws Exception {
        Platform.runLater(() -> {
            confirmationPasswordError.setText(errorText);
            confirmationPasswordPass.setStyle("-fx-border-color: red;");
            confirmationPassText.setStyle("-fx-border-color: red;");
        });
        messageTemp = message;
        throw new Exception(messageTemp);
    }
    public void clearPasswordErrorStylesForConfirmation() {
        Platform.runLater(() -> {
            confirmationPasswordError.setText("");
            confirmationPasswordPass.setStyle("-fx-border-color: #0077b6;");
            confirmationPassText.setStyle("-fx-border-color: #0077b6;");
            messageTemp="";
        });
    }


    @FXML
    void newShowPass(MouseEvent event) {
        if (showPasswordInChange.getImage().getUrl().equals(getClass().getResource("/image/hidePass.png").toString())) {
            newPasswordPass.setVisible(false);
            newPasswordText.setVisible(true);
            confirmationPasswordPass.setVisible(false);
            confirmationPassText.setVisible(true);
            newPasswordText.setText(newPasswordPass.getText());
            confirmationPassText.setText(confirmationPasswordPass.getText());
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/image/showPass.png")).toString());
            showPasswordInChange.setImage(image);
        } else {
            newPasswordPass.setVisible(true);
            newPasswordText.setVisible(false);
            confirmationPasswordPass.setVisible(true);
            confirmationPassText.setVisible(false);
            confirmationPasswordPass.setText(confirmationPassText.getText());
            newPasswordPass.setText(newPasswordText.getText());
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/image/hidePass.png")).toString());
            showPasswordInChange.setImage(image);
        }
    }

}
