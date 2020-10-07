package by.bsuir;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainWindowController {

    @FXML
    private Button OpenButton;

    @FXML
    private Button HelpButton;

    @FXML
    private TableView<DTO> OperandTable;

    @FXML
    private TableView<DTO> OperatorTable;

    @FXML
    private TableColumn<DTO, String> OperatorsNumber;

    @FXML
    private TableColumn<DTO, String> OperatorsName;

    @FXML
    private TableColumn<DTO, String> OperatorsCount;

    @FXML
    private TableColumn<DTO, String> OperandsNumber;

    @FXML
    private TableColumn<DTO, String> OperandsName;

    @FXML
    private TableColumn<DTO, String> OperandsCount;

    @FXML
    void initialize() {
        OperandsNumber.setCellValueFactory(number -> number.getValue().number.asString());
        OperandsName.setCellValueFactory(name -> name.getValue().name);
        OperandsCount.setCellValueFactory(count -> count.getValue().count.asString());

        OperatorsNumber.setCellValueFactory(number -> number.getValue().number.asString());
        OperatorsName.setCellValueFactory(name -> name.getValue().name);
        OperatorsCount.setCellValueFactory(count -> count.getValue().count.asString());

        HelpButton.setOnAction(actionEvent -> {
            OperatorTable.setDisable(true);
            OperandTable.setDisable(true);
        });

        OpenButton.setOnAction(actionEvent -> {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(App.getMainStage());
            if (file != null) {
                if (!file.getAbsolutePath().endsWith(".txt")) {
                    this.showError();
                } else {
                    OperandTable.setDisable(false);
                    OperatorTable.setDisable(false);
                    Container container = new Container();
                    FileService fileService = new FileService(container);
                    fileService.initLexems(file);
                    Map<String, Integer> operands = container.getOperands();
                    Map<String, Integer> operators = container.getOperators();
                    List<DTO> operandList = new LinkedList<>();
                    List<DTO> operatorList = new LinkedList<>();
                    int operandsLength = this.convertMapToList(operands, operandList);
                    int operatorsLength = this.convertMapToList(operators, operatorList);
                    int i = operandList.size();
                    int j = operatorList.size();
                    ObservableList<DTO> operandOL = FXCollections.observableList(operandList);
                    ObservableList<DTO> operatorOL = FXCollections.observableList(operatorList);
                    OperandTable.setItems(operandOL);
                    OperatorTable.setItems(operatorOL);
                    int dictionary = i + j;
                    int length = operandsLength + operatorsLength;
                    this.showAllMetrics(dictionary, length);
                }
            }
        });
    }

    private int convertMapToList(Map<String, Integer> map, List<DTO> list) {
        int i = 1;
        int length = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            list.add(new DTO(i++, entry.getKey(), entry.getValue()));
            length += entry.getValue();
        }
        return length;
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Ошибка");
        alert.setContentText("Откройте файл с расширением .txt");
        alert.showAndWait();
    }

    private void showAllMetrics(int dictionary, int length) {
        double volume = length * (Math.log(dictionary) / Math.log(2));
        String message = String.format("Словарь программы - %d\nДлина программы - %d\nОбъем программы - %.2f", dictionary, length, volume);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Информация");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class DTO {

        IntegerProperty number;

        StringProperty name;

        IntegerProperty count;

        public DTO(Integer number, String name, Integer count) {
            this.number = new SimpleIntegerProperty(number);
            this.name = new SimpleStringProperty(name);
            this.count = new SimpleIntegerProperty(count);
        }
    }
}
