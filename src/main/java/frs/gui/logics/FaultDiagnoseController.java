package frs.gui.logics;

import frs.gui.FRSGUISetting;
import frs.gui.FRSHttpRequestHandler;
import frs.gui.controllers.DemonstrationController;
import frs.gui.controllers.SetpointsViewController;
import frs.gui.controllers.SystemInfoViewController;
import frs.gui.views.FRSMainGUIController;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class FaultDiagnoseController {

    private FRSHttpRequestHandler FDSRequester = new FRSHttpRequestHandler(FRSGUISetting.FDSAddress);

    private FRSMainGUIController FDSMainGUIController;

    int currentFunction;
    int currentTask;
    long timestamp;
    JSONArray allAbfComponents, mSymptoms, mFaultProcedureInfos;
    JSONObject componentValueContainer;

    SystemInfoViewController systemInfoController;
    SetpointsViewController desiredValueController;
    DemonstrationController demonstrationController;

    Timer timer = new Timer();

    public FaultDiagnoseController(FRSMainGUIController FDSMainGUIController, AnchorPane setpoint_panel, AnchorPane systeminfo_panel, AnchorPane demonstration_panel) {
        this.FDSMainGUIController = FDSMainGUIController;
        systemInfoController = new SystemInfoViewController(systeminfo_panel);
        desiredValueController = new SetpointsViewController(setpoint_panel);
        demonstrationController = new DemonstrationController(this, demonstration_panel);
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
            mFaultProcedureInfos = FDSRequester.getAnalysisProcedure();
            currentFunction = componentValueContainer.getInt("function_id");
            currentTask = componentValueContainer.getInt("task_id");
            timestamp = Long.valueOf(componentValueContainer.getString("stamp_time"));
            allAbfComponents = componentValueContainer.getJSONArray("components");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    systemInfoController.refresh(allAbfComponents, currentFunction);
                    desiredValueController.refresh(allAbfComponents, currentFunction);
                    demonstrationController.checkFault(mFaultProcedureInfos);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkFault() {
        try {
            mFaultProcedureInfos = FDSRequester.getAnalysisProcedure();

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
                currentFunction = componentValueContainer.getInt("process_id");
                timestamp = Long.valueOf(componentValueContainer.getString("stamp_time"));
                allAbfComponents = componentValueContainer.getJSONArray("components");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        systemInfoController.refresh(allAbfComponents, currentFunction);
                        desiredValueController.refresh(allAbfComponents, currentFunction);
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
