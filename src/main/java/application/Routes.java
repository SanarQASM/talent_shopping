package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

public class Routes {
    private static FXMLLoader loader;
    public void openStage(String fxmlPath, String cssPath, String iconPath, String title, Consumer<Stage> onStageReady) throws IOException {
        loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        if (cssPath != null) {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
        }

        if (iconPath != null) {
            Image icon = new Image(Objects.requireNonNull(getClass().getResource(iconPath)).toString());
            stage.getIcons().add(icon);
        }
        onStageReady.accept(stage);
        stage.show();
    }
    public Object getController(){
        return loader.getController();
    }
}
