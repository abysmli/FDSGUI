package frs.gui.controllers;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

public class DetectedFaultController {
	
    public static final String Column1MapKey = "A";
    public static final String Column2MapKey = "B";
    
    public DetectedFaultController() {
        TableColumn<Map, String> firstDataColumn = new TableColumn<>("Class A");
        TableColumn<Map, String> secondDataColumn = new TableColumn<>("Class B");
        firstDataColumn.setCellValueFactory(new MapValueFactory(Column1MapKey));
        secondDataColumn.setCellValueFactory(new MapValueFactory(Column2MapKey));
        TableView table_view = new TableView<>();
        table_view.setItems(generateDataInMap());
        table_view.setEditable(true);
        table_view.getSelectionModel().setCellSelectionEnabled(true);
        table_view.getColumns().setAll(firstDataColumn, secondDataColumn);
    }
	
    private ObservableList<Map> generateDataInMap() {
        int max = 10;
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (int i = 1; i < max; i++) {
            Map<String, String> dataRow = new HashMap<>();
            String value1 = "A" + i;
            String value2 = "B" + i;
            dataRow.put(Column1MapKey, value1);
            dataRow.put(Column2MapKey, value2);
            allData.add(dataRow);
        }
        return allData;
    }
}
