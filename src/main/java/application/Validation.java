package application;

public class Validation {
    private static Validation vClass;
    private static final CheckingMethod cM= CheckingMethod.getInstance();
    private static AccountController aC;
    private static enterEmailController eEC;
    private static ChangePasswordController cPC;
    private static ChangeUsernameController cUC;
    private final int port = 8080;
//    private static final String filePath = "D:/eclipse/IdeaProjects/E-commerce-shopping/src/main/resources/database.txt"; // Path of the file to write to
    private final ClientConnection clientConnection = new ClientConnection("localhost", port);
    private static String response;
    public Validation(){}
    public static Validation getInstance(AccountController aC, enterEmailController eEC, ChangePasswordController cPC, ChangeUsernameController cUC){
        if (vClass==null){
            vClass=new Validation();
        }
        if (aC!=null){
            Validation.aC = aC;
        }
        if (cPC!=null){
            Validation.cPC = cPC;
        }
        if(eEC!=null){
            Validation.eEC = eEC;
        }
        if (cUC!=null){
            Validation.cUC=cUC;
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
        else if(SIPassword.length()<8){
            aC.setErrorForPasswordLogin("Too Short!!!", "Password Must 8 characters!!");
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
            CheckingMethod cM = new CheckingMethod();
            boolean strong = cM.checkPassToHaveDigit(SUPassword);
            if (SUPassword.length() >= 225) {
                aC.showErrorForPasswordSignup("Too Long!!!", "Password is too long");
            }
            else if(SUPassword.length()<8){
                aC.showErrorForPasswordSignup("Too Short!!!", "Password Must Be 8 Characters!");
            }
            else if (!strong) {
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
        if (emailText.isEmpty()) {
            eEC.setEmailError("Empty!", "Email cannot be empty!");
        }
        else if (cM.usernameOrEmail(emailText)) {
            eEC.setEmailError("Not Found!!!", "Please enter a correct email!");
        }
        else{
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
            if (retrieveAndDecryptPassword(user)) {
                cPC.showPasswordErrorForConfirmation("Same Old Password!", "Please Enter Different Password");
            }else {
                if(updatePassword(user,newPassword)){
                    cPC.clearPasswordErrorStylesForConfirmation();
                }else{
                    cPC.showPasswordErrorForConfirmation("", "Please try again!");
                }
            }
        }
        else {
            if(updatePassword(user,newPassword)){
                cPC.clearPasswordErrorStylesForConfirmation();
            }else{
                cPC.showPasswordErrorForConfirmation("", "Please try again!");
            }
        }
    }
    public void checkNewUsername(String newUsername,String oldUsername) throws Exception {
        if (newUsername.isEmpty()) {
            cUC.showUsernameError("Empty!", "Please Enter Username");
        }
        else if (newUsername.length() >= 128) {
            cUC.showUsernameError("Too Long!!!", "Please Enter Short Username");
        }
        else if(newUsername.equals(oldUsername)){
            cUC.showUsernameError("The Same old Username!", "Please Enter Different Username");
        }
        else{
            if(updateUsername(newUsername, oldUsername)){
                cUC.clearUsernameError();
            }else{
                cUC.showUsernameError("", "Please try again!");
            }
        }
    }
    private boolean updateUsername(String newUsername, String oldUsername) {
        boolean result;
        response = clientConnection.sendRequestWithTwoParameter("UPDATE_USERNAME", newUsername,oldUsername);
        System.out.println(response);
        result= cM.checkReturnedResponse(response);
        return result;
    }
    private boolean updatePassword(String user, String encryptedPassword){
        boolean result;
        if (cM.usernameOrEmail(user)) {
            response = clientConnection.sendRequestWithTwoParameter("UPDATE_PASSWORD_USERNAME", user,encryptedPassword);
        } else {
            response = clientConnection.sendRequestWithTwoParameter("UPDATE_PASSWORD_EMAIL", user,encryptedPassword);
        }
        System.out.println(response);
        result= cM.checkReturnedResponse(response);
        return result;
    }
    private boolean retrieveAndDecryptPassword(String user){
        // Print the server's response to the console
        if (cM.usernameOrEmail(user)) {
            response = clientConnection.sendRequestWithOneParameter("CHECK_PASSWORD_USERNAME", user);
        } else {
            response = clientConnection.sendRequestWithOneParameter("CHECK_PASSWORD_EMAIL", user);
        }
        System.out.println(response);  // Print the server's response to the console
        return cM.checkReturnedResponse(response);
    }
    public boolean checkEmailInDatabase(String email)  {
//        //check email in file
//        String name = "";
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(filePath));
//            String line;
//            List<String> list;
//            while ((line = reader.readLine()) != null) {
//                list = List.of(line.split(":"));
//                if (list.getFirst().equals("email")) {
//                    if (list.getLast().equals(email)) {
//                        name = list.getLast();
//                    } else {
//                        name = "";
//                    }
//                }
//            }
//            reader.close();
//        }catch(Exception e){
//            System.out.println("error ");
//        }
//        return !name.isEmpty();
//
//        //check email in database
        response = clientConnection.sendRequestWithOneParameter("CHECK_EMAIL", email);
        System.out.println(response);  // Print the server's response to the console
        return cM.checkReturnedResponse(response);
    }
    private boolean checkUsernameInDatabase(String username)  {
////checking username in file:
//        String name = "";
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(filePath));
//            String line;
//            List<String> list;
//            while ((line = reader.readLine()) != null) {
//                list = List.of(line.split(":"));
//                if (list.getFirst().equals("username")) {
//                    if (list.getLast().equals(username)) {
//                        name = list.getLast();
//                    } else {
//                        name = "";
//                    }
//                }
//            }
//            reader.close();
//        }
//        catch (Exception e) {
//            System.out.println(e);
//        }
//        return !name.isEmpty();

        //checking username in database:
        response = clientConnection.sendRequestWithOneParameter("CHECK_USERNAME", username);
        System.out.println(response);  // Print the server's response to the console
        return cM.checkReturnedResponse(response);
    }
    private boolean checkPasswordInDatabaseWithUsername(String username, String password){
//        checking password in database
//        BufferedReader reader = new BufferedReader(new FileReader(filePath));
//        String line;
//        String pass = "";
//        List<String> list;
//        while ((line = reader.readLine()) != null) {
//            list= List.of(line.split(":"));
//            if(list.getFirst().equals("password")){
//                if(list.getLast().equals(password)){
//                    pass=list.getLast();
//                }
//                else{
//                    pass="";
//                }
//            }
//        }
//        reader.close();
//        return pass.isEmpty();
        response = clientConnection.sendRequestWithTwoParameter("CHECK_PASSWORD_USERNAME", username,password);
        System.out.println(response);  // Print the server's response to the console
        return cM.checkReturnedResponse(response);
    }
    private boolean checkPasswordInDatabaseWithEmail(String email,String password){
        response = clientConnection.sendRequestWithTwoParameter("CHECK_PASSWORD_EMAIL", email,password);
        System.out.println(response);  // Print the server's response to the console
        return cM.checkReturnedResponse(response);
    }
    private boolean checkQuestionFormatInDatabaseWithEmail(String email,int question){
        response = clientConnection.sendRequestWithTwoParameter("CHECK_QUESTION_EMAIL", email,String.valueOf(question));
        System.out.println(response);
        return cM.checkReturnedResponse(response);
    }
    private boolean checkQuestionFormatInDatabaseWithUsername(String username,int question){
        response = clientConnection.sendRequestWithTwoParameter("CHECK_QUESTION_USERNAME", username,String.valueOf(question));
        System.out.println(response);
        return cM.checkReturnedResponse(response);
    }
    private boolean checkAnswerFormatInDatabaseWithEmail(String email,String answer){
        response = clientConnection.sendRequestWithTwoParameter("CHECK_ANSWER_EMAIL", email,answer);
        System.out.println(response);
        return cM.checkReturnedResponse(response);
    }
    private boolean checkAnswerFormatInDatabaseWithUsername(String username,String answer){
        response = clientConnection.sendRequestWithTwoParameter("CHECK_ANSWER_USERNAME",username, answer);
        System.out.println(response);
        return cM.checkReturnedResponse(response);
    }
    public boolean setAllInformationDatabase(String username, String password, String email, int indexQuestoin, String answer){
//        boolean result;
////        set user information in file:
//                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            writer.write("username:" + username + "\n"+
//                    "password:" + password + "\n"+
//                    "email:" + email + "\n"+
//                    "indexQuestion:" + indexQuestoin + "\n"+
//                    "Answer:" + answer + "\n"
//                    );
//            result=true;
////            nC.showNotificationUserAddSuccessfully();
//        } catch (IOException e) {
//                    result=false;
////            nC.showNotificaitonSomethingWrong("Can not add user to database!");
//        }
//
//                return result;
        //set all information to database:
        response = clientConnection.sendRequestToSetUserInformation("SET_USER_INFORMATION", username,password,email,indexQuestoin,answer);
        System.out.println(response);  // Print the server's response to the console
        return cM.checkReturnedResponse(response);
    }
    public boolean setNewEmailToOldEmail(String username, String email){
        response = clientConnection.sendRequestWithTwoParameter("SET_NEW_WITH_OLD_EMAIL", username,email);
        System.out.println(response);
        return cM.checkReturnedResponse(response);
    }
}
