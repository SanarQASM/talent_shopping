//package application;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.Alert.AlertType;
//import javafx.stage.StageStyle;
//import java.util.Optional;
//
//public class Main extends Application {
//    private static final NotificationsClass nC=new NotificationsClass();
//    long start;
//    @Override
//    public void start(Stage primaryStage) {
//        start = System.nanoTime();
//        try {
//            Routes route= new Routes();
//            route.openStage(
//                    "/fxmlFile/account.fxml",
//                    "/fxmlFile/application.css",
//                    "/image/mainImage.png",
//                    "Talent Shopping",
//                    stage -> {
//                        accountController aC = (accountController) route.getController();
//                        new accountController(stage, aC);
//                        stage.setResizable(false);
//                        stage.setOnCloseRequest(event -> {
//                            event.consume();
//                            logout(stage);
//                        });
//                    }
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("faild to open acount");
//                nC.showNotificationSomethingWrong("Failed to open Login/signup page");
//        }
//    }
//
//    public static void main(String[] args){
//        launch(args);
//    }
//
//    public void logout(Stage stage) {
//        Alert alert = new Alert(AlertType.CONFIRMATION);
//        alert.setTitle("Logout");
//        alert.setHeaderText("You're about to logout!");
//        alert.setContentText("Do you want to save before exiting?");
//        if (!stage.isShowing()) {
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.initStyle(StageStyle.UNDECORATED);
//        }
//        alert.initOwner(stage);
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            long Duration = (System.nanoTime() - start) / 1_000_000_000;
//            System.out.println("The duration to start program: " + Duration);
//            stage.close();
//        }
//    }
//
//}
package application;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Optional;

public class Main extends Application {
    private static NotificationsClass nC;
    private static long start;
    @Override
    public void start(Stage primaryStage) {
        start = System.nanoTime();
            nC = NotificationsClass.getInstance(primaryStage);
            try {
                Routes route = new Routes();
                route.openStage(
                        References.ACCOUNT_FRAME.getImageReference(),
                        References.CSS_STYLE.getImageReference(),
                        References.MAIN_IMAGE.getImageReference(),
                        "Talent Shopping",
                        stage -> {
                            AccountController aC = (AccountController) route.getController();
                            new AccountController(stage, aC,nC);
                            stage.setResizable(false);
                            stage.setOnCloseRequest(event -> {
                                event.consume();
                                logout(stage);
                            });
                        }
                );
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to open account page");
                nC.showNotificationSomethingWrong("Failed to open Login/Signup page");
            }

    }
    public static void main(String[] args) {
        launch(args);
    }
    public static void logout(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?");
        if (!stage.isShowing()) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
        }
        alert.initOwner(stage);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            long duration = (System.nanoTime() - start) / 1_000_000_000;
            System.out.println("The duration to start program: " + duration + " seconds");
            stage.close();
        }
    }
}


