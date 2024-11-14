package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.IOException;

public class SettingController {
    private static SettingController sC;
    private static mainFrame_user mF;
    private static Stage primaryStage;
    private static NotificationsClass nC;
    private static final Routes route=Routes.getInstance();
    private static final String cssStyle = References.CSS_STYLE.getImageReference();

    public SettingController(SettingController sC, mainFrame_user mF, Stage primaryStage) {
        SettingController.sC=sC;
        SettingController.mF=mF;
        SettingController.primaryStage=primaryStage;
        nC=NotificationsClass.getInstance(primaryStage);
    }

    @FXML
    void backToMainController(ActionEvent ignoredEvent) {
        mF.showStage();
        primaryStage.close();
    }

    //open change Email page
    @FXML
    void changeEmail(ActionEvent ignoredEvent) throws IOException {
        route.openStage(
                References.EMAIL_FRAME.getImageReference(),
                cssStyle,
                References.MAIN_EMAIL_IMAGE.getImageReference(),
                "Enter Email",
                stage -> {
                    try {
                        enterEmailController eEC = (enterEmailController) route.getController();
                        new enterEmailController(eEC, stage, sC, mF.getUsername());
                        primaryStage.close();
                        stage.setResizable(false);
                        stage.setOnCloseRequest(event1 -> {
                            event1.consume();
                            stage.close();
                            primaryStage.show();
                        });
                    } catch (Exception e) {
                        nC.showNotificationSomethingWrong("Failed to load email page");
                    }
                }
        );
    }
    //open change password page
    @FXML
    void changePassword(ActionEvent ignoredEvent) throws IOException {
        route.openStage(
                References.CHANGE_PASSWORD_FRAME.getImageReference(),
                cssStyle,
                References.CHANGE_PASSWORD_IMAGE.getImageReference(),
                "Enter Email",
                stage -> {
                    try {
                        ChangeUsernameController cUC = (ChangeUsernameController) route.getController();
                        new ChangeUsernameController(stage,cUC, sC, mF.getUsername());
                        primaryStage.close();
                        stage.setResizable(false);
                        stage.setOnCloseRequest(event1 -> {
                            event1.consume();
                            stage.close();
                            primaryStage.show();
                        });
                    } catch (Exception e) {
                        nC.showNotificationSomethingWrong("Failed to load change username page");
                    }
                }
        );
    }
    //open change username
    @FXML
    void changeUsername(ActionEvent ignoredEvent) throws IOException {
        route.openStage(
                References.CHANGE_USERNAME_FRAME.getImageReference(),
                cssStyle,
                References.CHANGE_USERNAME_IMAGE.getImageReference(),
                "Enter Email",
                stage -> {
                    try {
                        ChangePasswordController cPC = (ChangePasswordController) route.getController();
                        new ChangePasswordController(stage,cPC, sC, mF.getUsername());
                        primaryStage.close();
                        stage.setResizable(false);
                        stage.setOnCloseRequest(event1 -> {
                            event1.consume();
                            stage.close();
                            primaryStage.show();
                        });
                    } catch (Exception e) {
                        nC.showNotificationSomethingWrong("Failed to load change password page");
                    }
                }
        );
    }
    //logout from account
    @FXML
    void logout(ActionEvent ignoredEvent) {
        primaryStage.close();
        AccountController.showStageInLogout();
    }

    public void showStage() {
        primaryStage.show();
    }

}
