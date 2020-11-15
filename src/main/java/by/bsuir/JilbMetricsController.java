package by.bsuir;

import by.bsuir.holshed.Container;
import by.bsuir.holshed.FileService;
import by.bsuir.holshed.LexicalAnalyzer;
import by.bsuir.jilb.JilbMetrics;
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

public class JilbMetricsController {

    @FXML
    private Button OpenButton;

    @FXML
    private Button ReturnButton;

    @FXML
    private TableView<DTO> OperatorTable;

    @FXML
    private TableColumn<DTO, String> OperatorsNumber;

    @FXML
    private TableColumn<DTO, String> OperatorsName;

    @FXML
    private TableColumn<DTO, String> OperatorsCount;

    @FXML
    void initialize() {
        OperatorsNumber.setCellValueFactory(number -> number.getValue().number.asString());
        OperatorsName.setCellValueFactory(name -> name.getValue().name);
        OperatorsCount.setCellValueFactory(count -> count.getValue().count.asString());

        ReturnButton.setOnAction(actionEvent -> App.hideWindow("MainWindow.fxml"));

        OpenButton.setOnAction(actionEvent -> {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(App.getMainStage());
            if (file != null) {
                if (!file.getAbsolutePath().endsWith(".txt")) {
                    this.showError();
                } else {
                    Container container = new Container();
                    JilbMetrics metrics = new JilbMetrics(container, new LexicalAnalyzer(), file, new FileService(container));
                    metrics.initLexemes();
                    ObservableList<DTO> list = FXCollections.observableList(this.convertMapToList(metrics.getContainer().getOperators()));
                    OperatorTable.setItems(list);
                    this.showInformation(metrics);
                }
            }
        });
    }

    private List<DTO> convertMapToList(Map<String, Integer> map) {
        List<DTO> list = new LinkedList<>();
        int id = 1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            list.add(new DTO(id++, entry.getKey(), entry.getValue()));
        }
        return list;
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Ошибка");
        alert.setContentText("Откройте файл с расширением .txt");
        alert.showAndWait();
    }

    private void showInformation(JilbMetrics metrics) {
        int op = this.totalOperators(metrics);
        int conditionOp = this.conditionOperators(metrics);
        double d = (double) conditionOp / op;
        int max = metrics.getMaxNestingLevel();
        String str = "Количество операторов - " + op;
        str += "\nКоличество условных операторов - " + conditionOp;
        String s = String.format("\nОтносительная сложность программы - %.4f", d);
        str += s;
        str += "\nМаксимальный уровень вложенности - " + max;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Информация");
        alert.setContentText(str);
        alert.showAndWait();
    }

    private int conditionOperators(JilbMetrics metrics) {
        int count = 0;
        for (Map.Entry<String, Integer> entry : metrics.getContainer().getOperators().entrySet()) {
            if (entry.getKey().equals("if") || entry.getKey().equals("elif") ||
                    entry.getKey().equals("for") || entry.getKey().equals("while")) {
                count += entry.getValue();
            }
        }
        return count;
    }

    private int totalOperators(JilbMetrics metrics) {
        int sum = 0;
        for (Map.Entry<String, Integer> entry : metrics.getContainer().getOperators().entrySet()) {
            sum += entry.getValue();
        }
        return sum;
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
