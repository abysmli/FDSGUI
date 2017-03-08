package frs.gui.views;

import frs.gui.logics.FRSCenterController;
import frs.gui.logics.FaultDiagnoseController;
import frs.gui.MainApp;
import frs.gui.models.Component_List;
import frs.gui.models.Fault_List;
import frs.gui.models.Function_List;
import frs.gui.models.Mainfunction_List;
import frs.gui.models.Subfunction_List;
import frs.gui.models.Subsystem_List;
import frs.gui.models.Symptom_List;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.ToggleSwitch;
import org.json.JSONException;

public class FRSMainGUIController implements Initializable {

	/*
	 * -----------------componentTable --------------------
	 */
	@FXML
	private TableView<Component_List> componentTable;

	@FXML
	private TableColumn<Component_List, Integer> componentIdColumn;

	@FXML
	private TableColumn<Component_List, String> componentSymbolColumn, componentDescColumn, componentNameColumn,
			seriesColumn, typeColumn, activitionColumn, componentStatusColumn, componentDateColumn;

	private final ObservableList<Component_List> componentData = FXCollections.observableArrayList();

	/*
	 * -----------------functionTable --------------------
	 */
	@FXML
	private TableView<Function_List> functionTable;

	@FXML
	private TableColumn<Function_List, Integer> functionIdColumn;

	@FXML
	private TableColumn<Function_List, String> functionDescColumn, functionStatusColumn, functionDateColumn;

	private final ObservableList<Function_List> functionData = FXCollections.observableArrayList();

	/*
	 * -----------------subsystemTable --------------------
	 */
	@FXML
	private TableView<Subsystem_List> subsystemTable;

	@FXML
	private TableColumn<Subsystem_List, Integer> subsystemIdColumn;

	@FXML
	private TableColumn<Subsystem_List, String> subsystemDescColumn, subsystemStatusColumn, subsystemDateColumn;

	private final ObservableList<Subsystem_List> subsystemData = FXCollections.observableArrayList();

	/*
	 * -----------------subfunctionTable --------------------
	 */
	@FXML
	private TableView<Subfunction_List> subfunctionTable;

	@FXML
	private TableColumn<Subfunction_List, Integer> subfunctionIdColumn;

	@FXML
	private TableColumn<Subfunction_List, String> subfunctionDescColumn, subfunctionStatusColumn, subfunctionDateColumn;

	private final ObservableList<Subfunction_List> subfunctionData = FXCollections.observableArrayList();

	/*
	 * ----------------- mainfunctionTable --------------------
	 */
	@FXML
	private TableView<Mainfunction_List> mainfunctionTable;

	@FXML
	private TableColumn<Mainfunction_List, Integer> mainfunctionIdColumn;

	@FXML
	private TableColumn<Mainfunction_List, String> mainfunctionDescColumn, mainfunctionStatusColumn,
			mainfunctionDateColumn;

	private final ObservableList<Mainfunction_List> mainfunctionData = FXCollections.observableArrayList();

//	/* ----------------- knownfaultTable -------------------- */
//	@FXML
//	private TableView<?> knownFaultTable;
//
//	@FXML
//	private TableColumn<Map, Integer> knownFaultID;
//
//	@FXML
//	private TableColumn<Map, String> knownFaultType, knownFaultDesc, knownSolution;
//
//	
//	/* ----------------- unknownfaultTable -------------------- */
//	@FXML
//	private TableView<?> unknownFaultTable;
//
//	@FXML
//	private TableColumn<Map, Integer> unknownFaultID;
//
//	@FXML
//	private TableColumn<Map, String> unknownFaultType, unknownFaultDesc, unknownSolution;
	
	/* ----------------- faultTable -------------------- */
	@FXML
	private TableView<Fault_List> faultTable;

	@FXML
	private TableColumn<Fault_List, Integer> faultIdColumn, faultcomponentIdColumn;

	@FXML
	private TableColumn<Fault_List, String> faultTypeColumn, faultDescColumn, insertDateColumn;

	@FXML
	private TableColumn<Fault_List, Object> executeCommandColumn;

	private final ObservableList<Fault_List> faultData = FXCollections.observableArrayList();

	/* ----------------- symptomTable -------------------- */
	@FXML
	private TableView<Symptom_List> symptomTable;

	@FXML
	private TableColumn<Symptom_List, Integer> symptomIDColumn, componentIDColumn;

	@FXML
	private TableColumn<Symptom_List, String> descriptionColumn;

	@FXML
	private TableColumn<Symptom_List, Double> minLimitColumn, maxLimitColumn;

	private final ObservableList<Symptom_List> symptomData = FXCollections.observableArrayList();

	/* ----------------- Panels -------------------- */
	@FXML
	private AnchorPane setpoint_panel, symptoms_panel;

	@FXML
	private AnchorPane systeminfo_panel, functions_availability_panel, components_availability_panel;

