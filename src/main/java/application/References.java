package application;

public enum References {
    LOGIN_IMAGE("/image/login.png"),
    HIDE_PASS_IMAGE("/image/hidePass.png"),
    SHOW_PASS_IMAGE("/image/showPass.png"),
    REGISTER_IMAGE("/image/register.png"),
    CHANGE_STATUS_GIF("/image/changeToLoading.gif"),
    MAIN_IMAGE("/image/mainImage.png"),
    MAIN_EMAIL_IMAGE("/image/emailMain.png"),
    RESET_PASSWORD_IMAGE("/image/reset-password.png"),
    FORGET_PASSWORD_IMAGE("/image/forgot-password.png"),
    FORGET_EMAIL_IMAGE("/image/forgetEmail.png"),
    SOMETHING_WRONG_IMAGE("/image/somethingWrong.png"),
    NO_WIFI_IMAGE("/image/no-wifi.png"),
    EMAIL_SUCCESSFULLY_SEND("/image/EmailSentSuccessfully.png"),
    CHANGE_PASSWORD_IMAGE("/image/passwordChange.png"),
    TOO_MANY_TRY("/image/tooManyTry.png"),
    CHANGE_USERNAME_IMAGE("/image/changeUsername.png"),
    CHANGE_EMAIL_IMAGE("/image/changeEmail.png"),
    REVIEW_IMAGE("/image/review.png"),
    EMPTY_IMAGE("/image/error.png"),
    INVALID_IMAGE("/image/invalid.png"),
    INCORRECT_IMAGE("/image/incorrect.png"),
    ACCOUNT_CREATE_SUCCESSFULLY("/image/account_created_successfully.png"),
    CSS_STYLE("/fxmlFile/application.css"),
    MAIN_FRAME_USER("/fxmlFile/mainFrame-user.fxml"),
    ACCOUNT_FRAME("/fxmlFile/account.fxml"),
    EMAIL_FRAME("/fxmlFile/enterEmailController.fxml"),
    CHANGE_PASSWORD_FRAME("/fxmlFile/changePassword.fxml"),
    CHANGE_USERNAME_FRAME("/fxmlFile/changeUsername.fxml");
    private final String reference;
    References(String reference) {
        this.reference = reference;
    }
    public String getImageReference() {
        return reference;
    }
}
