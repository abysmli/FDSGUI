package frs.gui.controllers;

import frs.gui.FRSGUISetting;
import frs.gui.FRSHttpRequestHandler;
import frs.gui.controllers.DemonstrationController;
import frs.gui.controllers.SetpointsViewController;
import frs.gui.controllers.SymptomTableViewController;
import frs.gui.controllers.SymptomsViewController;
import frs.gui.controllers.SystemInfoViewController;
import frs.gui.models.Symptom_List;
import frs.gui.views.FRSMainGUIController;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class FaultDiagnoseController {

	private FRSHttpRequestHandler FDSRequester = new FRSHttpRequestHandler(FRSGUISetting.FDSAddress);

	private FRSMainGUIController FDSMainGUIController;

	ObservableList<Symptom_List> symptomData;

	int currentProcess;
	long timestamp;
	JSONArray allAbfComponents, mSymptoms, mFaultProcedureInfos;
	JSONObject componentValueContainer;

	SystemInfoViewController systemInfoController;
	SetpointsViewController desiredValueController;
	SymptomsViewController symptomsController;
	DemonstrationController demonstrationController;
	SymptomTableViewController symptomTable;

	Timer timer = new Timer();

	public FaultDiagnoseController(FRSMainGUIController FDSMainGUIController, AnchorPane setpoint_panel, AnchorPane systeminfo_panel,
			AnchorPane symptoms_panel, AnchorPane demonstration_panel, ObservableList<Symptom_List> symptomData) {
		this.FDSMainGUIController = FDSMainGUIController;
		systemInfoController = new SystemInfoViewController(systeminfo_panel);
		desiredValueController = new SetpointsViewController(setpoint_panel);
		symptomsController = new SymptomsViewController(symptoms_panel);
		demonstrationController = new DemonstrationController(this, demonstration_panel);
		symptomTable = new SymptomTableViewController(symptomData);
		update();
	}

	public void selectTabPane(int index) {
		FDSMainGUIController.selectTabPane(index);
	}

	public void start() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 0, 1000);
	}

	public void stop() {
		timer.cancel();
		timer.purge();
	}

	public void update() {
		try {
			mSymptoms = FDSRequester.getSymptoms();
			componentValueContainer = FDSRequester.getLastRuntimeData();
			mFaultProcedureInfos = FDSRequester.getFaultProcedureInfos();
			currentProcess = componentValueContainer.getInt("process_id");
			timestamp = Long.valueOf(componentValueContainer.getString("stamp_time"));
			allAbfComponents = componentValueContainer.getJSONArray("components");
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					symptomTable.generate(mSymptoms);
					symptomsController.generate(mSymptoms);
					systemInfoController.refresh(allAbfComponents, currentProcess);
					desiredValueController.refresh(allAbfComponents, currentProcess);
					demonstrationController.checkFault(mFaultProcedureInfos);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkFault() {
		try {
			mFaultProcedureInfos = FDSRequester.getFaultProcedureInfos();
			
			demonstrationController.checkFault(mFaultProcedureInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadData() {
		try {
			JSONArray metaComponentValue = FDSRequester.getRuntimeData();
			for (int i = 0; i < metaComponentValue.length(); i++) {
				componentValueContainer = metaComponentValue.getJSONObject(i);
				currentProcess = componentValueContainer.getInt("process_id");
				timestamp = Long.valueOf(componentValueContainer.getString("stamp_time"));
				allAbfComponents = componentValueContainer.getJSONArray("components");
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						systemInfoController.refresh(allAbfComponents, currentProcess);
						desiredValueController.refresh(allAbfComponents, currentProcess);
					}
				});
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Load Anlage Data");
			alert.setHeaderText("Anlage Running Data has been loaded");
			alert.setContentText("Total " + metaComponentValue.length() + " sets data loaded in system!");
			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
