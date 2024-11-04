package application;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class mainFrame_user implements Initializable {
    private List<VBox> categoryVBoxes;
    private List<VBox> pageVBox;
    private final Set<VBox> selectedVBoxes = new HashSet<>();
    private List<AnchorPane> listOfAnchorPane;
    private static mainFrame_user mF;
    private static Stage tempStage;
    private static String username;
    @FXML
    private VBox checkoutPageVbox;

    @FXML
    private VBox headphoneCategoryVbox;

    @FXML
    private ImageView headphoneIcon;

    @FXML
    private Label headphoneLabel;

    @FXML
    private ImageView homeIcon;

    @FXML
    private Label homeLabel;

    @FXML
    private VBox homePageVbox;

    @FXML
    private ImageView iphoneIcon;

    @FXML
    private Label iphoneLabel;

    @FXML
    private VBox laptopCategoryVbox;

    @FXML
    private ImageView laptopIcon;

    @FXML
    private Label laptopLabel;

    @FXML
    private GridPane mainGridPane;

    @FXML
    private ImageView newItemIcon;

    @FXML
    private Label newItemLabel;

    @FXML
    private VBox newItemPageVbox;

    @FXML
    private VBox pcCategoryVbox;

    @FXML
    private ImageView pcIcon;

    @FXML
    private Label pcLabel;

    @FXML
    private VBox phoneCaseCategoryVbox;

    @FXML
    private ImageView phoneCaseIcon;

    @FXML
    private Label phoneCaseLabel;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView settingIcon;

    @FXML
    private VBox smartPhoneCategoryVbox;

    @FXML
    private ImageView smartTvIcon;

    @FXML
    private Label smartTvLabel;

    @FXML
    private VBox smartWatchCategoryVbox;

    @FXML
    private ImageView smartWatchIcon;

    @FXML
    private Label smartWatchLabel;

    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView systemIcon;

    @FXML
    private HBox tabCategories_user_hbox;

    @FXML
    private VBox tabForm_user_vbox;

    @FXML
    private VBox tabletCategoryVbox;

    @FXML
    private ImageView tabletIcon;

    @FXML
    private Label tabletLabel;

    @FXML
    private VBox tvCategoryVbox;

    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView aboutUsIcon;

    @FXML
    private Label aboutUsLabel;

    @FXML
    private VBox aboutUsPageVbox;

    @FXML
    private VBox allCategoryVbox;

    @FXML
    private VBox bestSallCategoryVbox;

    @FXML
    private ImageView bestSellIcon;

    @FXML
    private Label bestSellLabel;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox cameraCategoryVbox;

    @FXML
    private ImageView cameraIcon;

    @FXML
    private Label cameraLabel;

    @FXML
    private ImageView categoryIcon;

    @FXML
    private Label categoryLabel;

    @FXML
    private ImageView checkoutIcon;

    @FXML
    private Label checkoutLabel;

    public mainFrame_user(mainFrame_user mF, Stage tempStage,String username){
        mainFrame_user.mF=mF;
        mainFrame_user.tempStage=tempStage;
        mainFrame_user.username=username;
    }

    public mainFrame_user(){}
    @FXML
    void openSetting(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/settingController.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage=new Stage();
        primaryStage.setTitle("Talent Shopping");
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fxmlFile/application.css")).toExternalForm());
        primaryStage.setScene(scene);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/image/setting.png")).toString());
        primaryStage.getIcons().add(image);
        settingController sC=loader.getController();
        new settingController(sC,mF,primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
        tempStage.close();
        primaryStage.setOnCloseRequest(event1 -> {
            event1.consume();
            primaryStage.close();
            tempStage.show();
        });
    }

    public void setSize(int width, int space) {
        tabForm_user_vbox.setSpacing(space);
        tabForm_user_vbox.setPrefWidth(width);
        if(space==30)
            tabCategories_user_hbox.setSpacing(space+space);
        else if(space==15)
            tabCategories_user_hbox.setSpacing(space+5);
        else
            tabCategories_user_hbox.setSpacing(space);
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        listOfAnchorPane = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFile/cardController.fxml"));
            AnchorPane anchorPane;
            try {
                anchorPane = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String countId = "anchorPane" + (i + 1);// lera String bo id nwe drust dakain
            anchorPane.setId(countId);// bo har hboxek id nwe daxl akain
            listOfAnchorPane.add(anchorPane);
        }
        categoryVBoxes = new ArrayList<>();
        categoryVBoxes.add(tvCategoryVbox);
        categoryVBoxes.add(tabletCategoryVbox);
        categoryVBoxes.add(smartWatchCategoryVbox);
        categoryVBoxes.add(smartPhoneCategoryVbox);
        categoryVBoxes.add(pcCategoryVbox);
        categoryVBoxes.add(phoneCaseCategoryVbox);
        categoryVBoxes.add(laptopCategoryVbox);
        categoryVBoxes.add(headphoneCategoryVbox);
        categoryVBoxes.add(cameraCategoryVbox);
        categoryVBoxes.add(allCategoryVbox);
        pageVBox=new ArrayList<>();
        pageVBox.add(homePageVbox);
        pageVBox.add(newItemPageVbox);
        pageVBox.add(checkoutPageVbox);
        pageVBox.add(bestSallCategoryVbox);
        pageVBox.add(aboutUsPageVbox);
        initializeCategories();
        updateProductByCategory();
    }
    public void setSizeForAllIcon(int width, int height){
        homeIcon.setFitHeight(height);
        homeIcon.setFitWidth(width);
        newItemIcon.setFitHeight(height);
        newItemIcon.setFitWidth(width);
        bestSellIcon.setFitHeight(height);
        bestSellIcon.setFitWidth(width);
        checkoutIcon.setFitHeight(height);
        checkoutIcon.setFitWidth(width);
        aboutUsIcon.setFitHeight(height);
        aboutUsIcon.setFitWidth(width);
        categoryIcon.setFitHeight(height);
        categoryIcon.setFitWidth(width);
        laptopIcon.setFitHeight(height);
        laptopIcon.setFitWidth(width);
        iphoneIcon.setFitHeight(height);
        iphoneIcon.setFitWidth(width);
        tabletIcon.setFitHeight(height);
        tabletIcon.setFitWidth(width);
        phoneCaseIcon.setFitHeight(height);
        phoneCaseIcon.setFitWidth(width);
        pcIcon.setFitHeight(height);
        pcIcon.setFitWidth(width);
        smartTvIcon.setFitHeight(height);
        smartTvIcon.setFitWidth(width);
        smartWatchIcon.setFitHeight(height);
        smartWatchIcon.setFitWidth(width);
        headphoneIcon.setFitHeight(height);
        headphoneIcon.setFitWidth(width);
        cameraIcon.setFitHeight(height);
        cameraIcon.setFitWidth(width);
    }
    public void setVisableForAllLabel(boolean visibility,int fontSize){
        String font=laptopLabel.getFont().getName();
        homeLabel.setFont(new Font(font, fontSize));
        newItemLabel.setFont(new Font(font, fontSize));
        checkoutLabel.setFont(new Font(font, fontSize));
        bestSellLabel.setFont(new Font(font, fontSize));
        aboutUsLabel.setFont(new Font(font, fontSize));
        usernameLabel.setFont(new Font(font, fontSize));
        categoryLabel.setFont(new Font(font, fontSize));
        laptopLabel.setFont(new Font(font, fontSize));
        pcLabel.setFont(new Font(font, fontSize));
        iphoneLabel.setFont(new Font(font, fontSize));
        tabletLabel.setFont(new Font(font, fontSize));
        phoneCaseLabel.setFont(new Font(font, fontSize));
        smartWatchLabel.setFont(new Font(font, fontSize));
        smartTvLabel.setFont(new Font(font, fontSize));
        headphoneLabel.setFont(new Font(font, fontSize));
        cameraLabel.setFont(new Font(font, fontSize));
        homeLabel.setVisible(visibility);
        newItemLabel.setVisible(visibility);
        checkoutLabel.setVisible(visibility);
        bestSellLabel.setVisible(visibility);
        aboutUsLabel.setVisible(visibility);
        usernameLabel.setVisible(visibility);
        categoryLabel.setVisible(visibility);
        laptopLabel.setVisible(visibility);
        pcLabel.setVisible(visibility);
        iphoneLabel.setVisible(visibility);
        tabletLabel.setVisible(visibility);
        phoneCaseLabel.setVisible(visibility);
        smartWatchLabel.setVisible(visibility);
        smartTvLabel.setVisible(visibility);
        headphoneLabel.setVisible(visibility);
        cameraLabel.setVisible(visibility);
    }
    public void setSizeForTwoOtherIcon(int width, int height,boolean visibility){
        systemIcon.setFitHeight(height);
        systemIcon.setFitWidth(width);
        settingIcon.setFitHeight(height);
        settingIcon.setFitWidth(width);
        systemIcon.setVisible(visibility);
        settingIcon.setVisible(visibility);
    }
    private void initializeCategories() {
        for (VBox vbox : categoryVBoxes) {
            vbox.setOnMouseClicked(_ -> handleMultiSelect(vbox));
        }
        for (VBox vbox : pageVBox) {
            vbox.setOnMouseClicked(_ -> setupSingleSelection(vbox));
        }

    }
    private void handleMultiSelect(VBox vbox) {
        if (selectedVBoxes.contains(vbox)) {
            if (!vbox.getChildren().isEmpty() && vbox.getChildren().getLast() instanceof Label) {
                vbox.getChildren().getLast().setStyle("-fx-text-fill: #7C8363; ");
                updateProductByCategory();
            }
            selectedVBoxes.remove(vbox);
        } else {
            if (!vbox.getChildren().isEmpty() && vbox.getChildren().getLast() instanceof Label) {
                vbox.getChildren().getLast().setStyle("-fx-text-fill: #EDF4F2; ");
                updateProductByCategory();
            }
            selectedVBoxes.add(vbox);
        }
    }

    private void updateProductByCategory() {
        mainGridPane.getChildren().clear();// clear the grid pane
        mainGridPane.getRowConstraints().clear();
        mainGridPane.getColumnConstraints().clear();
        int row = 0;
        int column = 0;
        for (AnchorPane anchorPane : listOfAnchorPane) {
            if (column >= 5) {
                column = 0;
                row++;
            }
            mainGridPane.add(anchorPane, column++, row);// tawakw size randomnumber tawaw dabe atu
        }

    }

    private void setupSingleSelection(VBox vBoxSelected) {
        for (VBox vbox : pageVBox) {
            if(vbox.getChildren().getLast() instanceof Label){
                vbox.getChildren().getLast().setStyle("-fx-text-fill: #7C8363; ");
            }
        }
        if(vBoxSelected.getChildren().getLast() instanceof Label){
            vBoxSelected.getChildren().getLast().setStyle("-fx-text-fill: #EDF4F2; ");
        }
    }

    public void showStage() {
        tempStage.show();
    }
    public String getUsername(){
        return username;
    }
}
