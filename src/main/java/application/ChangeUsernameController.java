package application;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class ChangeUsernameController {
    private static String messageTemp;
    private static Stage tempStage;
    private static NotificationsClass nC;
    private static SettingController sC;
    private static ChangeUsernameController cUC;
    private static String username;
    private static Validation validate;
    private static final String errorColor=Colors.BORDER_ERROR_COLOR.getColor();
    private static final String defaultColor=Colors.BORDER_DEFAULT_COLOR.getColor();

    @FXML
    private TextField changeUsername;

    @FXML
    private Label changeUsernameError;

    @FXML
    private ImageView changeUsernameImage;

    public ChangeUsernameController(){}

    public ChangeUsernameController(Stage stage, ChangeUsernameController cUC, SettingController sC, String username) {
        ChangeUsernameController.tempStage=stage;
        ChangeUsernameController.cUC =cUC;
        ChangeUsernameController.username =username;
        ChangeUsernameController.validate=Validation.getInstance(null,null,null,cUC);
        ChangeUsernameController.nC=NotificationsClass.getInstance(tempStage);
        setEnterKeyword();
        ChangeUsernameController.sC =sC;
    }
    @FXML
    void backToSettingForm(ActionEvent ignoredEvent) {
        returnToSettingPage();
    }
    @FXML
    void changeUsername(ActionEvent ignoredEvent){
        startChangeUsername();
    }
    private void startChangeUsername(){
        Task<Void> changeUsernameTask = createChangeUsernameTask();
        changeUsernameTask.setOnSucceeded(_ -> onTaskSuccess());
        changeUsernameTask.setOnFailed(_ -> onTaskFailure());
        new Thread(changeUsernameTask).start();
        setChangeUsernameImage(References.CHANGE_STATUS_GIF.getImageReference());
    }
    private Task<Void> createChangeUsernameTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                changeUsername.setText(changeUsername.getText().trim());
                validate.checkNewUsername(changeUsername.getText(),username);
                return null;
            }
        };
    }
    private void onTaskSuccess() {
        Platform.runLater(() -> {
            setChangeUsernameImage(References.CHANGE_USERNAME_IMAGE.getImageReference());
            nC.showNotificationNewUsernameSetSuccessfully();
            returnToSettingPage();
            updateUsernameInMainFrame(changeUsername.getText());
        });
    }
    private void onTaskFailure() {
        Platform.runLater(() -> {
            setChangeUsernameImage(References.CHANGE_USERNAME_IMAGE.getImageReference());
            nC.showNotificationSomethingWrong(messageTemp);
        });
    }
    private void setChangeUsernameImage(String imagePath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        changeUsernameImage.setImage(image);
    }
    public void showUsernameError(String errorText, String message) throws Exception {
        Platform.runLater(() -> {
            changeUsernameError.setText(errorText);
            changeUsername.setStyle(errorColor);
        });
        messageTemp = message;
        throw new Exception(messageTemp);
    }
    public void clearUsernameError() {
        Platform.runLater(() -> {
            changeUsernameError.setText("");
            changeUsername.setStyle(defaultColor);
            messageTemp="";
        });
    }
    private void setEnterKeyword(){
        tempStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                startChangeUsername();
            }
        });
    }
    private void returnToSettingPage(){
        tempStage.close();
        sC.showStage();
        sC=null;
    }
    private void updateUsernameInMainFrame(String username) {
        mainFrame_user.setNewUsername(username);
    }

}
