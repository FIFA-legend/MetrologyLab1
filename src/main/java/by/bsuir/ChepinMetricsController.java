package by.bsuir;

import by.bsuir.holshed.Container;
import by.bsuir.chepin.ChepinMetrics;
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

public class ChepinMetricsController {

    @FXML
    private Button OpenButton;

    @FXML
    private Button ReturnButton;

    @FXML
    private TableView<DTO> OperandTable;

    @FXML
    private TableColumn<DTO, String> OperandsNumber;

    @FXML
    private TableColumn<DTO, String> OperandsName;

    @FXML
    private TableColumn<DTO, String> Type;

    @FXML
    private TableColumn<DTO, String> InputOutputType;

    @FXML
    private TableColumn<DTO, String> OperandsCount;

    @FXML
    void initialize() {
        OperandsNumber.setCellValueFactory(number -> number.getValue().number.asString());
        OperandsName.setCellValueFactory(name -> name.getValue().name);
        Type.setCellValueFactory(type -> type.getValue().type);
        InputOutputType.setCellValueFactory(type -> type.getValue().inputOutputType);
        OperandsCount.setCellValueFactory(count -> count.getValue().count.asString());

        ReturnButton.setOnAction(actionEvent -> App.hideWindow("MainWindow.fxml"));

        OpenButton.setOnAction(actionEvent -> {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(App.getMainStage());
            if (file != null) {
                if (!file.getAbsolutePath().endsWith(".txt")) {
                    this.showError();
                } else {
                    ChepinMetrics metrics = new ChepinMetrics(new Container(), file);
                    metrics.initLexemes();
                    Map<String, Integer> result = metrics.getOperatorsCount(metrics.getContainer().getOperands());
                    Map<String, String> chepinMetricsMap = metrics.chepinMetrics(result);
                    Map<String, String> inputOutputMap = metrics.chepinInputOutputMetrics(chepinMetricsMap);
                    ObservableList<DTO> observableList = FXCollections.observableList(this.convertMapToList(result, chepinMetricsMap, inputOutputMap));
                    OperandTable.setItems(observableList);
                    showInformation(result, chepinMetricsMap, inputOutputMap);
                }
            }
        });
    }

    private void showInformation(Map<String, Integer> spenMap, Map<String, String> chepinMap, Map<String, String> inputOutputMap) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Информация");
        int summary = this.countSummarySpen(spenMap);
        double metrics = this.countMetrics(chepinMap);
        double inputOutputMetrics = this.countMetrics(inputOutputMap);
        String str = "Суммарный Спен программы - " + summary;
        str = str.concat(String.format("\nМетрика Чепина - %.2f\n", metrics));
        str = str.concat(String.format("Метрика Чепина ввода/вывода - %.2f", inputOutputMetrics));
        alert.setContentText(str);
        alert.showAndWait();
    }

    private double countMetrics(Map<String, String> map) {
        int p = this.countType(map, "P");
        int m = this.countType(map, "M");
        int c = this.countType(map, "C");
        int t = this.countType(map, "T");
        return  p + 2 * m + 3 * c + 0.5 * t;
    }

    private int countSummarySpen(Map<String, Integer> map) {
        return map.values()
                .stream()
                .mapToInt(count -> count)
                .sum();
    }

    private int countType(Map<String, String> chepinMap, String type) {
        return (int) chepinMap.values()
                .stream()
                .filter(value -> value.equals(type))
                .count();
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Ошибка");
        alert.setContentText("Откройте файл с расширением .txt");
        alert.showAndWait();
    }

    private List<DTO> convertMapToList(Map<String, Integer> map, Map<String, String> chepikMap, Map<String, String> inputOutputMap) {
        List<DTO> list = new LinkedList<>();
        int number = 1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String str;
            str = inputOutputMap.getOrDefault(entry.getKey(), "-");
            list.add(new DTO(number++, entry.getKey(), chepikMap.get(entry.getKey()), str, entry.getValue()));
        }
        return list;
    }

    private static class DTO {

        IntegerProperty number;

        StringProperty name;

        StringProperty type;

        StringProperty inputOutputType;

        IntegerProperty count;

        public DTO(Integer number, String name, String type, String inputOutputType, Integer count) {
            this.number = new SimpleIntegerProperty(number);
            this.name = new SimpleStringProperty(name);
            this.type = new SimpleStringProperty(type);
            this.inputOutputType = new SimpleStringProperty(inputOutputType);
            this.count = new SimpleIntegerProperty(count);
        }
    }
}
