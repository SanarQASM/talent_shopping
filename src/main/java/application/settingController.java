package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.IOException;

public class settingController {
    private static settingController sC;
    private static mainFrame_user mF;
    private static Stage primaryStage;
    private static final NotificationsClass nC=new NotificationsClass();
    private static final Routes route=new Routes();
    public settingController(settingController sC, mainFrame_user mF, Stage primaryStage) {
        settingController.sC=sC;
        settingController.mF=mF;
        settingController.primaryStage=primaryStage;
    }

    @FXML
    void backToMainController(ActionEvent event) {
        mF.showStage();
        primaryStage.close();
    }

    //open change Email page
    @FXML
    void changeEmail(ActionEvent event) throws IOException {
        route.openStage(
                "/fxmlFile/enterEmailController.fxml",
                "/fxmlFile/application.css",
                "/image/emailMain.png",
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

    @FXML
    void changePassword(ActionEvent event) {

    }

    @FXML
    void changeUsername(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    public void showStage() {
        primaryStage.show();
    }

}
