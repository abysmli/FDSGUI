package frs.gui.logics;

import frs.gui.FRSGUISetting;
import frs.gui.FRSHttpRequestHandler;
import frs.gui.controllers.ComponentModelViewController;
import frs.gui.controllers.ComponentsAvailabilityViewController;
import frs.gui.controllers.DatabaseTableViewController;
import frs.gui.controllers.FunctionsAvailabilityViewController;
import frs.gui.controllers.GraphicViewController;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import frs.gui.models.Component_List;
import frs.gui.models.Fault_List;
import frs.gui.models.Function_List;
import frs.gui.models.Mainfunction_List;
import frs.gui.models.Subfunction_List;
import frs.gui.models.Subsystem_List;
import frs.gui.views.FRSMainGUIController;

import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class FRSCenterController {

    private FRSHttpRequestHandler http = new FRSHttpRequestHandler(FRSGUISetting.FDSAddress);

    private GraphicViewController graphic;
    private ComponentModelViewController componentModel;
    private DatabaseTableViewController modelTable;
    private FunctionsAvailabilityViewController functionAvailability;
    private ComponentsAvailabilityViewController componentAvailability;

    Timer timer = new Timer();
    JSONObject mAllDataTable = new JSONObject();

    public FRSCenterController(FRSMainGUIController fds_gui, Canvas graphic_canvas, AnchorPane canvas_panel,
            AnchorPane components_model_panel, AnchorPane functions_model_panel, AnchorPane requirements_model_panel,
            AnchorPane functions_availability_panel, AnchorPane components_availability_panel,
            ObservableList<Fault_List> faultData, ObservableList<Function_List> functionData,
            ObservableList<Subfunction_List> subfunctionData, ObservableList<Mainfunction_List> mainfunctionData,
            ObservableList<Component_List> componentData, ObservableList<Subsystem_List> subsystemData) {

        graphic = new GraphicViewController(canvas_panel, graphic_canvas, mAllDataTable);
        componentModel = new ComponentModelViewController(components_model_panel, mAllDataTable);
        modelTable = new DatabaseTableViewController(faultData, functionData, subfunctionData, mainfunctionData,
                componentData, subsystemData, mAllDataTable);
        functionAvailability = new FunctionsAvailabilityViewController(functions_availability_panel, mAllDataTable);
        componentAvailability = new ComponentsAvailabilityViewController(components_availability_panel, mAllDataTable);
        update();
    }

    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

            }
        }, 0, 3000);
    }

    public void stop() {
        timer.cancel();
        timer.purge();
    }

    private void update() {
        try {
            getAllDataTables();
            modelTable.refresh();
            graphic.draw();
            componentModel.draw();
            functionAvailability.refresh();
            componentAvailability.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllDataTables() throws JSONException, Exception {
        mAllDataTable.put("Faults", getFaults());
        mAllDataTable.put("Functions", getFunctions());
        mAllDataTable.put("Components", getComponents());
        mAllDataTable.put("Subsystems", getSubsystems());
        mAllDataTable.put("Subfunctions", getSubfunctions());
        mAllDataTable.put("Mainfunctions", getMainfunctions());
        mAllDataTable.put("SubsystemComponentRel", getSubsystemComponentRel());
        mAllDataTable.put("ComponentFunctionRel", getComponentFunctionRel());
        mAllDataTable.put("SubfunctionFunctionRel", getSubfunctionFunctionRel());
        mAllDataTable.put("MainfunctionSubfunctionRel", getMainfunctionSubfunctionRel());
    }

    private JSONArray getSubsystemComponentRel() throws Exception {
        return http.getSubsystemComponentRel();
    }

    private JSONArray getComponentFunctionRel() throws Exception {
        return http.getComponentFunctionRel();
    }

    private JSONArray getSubfunctionFunctionRel() throws Exception {
        return http.getSubfunctionFunctionRel();
    }

    private JSONArray getMainfunctionSubfunctionRel() throws Exception {
        return http.getMainfunctionSubfunctionRel();
    }

    private JSONArray getMainfunctions() throws Exception {
        return http.getMainfunctions();
    }

    private JSONArray getSubsystems() throws Exception {
        return http.getSubsystems();
    }

    private JSONArray getComponents() throws Exception {
        return http.getComponents();
    }

    private JSONArray getFunctions() throws Exception {
        return http.getFunctions();
    }

    private JSONArray getSubfunctions() throws Exception {
        return http.getSubfunctions();
    }

    private JSONArray getFaults() throws Exception {
        return http.getFaults();
    }

    public void resetDatabase() {
        try {
            JSONObject result = http.resetDatabase();
            if (result.getString("result").equals("success")) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Reset Database");
                alert.setHeaderText("Database has been reset!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
