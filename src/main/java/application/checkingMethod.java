package application;

import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class checkingMethod {
    public boolean checkPassToHaveDigit(String username) {
        int countDigit = 0;
        int countLetter = 0;
        for (int i = 0; i < username.length(); i++) {
            char digit = username.charAt(i);
            if (Character.isLetter(digit)) {
                countLetter++;
            }
            if (Character.isDigit(digit)) {
                countDigit++;
            }
        }
        return !(countDigit == 0) && !(countLetter == 0);
    }
    public void toggleTabVisibility(Stage stage, mainFrame_user mF) {
        double width = stage.getWidth();
        if (width > 800 && width < 1000) {
            mF.setSize(100,20);
            mF.setSizeForAllIcon(40,40);
            mF.setSizeForTwoOtherIcon(35,35,true);
            mF.setVisableForAllLabel(true,18);
        }
        else if(width>=1000){
            mF.setSize(150,30);
            mF.setSizeForAllIcon(55,55);
            mF.setSizeForTwoOtherIcon(45,45,true);
            mF.setVisableForAllLabel(true,22);
        }
        else {
            mF.setSize(75,15);
            mF.setSizeForAllIcon(35,50);
            mF.setSizeForTwoOtherIcon(10,10,false);
            mF.setVisableForAllLabel(false,2);
        }
    }
    public boolean usernameOrEmail(String email) {
        return (!email.endsWith("@gmail.com"));
    }
    public Timestamp getCurrentDateTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
    public boolean checkReturnedResponse(String response) {
        return response.startsWith("1");
    }
}
