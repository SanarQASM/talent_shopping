package application;

import java.io.*;
import java.util.List;

public class Validation {
    private static Validation vClass;
    private static final checkingMethod cM=new checkingMethod();
    private static accountController aC;
    private static final DatabaseConnection dB=new DatabaseConnection();
    private static enterEmailController eEC;
    private static changePasswordController cPC;
    private static EncryptionAndDecryptionPass eADP=new EncryptionAndDecryptionPass();
    private final int port = 8080;
    private static final String filePath = "D:/eclipse/IdeaProjects/E-commerce-shopping/src/main/resources/database.txt"; // Path of the file to write to
    private final ClientConnection clientConnection = new ClientConnection("localhost", port);
    private static String response;
    public Validation( accountController aC,changePasswordController cPC){
        Validation.aC = aC;
        Validation.cPC = cPC;
    }
    public Validation(accountController aC){
        Validation.aC=aC;
    }
    public Validation (enterEmailController eEC){
        Validation.eEC=eEC;
    }
    public Validation( enterEmailController eEC,changePasswordController cPC){
        Validation.eEC = eEC;
        Validation.cPC = cPC;
    }
    public Validation(){}
    public static Validation getInstance(){
        if (vClass==null){
            vClass=new Validation();
        }
        return vClass;
    }
    public void checkingUsername_Si(String SIUsername) throws Exception {
        //check the username in login
        if (SIUsername.isEmpty()) {
            aC.setErrorForUsernameLogin("Empty!", "Username is empty!");
        }
        else if (SIUsername.length() >= 128) {
            aC.setErrorForUsernameLogin("Too Long!!!", "Username too long!");
        }
        else if (cM.usernameOrEmail(SIUsername)) {
            // Check if input is a username
            if (checkUsernameInDatabase(SIUsername)) {
                aC.clearErrorForUsernameLogin();
                checkingPassword_Si(aC.gettingFinalPass_Si(), SIUsername, true);
            } else {
               aC.setErrorForUsernameLogin("Not found!", "Please enter a correct username.");
            }
        }
        else {
            // Check if input is an email
            if (checkEmailInDatabase(SIUsername)) {
                aC.clearErrorForUsernameLogin();
                checkingPassword_Si(aC.gettingFinalPass_Si(), SIUsername, false);
            } else {
                aC.setErrorForUsernameLogin("Not found!", "Please enter a correct email.");
            }
        }
    }
    private void checkingPassword_Si(String SIPassword, String SIUsername, boolean isUsername) throws Exception {
        if (SIPassword.isEmpty()) {
            aC.setErrorForPasswordLogin("Empty!", "Password is empty!");
        }
        else if (SIPassword.length() >= 225) {
            aC.setErrorForPasswordLogin("Too Long!!!", "Password too long!");
        }
        else {
            boolean passwordFound = isUsername ? checkPasswordInDatabaseWithUsername(SIUsername, SIPassword)
                    : checkPasswordInDatabaseWithEmail(SIUsername, SIPassword);
            if (!passwordFound) {
                aC.setErrorForPasswordLogin("Not found!", "Please enter the correct password.");
            } else {
                aC.clearErrorForPasswordLogin();
            }
        }
    }
    public void checkingUsername_Su(String SUUsername) throws Exception {
        if (SUUsername.isEmpty()) {
            aC.showErrorForUsernameSignup("Empty!", "username cannot be empty");
        }
        else if (SUUsername.length() > 128) {
            aC.showErrorForUsernameSignup("Too Long!!!", "username must be shorter!");
        }
        else if (checkUsernameInDatabase(SUUsername)) {
            aC.showErrorForUsernameSignup("Already have!", "Please use another username");
        }
        else {
            aC.clearErrorForUsernameSignup();
        }
    }
    public void checkingPassword_Su(String SUPassword) throws Exception {
        if (SUPassword.isEmpty()) {
            aC.showErrorForPasswordSignup("Empty!", "Password cannot be empty");
        } else {
            checkingMethod cM = new checkingMethod();
            boolean strong = cM.checkPassToHaveDigit(SUPassword);
            if (SUPassword.length() >= 225) {
                aC.showErrorForPasswordSignup("Too Long!!!", "Password is too long");
            } else if (!strong) {
                aC.showErrorForPasswordSignup("letter & Digit!", "Password must contain both letters and digits");
            } else {
                aC.clearErrorForPasswordSignup();
            }
        }
    }
    public void checkComboBoxForSignup(int suQuestion) throws Exception {
        if (suQuestion==0) {
            aC.showComboBoxErrorForSignup("choose!", "Please choose one of the questions");
        } else {
            aC.clearComboBoxErrorForSignup();
        }
    }
    public void checkAnswerForSignup(String suAnswer) throws Exception {
        if (suAnswer.isEmpty()) {
            aC.showAnswerErrorForSignup("Answer!", "Please Enter Answer");
        } else if (suAnswer.length() > 225) {
            aC.showAnswerErrorForSignup("Too Long!!!", "Please Enter Short Answer");
        } else {
            aC.clearAnswerErrorForSignup();
        }
    }
    public void checkUsernameForget(String username,int question, String answer) throws Exception {
        if (username.length() >= 128) {
            aC.showErrorForUsernameForget("Too Long!!!", "Please Enter Short username");
        } else if (username.isEmpty()) {
            aC.showErrorForUsernameForget("Empty!", "Username cannot be empty!");
        } else if (cM.usernameOrEmail(username)) {//if true then it is username
            if (!checkUsernameInDatabase(username)) {
                aC.showErrorForUsernameForget("Not Found!", "Username not found!");
            }else{
                aC.clearErrorForUsernameForget();
                checkQuestionFormatWithUsername(question,answer,username);
            }
        } else  {
            if (!checkEmailInDatabase(username)) {
                aC.showErrorForUsernameForget("Not Found!", "Email not found!");
            }
            else{
                aC.clearErrorForUsernameForget();
                checkQuestionFormatWithEmail(question,answer,username);
            }
        }
    }
    private void checkQuestionFormatWithEmail(int question,String answer,String user) throws Exception {
        if (question==0) {
            aC.showErrorForQuestionFormat("choose!", "Please Choose one of the Question");
        }else if (!checkQuestionFormatInDatabaseWithEmail(user,question)){
            aC.showErrorForQuestionFormat("not found!","Please Enter Your Question");
        }
        else {
            aC.clearErrorForQuestionFormat();
            checkAnswerFormatWithEmail(answer,user);
        }
    }
    private void checkQuestionFormatWithUsername(int question,String answer,String username) throws Exception {
        if (question==0) {
            aC.showErrorForQuestionFormat("choose!", "Please Choose one of the Question");
        }else if (!checkQuestionFormatInDatabaseWithUsername(username,question)){
            System.out.println("question not found!"+ question);
            aC.showErrorForQuestionFormat("not found!","Please Enter Your Question");
        }
        else {
            aC.clearErrorForQuestionFormat();
            checkAnswerFormatWithUsername(answer,username);
        }
    }
    private void checkAnswerFormatWithEmail(String answer,String email) throws Exception {
        if (answer.isEmpty()) {
            aC.showErrorForAnswerFormat("Answer!", "Please Enter The Answer!", "red");
        } else if (answer.length() > 225) {
            aC.showErrorForAnswerFormat("Too Long!!!", "Please Enter short answer!", "red");
        }else if (!checkAnswerFormatInDatabaseWithEmail(email,answer)){
            aC.showErrorForAnswerFormat("Not Found!","Please Enter Correct answer","red");
        }
        else {
            aC.clearErrorForAnswerFormat();
        }
    }
    private void checkAnswerFormatWithUsername(String answer,String username) throws Exception {
        if (answer.isEmpty()) {
            aC.showErrorForAnswerFormat("Answer!", "Please Enter The Answer!", "red");
        } else if (answer.length() > 225) {
            aC.showErrorForAnswerFormat("Too Long!!!", "Please Enter short answer!", "red");
        }else if (!checkAnswerFormatInDatabaseWithUsername(username,answer)){
            aC.showErrorForAnswerFormat("Not Found!","Please Enter Correct answer","red");
        }
        else {
            aC.clearErrorForAnswerFormat();
        }
    }
    public void checkCodeInEnterEmailController(String code) throws Exception {
            if (code.isEmpty()) {
                eEC.handleErrorForCode("Empty!", "Code cannot be empty!", "-fx-border-color: red;");
            } else if (!emailSender.randomNumbers.equals(code)) {
                eEC.handleErrorForCode("Not Same!", "Please enter the same code we sent to you!", "-fx-border-color: red;");
            } else {
                eEC.clearErrorForCode();
            }
    }
    public void checkEmailInEnterEmailController(String emailText) throws Exception {
        System.out.println("your email is: "+emailText);
        if (emailText.isEmpty()) {
            eEC.setEmailError("Empty!", "Email cannot be empty!");
        }
        else if (cM.usernameOrEmail(emailText)) {
            eEC.setEmailError("Not Found!!!", "Please enter a correct email!");
        }
        else{
            System.out.println("it is success");
            eEC.setEmailSuccess();
        }

    }
    public void checkNewPassword(String newPassword,String confirmationPassword,String user) throws Exception {
        if (newPassword.isEmpty()) {
            cPC.showPasswordErrorForNew("Empty!", "Please Enter Password");
        }
        else if (newPassword.length() > 225) {
            cPC.showPasswordErrorForNew("Too Long!!!", "Please Enter Short Password");
        }
        else if (!cM.checkPassToHaveDigit(newPassword)) {
            cPC.showPasswordErrorForNew("Letter & Digit!", "Password must contain letter & Digit");
        }
        else{
            cPC.clearPasswordErrorStylesForNew();
            checkConfirmationPassword(newPassword, confirmationPassword, user);
        }
    }
    private void checkConfirmationPassword(String newPassword, String confirmationPassword, String user) throws Exception {
        if (confirmationPassword.isEmpty()) {
            cPC.showPasswordErrorForConfirmation("Empty!", "Please Enter Password");
        }
        else if (newPassword.length() > 225) {
            cPC.showPasswordErrorForConfirmation("Too Long!!!", "Please Enter Short Password");
        }
        else if (!cM.checkPassToHaveDigit(newPassword)) {
            cPC.showPasswordErrorForConfirmation("Letter & Digit!", "Password must contain letter & Digit");
        }
        else{
            cPC.clearPasswordErrorStylesForConfirmation();
            checkNewPasswordAndConfirmationPassword(newPassword,confirmationPassword,user);
        }
    }
    private void checkNewPasswordAndConfirmationPassword(String newPassword, String confirmationPassword, String user) throws Exception {

        if (!newPassword.equals(confirmationPassword)) {
            cPC.showPasswordErrorForConfirmation("Not Same!", "Please Enter Same Password");
        }
        if (aC != null) {
            System.out.println("ac is not null");
            String decryptedPassword = retrieveAndDecryptPassword(user);
            if (decryptedPassword.equals(newPassword)) {
                cPC.showPasswordErrorForConfirmation("Same Old Password!", "Please Enter Different Password");
            }else {
                String encryptedPassword = eADP.encrypt(newPassword);
                updatePassword(user, encryptedPassword);
                cPC.clearPasswordErrorStylesForConfirmation();
            }
        }
        else {
            cPC.clearPasswordErrorStylesForConfirmation();
        }
    }
    private String retrieveAndDecryptPassword(String user) throws Exception {
        if (cM.usernameOrEmail(user)) {
            eADP = new EncryptionAndDecryptionPass(dB.getSecretKeyWithUsername(user));
            return eADP.decrypt(dB.getPasswordThroughUsername(user));
        } else {
            eADP = new EncryptionAndDecryptionPass(dB.getSecretKeyWithEmail(user));
            return eADP.decrypt(dB.getPasswordThroughEmail(user));
        }
    }
    private void updatePassword(String user, String encryptedPassword){
        if (cM.usernameOrEmail(user)) {
            dB.setNewToOldPasswordWithUsername(user, encryptedPassword);
        } else {
            dB.setNewToOldPasswordWithEmail(user, encryptedPassword);
        }
    }


