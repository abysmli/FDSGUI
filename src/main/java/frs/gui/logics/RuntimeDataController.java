package frs.gui.logics;

import frs.gui.FRSGUISetting;
import frs.gui.FRSHttpRequestHandler;
import frs.gui.controllers.DemonstrationController;
import frs.gui.controllers.SollValueViewController;
import frs.gui.controllers.SystemInfoViewController;
import frs.gui.views.FRSMainGUIController;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class RuntimeDataController {

    private FRSHttpRequestHandler FDSRequester = new FRSHttpRequestHandler(FRSGUISetting.FDSAddress);

    private FRSMainGUIController FDSMainGUIController;

    int currentFunction;
    int currentTask;
    long timestamp;
    JSONArray allAbfComponents, mSymptoms, mAnalysisProcedureInfos;
    JSONObject componentValueContainer;

    SystemInfoViewController systemInfoController;
    SollValueViewController sollValueController;
    DemonstrationController demonstrationController;

    Timer timer = new Timer();

    public RuntimeDataController(FRSMainGUIController FDSMainGUIController, AnchorPane setpoint_panel, AnchorPane systeminfo_panel, AnchorPane demonstration_panel, ScrollPane demonstration_scroll_panel) {
        this.FDSMainGUIController = FDSMainGUIController;
        systemInfoController = new SystemInfoViewController(systeminfo_panel);
        sollValueController = new SollValueViewController(setpoint_panel);
        demonstrationController = new DemonstrationController(this, demonstration_panel, demonstration_scroll_panel);
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
            mAnalysisProcedureInfos = FDSRequester.getAnalysisProcedure();
            currentFunction = componentValueContainer.getInt("function_id");
            currentTask = componentValueContainer.getInt("task_id");
            timestamp = Long.valueOf(componentValueContainer.getString("stamp_time"));
            allAbfComponents = componentValueContainer.getJSONArray("components");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    systemInfoController.refresh(allAbfComponents, currentFunction, currentTask);
                    sollValueController.refresh(allAbfComponents, currentFunction, currentTask);
                    demonstrationController.check(mAnalysisProcedureInfos);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void check() {
        try {
            mAnalysisProcedureInfos = FDSRequester.getAnalysisProcedure();
            demonstrationController.check(mAnalysisProcedureInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            JSONArray metaRuntimeData = FDSRequester.getRuntimeData();
            for (int i = 0; i < metaRuntimeData.length(); i++) {
                componentValueContainer = metaRuntimeData.getJSONObject(i);
                currentFunction = componentValueContainer.getInt("function_id");
                currentTask = componentValueContainer.getInt("task_id");
                timestamp = Long.valueOf(componentValueContainer.getString("stamp_time"));
                allAbfComponents = componentValueContainer.getJSONArray("components");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        systemInfoController.refresh(allAbfComponents, currentFunction, currentTask);
                        sollValueController.refresh(allAbfComponents, currentFunction, currentTask);
                    }
                });
            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Load Anlage Data");
            alert.setHeaderText("Anlage Running Data has been loaded");
            alert.setContentText("Total " + metaRuntimeData.length() + " sets data loaded in system!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
