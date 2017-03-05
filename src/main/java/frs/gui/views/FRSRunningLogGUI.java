package frs.gui.views;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class FRSRunningLogGUI implements Initializable {
	/*
	@FXML
	private TableView<Symptom_List> symptomTable;
	
	@FXML
	private TableColumn<Symptom_List, Integer> symptomIDColumn, componentIDColumn;
	
	@FXML
	private TableColumn<Symptom_List, String> descriptionColumn;
	
	@FXML
	private TableColumn<Symptom_List, Double> minLimitColumn, maxLimitColumn;
	
	private final ObservableList<Symptom_List> symptomData = FXCollections.observableArrayList();
	*/
	
	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public FRSRunningLogGUI() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//initSymptomTable();
	}
	/*
	private void initSymptomTable() {
		symptomTable.setEditable(false);
		symptomIDColumn.setCellValueFactory(new PropertyValueFactory<Symptom_List, Integer>("symptomId"));
		componentIDColumn.setCellValueFactory(new PropertyValueFactory<Symptom_List, Integer>("componentId"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Symptom_List, String>("description"));
		minLimitColumn.setCellValueFactory(new PropertyValueFactory<Symptom_List, Double>("minLimit"));
		maxLimitColumn.setCellValueFactory(new PropertyValueFactory<Symptom_List, Double>("maxLimit"));
		symptomTable.setItems(symptomData);
	}*/
}
