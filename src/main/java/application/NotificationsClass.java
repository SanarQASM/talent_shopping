package application;

import javafx.application.Platform;
import org.controlsfx.control.Notifications;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.stage.Stage;

import java.util.Objects;

public class NotificationsClass {
    private static NotificationsClass nC;
    private final Stage ownerStage;

    private NotificationsClass(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    private void showNotification(String title, String message, String imagePath) {
        Platform.runLater(() -> {
            Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
            Notifications notification = Notifications.create()
                    .title(title)
                    .text(message)
                    .owner(ownerStage)
                    .darkStyle()
                    .hideAfter(Duration.seconds(5))
                    .graphic(new ImageView(image));
            notification.show();
        });
    }
    public static NotificationsClass getInstance(Stage ownerStage) {
        if(nC==null){
            nC = new NotificationsClass(ownerStage);
        }
        return nC;
    }

    public void showNotificationSomethingWrong(String message) {
        showNotification("Opps, try again!", message,References.SOMETHING_WRONG_IMAGE.getImageReference());
    }

    public void showNotificationNoInternet() {
        showNotification("No internet Access", "Please check internet before trying", References.NO_WIFI_IMAGE.getImageReference());
    }

    public void showNotificationCheckYourEmail() {
        showNotification("Email Sent Successfully", "Check your email",References.EMAIL_SUCCESSFULLY_SEND.getImageReference());
    }

    public void showNotificationTooManyTry() {
        showNotification("Too many tries", "Try again later", References.TOO_MANY_TRY.getImageReference());
    }

    public void showNotificationSuccessChangeEmail() {
        showNotification("Email Changed Successfully", "You can use your new email!", References.FORGET_EMAIL_IMAGE.getImageReference());
    }

    public void showNotificationPasswordChangeSuccessfully() {
        showNotification("Password Changed Successfully", "You can use the new password now", References.CHANGE_PASSWORD_IMAGE.getImageReference());
    }

    public void showNotificationNewEmailSetSuccessfully() {
        showNotification("Email Changed Successfully", "You can use the new email now", References.CHANGE_EMAIL_IMAGE.getImageReference());
    }

    public void showNotificationNewUsernameSetSuccessfully() {
        showNotification("Username Changed Successfully", "You can use the new username now", References.CHANGE_USERNAME_IMAGE.getImageReference());
    }

    public void showNotificationSendToReview() {
        showNotification("Book Added Successfully", "Please wait for review. We will display it in 'Additional Books' after review",References.REGISTER_IMAGE.getImageReference());
    }

    public void showNotificationFieldEmpty(String message) {
        showNotification("The Information is Incorrect", message, References.EMPTY_IMAGE.getImageReference());
    }

    public void showNotificationInvalidType() {
        showNotification("File Type Not Allowed", "Please verify the file type and try again", References.INVALID_IMAGE.getImageReference());
    }

    public void showNotificationEnterCorrectInformation() {
        showNotification("Wrong Information", "Please check the information",References.INCORRECT_IMAGE.getImageReference());
    }

    public void showNotificationEnterEmail() {
        showNotification("Email Address", "Add your email address", References.MAIN_EMAIL_IMAGE.getImageReference());
    }

    public void showNotificationEnterNewPassword() {
        showNotification("Password", "Add a new and different password", References.CHANGE_PASSWORD_IMAGE.getImageReference());
    }

    public void showNotificationEnterNewUsername() {
        showNotification("Username", "Add a new and different username", References.CHANGE_USERNAME_IMAGE.getImageReference());
    }

    public void showNotificationPleaseWait() {
        showNotification("Please Wait", "It may take some time", References.REGISTER_IMAGE.getImageReference());
    }

    public void showNotificationSuccessfullyCreateAccount() {
        showNotification("Account Created Successfully", "Now you can log in",References.ACCOUNT_CREATE_SUCCESSFULLY.getImageReference());
    }
}
