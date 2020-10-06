package by.bsuir;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainWindowController {

    @FXML
    private Button OpenButton;

    @FXML
    private Button HelpButton;

    @FXML
    private TableView<String> Table;

    @FXML
    private TableColumn<String, String> OperatorsNumber;

    @FXML
    private TableColumn<String, String> OperatorsName;

    @FXML
    private TableColumn<String, String> OperatorsCount;

    @FXML
    private TableColumn<String, String> OperandsNumber;

    @FXML
    private TableColumn<String, String> OperandsName;

    @FXML
    private TableColumn<String, String> OperandsCount;

    @FXML
    void initialize() {

    }

}