	@FXML
	private AnchorPane components_model_panel, functions_model_panel, requirements_model_panel;

	@FXML
	private AnchorPane canvas_panel, banner_panel, demonstration_panel;

	@FXML
	private TabPane mainTabPane, problem_diagnose_TabPane;

	@FXML
	private VBox system_controll_vbox;

	/* ----------------- Variables -------------------- */
	@FXML
	private Canvas graphicCanvas;

	@FXML
	private ImageView anlage_img;

	boolean changeFlag = false;
	boolean startFlag = true;

	// Reference to the main application.
	private MainApp application;
	private FRSCenterController centerController;
	private FaultDiagnoseController problemDiagnoseController;

	private Parent runninglog_root;
	private Stage runninglog_stage = new Stage();

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public FRSMainGUIController() {

	}

	public void startApp(MainApp mainApp) throws JSONException, Exception {
		setApplication(mainApp);
		centerController = new FRSCenterController(this, graphicCanvas, canvas_panel, components_model_panel,
				functions_model_panel, requirements_model_panel, functions_availability_panel,
				components_availability_panel, faultData, functionData, subfunctionData, mainfunctionData,
				componentData, subsystemData);

		problemDiagnoseController = new FaultDiagnoseController(this, setpoint_panel, systeminfo_panel,
				symptoms_panel, demonstration_panel, symptomData);
	}

	private void initModelTables() {
		initMainFunctionModelTable();
		initSubsystemModelTable();
		initComponentModelTable();
		initFunctionModelTable();
		initSubFunctionModelTable();
		initFaultTable();
		initSymptomTable();
	}

	private void initFaultTable() {
		faultTable.setEditable(false);
		faultIdColumn.setCellValueFactory(new PropertyValueFactory<Fault_List, Integer>("faultId"));
		faultcomponentIdColumn.setCellValueFactory(new PropertyValueFactory<Fault_List, Integer>("componentId"));
		faultTypeColumn.setCellValueFactory(new PropertyValueFactory<Fault_List, String>("faultType"));
		faultDescColumn.setCellValueFactory(new PropertyValueFactory<Fault_List, String>("faultDesc"));
		executeCommandColumn.setCellValueFactory(new PropertyValueFactory<Fault_List, Object>("executeCommand"));
		insertDateColumn.setCellValueFactory(new PropertyValueFactory<Fault_List, String>("insertDate"));
		faultTable.setItems(faultData);
	}

	private void initSubFunctionModelTable() {
		subfunctionTable.setEditable(false);
		subfunctionIdColumn.setCellValueFactory(new PropertyValueFactory<Subfunction_List, Integer>("subfunctionId"));
		subfunctionDescColumn
				.setCellValueFactory(new PropertyValueFactory<Subfunction_List, String>("subfunctionDesc"));
		subfunctionStatusColumn
				.setCellValueFactory(new PropertyValueFactory<Subfunction_List, String>("subfunctionStatus"));
		subfunctionDateColumn
				.setCellValueFactory(new PropertyValueFactory<Subfunction_List, String>("subfunctionDate"));
		subfunctionTable.setItems(subfunctionData);
	}

	private void initFunctionModelTable() {
		functionTable.setEditable(false);
		functionIdColumn.setCellValueFactory(new PropertyValueFactory<Function_List, Integer>("functionId"));
		functionDescColumn.setCellValueFactory(new PropertyValueFactory<Function_List, String>("functionDesc"));
		functionStatusColumn.setCellValueFactory(new PropertyValueFactory<Function_List, String>("functionStatus"));
		functionDateColumn.setCellValueFactory(new PropertyValueFactory<Function_List, String>("functionDate"));
		functionTable.setItems(functionData);
	}

