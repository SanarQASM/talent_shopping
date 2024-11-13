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
            System.out.println("nc is created");
            nC = new NotificationsClass(ownerStage);
        }
        return nC;
    }

    public void showNotificationSomethingWrong(String message) {
        showNotification("Opps, try again!", message, "/image/somethingWrong.png");
    }

    public void showNotificationNoInternet() {
        showNotification("No internet Access", "Please check internet before trying", "/image/no-wifi.png");
    }

    public void showNotificationCheckYourEmail() {
        showNotification("Email Sent Successfully", "Check your email", "/image/EmailSentSuccessfully.png");
    }

    public void showNotificationTooManyTry() {
        showNotification("Too many tries", "Try again later", "/image/tooManyTry.png");
    }

    public void showNotificationSuccessChangeEmail() {
        showNotification("Email Changed Successfully", "You can use your new email!", "/image/forgetEmail.png");
    }

    public void showNotificationPasswordChangeSuccessfully() {
        showNotification("Password Changed Successfully", "You can use the new password now", "/image/passwordChange.png");
    }

    public void showNotificationNewEmailSetSuccessfully() {
        showNotification("Email Changed Successfully", "You can use the new email now", "/image/changeEmail.png");
    }

    public void showNotificationNewUsernameSetSuccessfully() {
        showNotification("Username Changed Successfully", "You can use the new username now", "/image/changeUsername.png");
    }

    public void showNotificationSendToReview() {
        showNotification("Book Added Successfully", "Please wait for review. We will display it in 'Additional Books' after review", "/image/review.png");
    }

    public void showNotificationFieldEmpty(String message) {
        showNotification("The Information is Incorrect", message, "/image/error.png");
    }

    public void showNotificationInvalidType() {
        showNotification("File Type Not Allowed", "Please verify the file type and try again", "/image/invalid.png");
    }

    public void showNotificationEnterCorrectInformation() {
        showNotification("Wrong Information", "Please check the information", "/image/incorrect.png");
    }

    public void showNotificationEnterEmail() {
        showNotification("Email Address", "Add your email address", "/image/emailMain.png");
    }

    public void showNotificationEnterNewPassword() {
        showNotification("Password", "Add a new and different password", "/image/passwordChange.png");
    }

    public void showNotificationEnterNewUsername() {
        showNotification("Username", "Add a new and different username", "/image/changeUsername.png");
    }

    public void showNotificationPleaseWait() {
        showNotification("Please Wait", "It may take some time", "/image/review.png");
    }

    public void showNotificationSuccessfullyCreateAccount() {
        showNotification("Account Created Successfully", "Now you can log in", "/image/account_created_successfully.png");
    }
}
