package by.bsuir;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        scene = new Scene(loadMainFXML());
        stage.setTitle("Анализатор кода");
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadMainFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainWindow.fxml"));
        return fxmlLoader.load();
    }

    public static void hideWindow(String str) {
        FXMLLoader loader = getLoader(str);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        App.getMainStage().setScene(new Scene(root));
        App.getMainStage().show();
    }

    private static FXMLLoader getLoader(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml));
    }

    public static void main(String[] args) {
        launch();
    }

}