	private void initComponentModelTable() {
		componentTable.setEditable(false);
		componentIdColumn.setCellValueFactory(new PropertyValueFactory<Component_List, Integer>("componentId"));
		componentNameColumn.setCellValueFactory(new PropertyValueFactory<Component_List, String>("componentName"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Component_List, String>("componentType"));
		seriesColumn.setCellValueFactory(new PropertyValueFactory<Component_List, String>("componentSeries"));
		componentSymbolColumn.setCellValueFactory(new PropertyValueFactory<Component_List, String>("componentSymbol"));
		componentDescColumn.setCellValueFactory(new PropertyValueFactory<Component_List, String>("componentDesc"));
		activitionColumn.setCellValueFactory(new PropertyValueFactory<Component_List, String>("componentActivition"));
		componentStatusColumn.setCellValueFactory(new PropertyValueFactory<Component_List, String>("componentStatus"));
		componentDateColumn.setCellValueFactory(new PropertyValueFactory<Component_List, String>("componentDate"));
		componentTable.setItems(componentData);
	}

	private void initSubsystemModelTable() {
		subsystemTable.setEditable(false);
		subsystemIdColumn.setCellValueFactory(new PropertyValueFactory<Subsystem_List, Integer>("subsystemId"));
		subsystemDescColumn.setCellValueFactory(new PropertyValueFactory<Subsystem_List, String>("subsystemDesc"));
		subsystemStatusColumn.setCellValueFactory(new PropertyValueFactory<Subsystem_List, String>("subsystemStatus"));
		subsystemDateColumn.setCellValueFactory(new PropertyValueFactory<Subsystem_List, String>("subsystemDate"));
		subsystemTable.setItems(subsystemData);
	}

	private void initMainFunctionModelTable() {
		mainfunctionTable.setEditable(false);
		mainfunctionIdColumn
				.setCellValueFactory(new PropertyValueFactory<Mainfunction_List, Integer>("mainfunctionId"));
		mainfunctionDescColumn
				.setCellValueFactory(new PropertyValueFactory<Mainfunction_List, String>("mainfunctionDesc"));
		mainfunctionStatusColumn
				.setCellValueFactory(new PropertyValueFactory<Mainfunction_List, String>("mainfunctionStatus"));
		mainfunctionDateColumn
				.setCellValueFactory(new PropertyValueFactory<Mainfunction_List, String>("mainfunctionDate"));
		mainfunctionTable.setItems(mainfunctionData);
	}

	private void initSymptomTable() {
		symptomTable.setEditable(false);
		symptomIDColumn.setCellValueFactory(new PropertyValueFactory<Symptom_List, Integer>("symptomId"));
		componentIDColumn.setCellValueFactory(new PropertyValueFactory<Symptom_List, Integer>("componentId"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Symptom_List, String>("description"));
		minLimitColumn.setCellValueFactory(new PropertyValueFactory<Symptom_List, Double>("minLimit"));
		maxLimitColumn.setCellValueFactory(new PropertyValueFactory<Symptom_List, Double>("maxLimit"));
		symptomTable.setItems(symptomData);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initModelTables();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FRSRunningLog.fxml"));
			runninglog_root = (Parent) fxmlLoader.load();
			runninglog_stage.setTitle("Running Log");
			runninglog_stage.setScene(new Scene(runninglog_root));
			runninglog_stage.initStyle(StageStyle.DECORATED);
			runninglog_stage.setResizable(false);

			canvas_panel.setStyle("-fx-background-color: white");
			banner_panel.setStyle("-fx-background-color: white");
			anlage_img.setStyle("-fx-border-color: #848484; -fx-border-width: 1;");
			anlage_img.setEffect(new DropShadow(4d, +3d, +3d, Color.GRAY));

			final ToggleSwitch auto_run_toggle = new ToggleSwitch("Auto Running");
			auto_run_toggle.setLayoutY(23);
			auto_run_toggle.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
				@Override
				public void handle(javafx.scene.input.MouseEvent e) {
					if (auto_run_toggle.isSelected()) {
						problemDiagnoseController.start();
						centerController.start();
					} else {
						problemDiagnoseController.stop();
						centerController.stop();
					}
				}
			});
			Pane togglePane = new Pane();
			togglePane.getChildren().add(auto_run_toggle);
			system_controll_vbox.getChildren().add(togglePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MainApp getApplication() {
		return application;
	}

	public void setApplication(MainApp application) {
		this.application = application;
	}

	public void selectTabPane(int index) {
		mainTabPane.getSelectionModel().select(index);
	}

	@FXML
	private void reset_database(ActionEvent event) {
		centerController.resetDatabase();
	}

	@FXML
	private void open_running_log(ActionEvent event) {
		if (runninglog_stage.isShowing()) {
			runninglog_stage.hide();
		} else {
			runninglog_stage.show();
		}
	}

	@FXML
	private void problem_diagnose(ActionEvent event) {
		selectTabPane(1);
	}

	@FXML
	private void system_info(ActionEvent event) {
		selectTabPane(2);
	}

	@FXML
	private void system_graphic(ActionEvent event) {
		selectTabPane(3);
	}

	@FXML
	private void ats_model(ActionEvent event) {
		selectTabPane(4);
	}

	@FXML
	private void database_handler(ActionEvent event) {
		selectTabPane(5);
	}

	@FXML
	private void demonstration(ActionEvent event) {
		selectTabPane(6);
	}

	@FXML
	private void check_faults(ActionEvent event) {
		problemDiagnoseController.checkFault();
	}

	@FXML
	private void detected_faults(ActionEvent event) {
		selectTabPane(1);
		problem_diagnose_TabPane.getSelectionModel().select(2);
	}

	@FXML
	private void load_data(ActionEvent event) {
		problemDiagnoseController.loadData();
	}

	@FXML
	private void load_framework_file(ActionEvent event) {

	}

	@FXML
	private void export_framework_file(ActionEvent event) {

	}
  
}
