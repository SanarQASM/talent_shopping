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
    CSS_STYLE("/fxmlFile/application.css"),
    MAIN_FRAME_USER("/fxmlFile/mainFrame-user.fxml"),
    EMAIL_FRAME("/fxmlFile/enterEmailController.fxml"),
    CHANGE_PASSWORD_FRAME("/fxmlFile/changePassword.fxml");
    private final String reference;
    References(String reference) {
        this.reference = reference;
    }
    public String getImageReference() {
        return reference;
    }
}