    public boolean checkEmailInDatabase(String email)  {
        //check email in file
        String name = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            List<String> list;
            while ((line = reader.readLine()) != null) {
                list = List.of(line.split(":"));
                if (list.getFirst().equals("email")) {
                    if (list.getLast().equals(email)) {
                        name = list.getLast();
                    } else {
                        name = "";
                    }
                }
            }
            reader.close();
        }catch(Exception e){
            System.out.println("error ");
        }
        return !name.isEmpty();
//
//        //check email in database
//        response = clientConnection.sendRequestWithOneParameter("CHECK_EMAIL", email);
//        System.out.println(response);  // Print the server's response to the console
//        return cM.checkReturnedResponse(response);
    }
    private boolean checkUsernameInDatabase(String username)  {
//checking username in file:
        String name = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            List<String> list;
            while ((line = reader.readLine()) != null) {
                list = List.of(line.split(":"));
                if (list.getFirst().equals("username")) {
                    if (list.getLast().equals(username)) {
                        name = list.getLast();
                    } else {
                        name = "";
                    }
                }
            }
            reader.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return !name.isEmpty();

        //checking username in database:
//        response = clientConnection.sendRequestWithOneParameter("CHECK_USERNAME", username);
//        System.out.println(response);  // Print the server's response to the console
//        return cM.checkReturnedResponse(response);

    }
    private boolean checkPasswordInDatabaseWithUsername(String username, String password) throws IOException {
//        checking password in database
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        String pass = "";
        List<String> list;
        while ((line = reader.readLine()) != null) {
            list= List.of(line.split(":"));
            if(list.getFirst().equals("password")){
                if(list.getLast().equals(password)){
                    pass=list.getLast();
                }
                else{
                    pass="";
                }
            }
        }
        reader.close();
        return pass.isEmpty();
//        response = clientConnection.sendRequestWithTwoParameter("CHECK_PASSWORD_USERNAME", username,password);
//        System.out.println(response);  // Print the server's response to the console
//        return cM.checkReturnedResponse(response);
    }
    private boolean checkPasswordInDatabaseWithEmail(String email,String password){
        response = clientConnection.sendRequestWithTwoParameter("CHECK_PASSWORD_EMAIL", email,password);
        System.out.println(response);  // Print the server's response to the console
        return cM.checkReturnedResponse(response);
    }


    private boolean checkQuestionFormatInDatabaseWithEmail(String email,int question){
        return dB.checkingQuestionWithEmail(email,question);
    }
    private boolean checkQuestionFormatInDatabaseWithUsername(String username,int question){
        return dB.checkingQuestionWithUsername(username,question);
    }
    private boolean checkAnswerFormatInDatabaseWithEmail(String email,String answer){
        return dB.checkingAnswerWithEmail(email,answer);
    }
    private boolean checkAnswerFormatInDatabaseWithUsername(String username,String answer){
        return dB.checkingAnswerWithUsername(username,answer);
    }


    public boolean setAllInformationIndatabase(String username, String password, String email, int indexQuestoin, String answer) throws Exception {
        boolean result;
//        set user information in file:
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("username:" + username + "\n"+
                    "password:" + password + "\n"+
                    "email:" + email + "\n"+
                    "indexQuestion:" + indexQuestoin + "\n"+
                    "Answer:" + answer + "\n"
                    );
            result=true;
//            nC.showNotificationUserAddSuccessfully();
        } catch (IOException e) {
                    result=false;
//            nC.showNotificaitonSomethingWrong("Can not add user to database!");
        }

                return result;
        //set all information to database:
//        String generatedKey=eADP.generateSecretKey();
//        String encryptedPassword=eADP.encrypt(password);
//        response = clientConnection.sendRequestToSetUserInformation("SET_USER_INFORMATION", username,encryptedPassword,email,indexQuestoin,answer,generatedKey);
//        System.out.println(response);  // Print the server's response to the console
//        return cM.checkReturnedResponse(response);
    }
}
