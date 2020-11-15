package by.bsuir;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {

    @FXML
    private Button holshedMetricsButton;

    @FXML
    private Button jilbMetricsButton;

    @FXML
    void initialize() {
        holshedMetricsButton.setOnAction(actionEvent -> App.hideWindow("HolshedMetricsWindow.fxml"));

        jilbMetricsButton.setOnAction(actionEvent -> App.hideWindow("JilbMetricsWindow.fxml"));
    }
}
