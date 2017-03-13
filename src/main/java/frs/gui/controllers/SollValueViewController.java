package frs.gui.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SollValueViewController {

    AnchorPane setpoint_panel;
    boolean firstRunFlag = true;
    List<Label> sensorLabelsContainer = new ArrayList<>();
    List<Label> sensorRateLabelsContainer = new ArrayList<>();
    List<Label> actorLabelsContainer = new ArrayList<>();
    List<Label> ventilLabelsContainer = new ArrayList<>();
    List<Label> schalterLabelsContainer = new ArrayList<>();
    List<Label> controllerLabelsContainer = new ArrayList<>();
    Label process;

    public SollValueViewController(AnchorPane setpoint_panel) {
        this.setpoint_panel = setpoint_panel;
    }

    private void generate(JSONArray allAbfComponents) {
        final GridPane grid = new GridPane();
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setVgap(20);
        grid.setHgap(20);
        grid.setMinWidth(1000);
        AnchorPane.setTopAnchor(grid, 0.0);
        AnchorPane.setRightAnchor(grid, 0.0);
        AnchorPane.setLeftAnchor(grid, 0.0);
        AnchorPane.setBottomAnchor(grid, 0.0);
        setpoint_panel.getChildren().add(grid);

        final Label caption = new Label("Setpoints Check of Problem Diagnose Module");
        caption.setStyle("-fx-font-weight: bold; -fx-font-family: Verdana; -fx-font-size: 20px");
        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 5);
        grid.getChildren().add(caption);

        process = new Label("");
        process.setStyle("-fx-font-weight: bold; -fx-font-family: Verdana; -fx-font-size: 16px");
        GridPane.setConstraints(process, 5, 0);
        GridPane.setColumnSpan(process, 4);
        grid.getChildren().add(process);

        final Separator sepTitle = new Separator();
        sepTitle.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepTitle, 0, 1);
        GridPane.setColumnSpan(sepTitle, 9);
        grid.getChildren().add(sepTitle);

        final Label sensorNameLabel = new Label("Sensor Name");
        sensorNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(sensorNameLabel, 0, 2);
        grid.getChildren().add(sensorNameLabel);

        final Label sensorValueLabel = new Label("Current Value");
        sensorValueLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(sensorValueLabel, 1, 2);
        grid.getChildren().add(sensorValueLabel);

        final Label sensorRateLabel = new Label("Current Rate");
        sensorRateLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(sensorRateLabel, 2, 2);
        grid.getChildren().add(sensorRateLabel);

        final Label sensorValueSetpointLabel = new Label("Setpoint Value");
        sensorValueSetpointLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(sensorValueSetpointLabel, 3, 2);
        grid.getChildren().add(sensorValueSetpointLabel);

        final Label sensorRateSetpointLabel = new Label("Setpoint Rate");
        sensorRateSetpointLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(sensorRateSetpointLabel, 4, 2);
        grid.getChildren().add(sensorRateSetpointLabel);

        final Separator sepMid1 = new Separator();
        sepMid1.setOrientation(Orientation.VERTICAL);
        sepMid1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepMid1, 5, 2);
        GridPane.setRowSpan(sepMid1, 2);
        grid.getChildren().add(sepMid1);

        final Label actorNameLabel = new Label("Actor Name");
        actorNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(actorNameLabel, 6, 2);
        grid.getChildren().add(actorNameLabel);

        final Label actorValueLabel = new Label("Current Value");
        actorValueLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(actorValueLabel, 7, 2);
        grid.getChildren().add(actorValueLabel);

        final Label actorValueSetpointLabel = new Label("Setpoint Value");
        actorValueSetpointLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(actorValueSetpointLabel, 8, 2);
        grid.getChildren().add(actorValueSetpointLabel);

        final AnchorPane sensorPane = new AnchorPane();
        GridPane.setConstraints(sensorPane, 0, 3);
        grid.getChildren().add(sensorPane);

        final AnchorPane sensorValuePane = new AnchorPane();
        GridPane.setConstraints(sensorValuePane, 1, 3);
        grid.getChildren().add(sensorValuePane);

        final AnchorPane sensorChangeRatePane = new AnchorPane();
        GridPane.setConstraints(sensorChangeRatePane, 2, 3);
        grid.getChildren().add(sensorChangeRatePane);

        final AnchorPane sensorValueSetpointPane = new AnchorPane();
        GridPane.setConstraints(sensorValueSetpointPane, 3, 3);
        grid.getChildren().add(sensorValueSetpointPane);

        final AnchorPane sensorChangeRateSetpointPane = new AnchorPane();
        GridPane.setConstraints(sensorChangeRateSetpointPane, 4, 3);
        grid.getChildren().add(sensorChangeRateSetpointPane);

        final AnchorPane actorPane = new AnchorPane();
        GridPane.setConstraints(actorPane, 6, 3);
        grid.getChildren().add(actorPane);

        final AnchorPane actorValuePane = new AnchorPane();
        GridPane.setConstraints(actorValuePane, 7, 3);
        grid.getChildren().add(actorValuePane);

        final AnchorPane actorValueSetpointPane = new AnchorPane();
        GridPane.setConstraints(actorValueSetpointPane, 8, 3);
        grid.getChildren().add(actorValueSetpointPane);

        final Separator sepRow1 = new Separator();
        sepRow1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepRow1, 0, 4);
        GridPane.setColumnSpan(sepRow1, 9);
        grid.getChildren().add(sepRow1);

        final Label ventilNameLabel = new Label("Ventil Name");
        ventilNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(ventilNameLabel, 0, 5);
        grid.getChildren().add(ventilNameLabel);

        final Label ventilValueLabel = new Label("Current Value");
        ventilValueLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(ventilValueLabel, 1, 5);
        grid.getChildren().add(ventilValueLabel);

        final Label ventilSetpointLabel = new Label("Setpoint Value");
        ventilSetpointLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(ventilSetpointLabel, 2, 5);
        GridPane.setColumnSpan(ventilSetpointLabel, 3);
        grid.getChildren().add(ventilSetpointLabel);

        final Separator sepMid2 = new Separator();
        sepMid2.setOrientation(Orientation.VERTICAL);
        sepMid2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepMid2, 5, 5);
        GridPane.setRowSpan(sepMid2, 2);
        grid.getChildren().add(sepMid2);

        final Label schalterNameLabel = new Label("Schalter Name");
        schalterNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(schalterNameLabel, 6, 5);
        grid.getChildren().add(schalterNameLabel);

        final Label schalterValueLabel = new Label("Current Value");
        schalterValueLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(schalterValueLabel, 7, 5);
        grid.getChildren().add(schalterValueLabel);

        final Label schalterSetpointLabel = new Label("Setpoint Value");
        schalterSetpointLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(schalterSetpointLabel, 8, 5);
        grid.getChildren().add(schalterSetpointLabel);

        final AnchorPane ventilPane = new AnchorPane();
        GridPane.setConstraints(ventilPane, 0, 6);
        grid.getChildren().add(ventilPane);

        final AnchorPane ventilValuePane = new AnchorPane();
        GridPane.setConstraints(ventilValuePane, 1, 6);
        grid.getChildren().add(ventilValuePane);

        final AnchorPane ventilValueSetpointPane = new AnchorPane();
        GridPane.setConstraints(ventilValueSetpointPane, 2, 6);
        GridPane.setColumnSpan(ventilValueSetpointPane, 3);
        grid.getChildren().add(ventilValueSetpointPane);

        final AnchorPane schalterPane = new AnchorPane();
        GridPane.setConstraints(schalterPane, 6, 6);
        grid.getChildren().add(schalterPane);

        final AnchorPane schalterValuePane = new AnchorPane();
        GridPane.setConstraints(schalterValuePane, 7, 6);
        grid.getChildren().add(schalterValuePane);

        final AnchorPane schalterValueSetpointPane = new AnchorPane();
        GridPane.setConstraints(schalterValueSetpointPane, 8, 6);
        grid.getChildren().add(schalterValueSetpointPane);

        final Separator sepRow2 = new Separator();
        sepRow2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepRow2, 0, 7);
        GridPane.setColumnSpan(sepRow2, 9);
        grid.getChildren().add(sepRow2);

        final Label controllerNameLabel = new Label("Micro Controller Name");
        controllerNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(controllerNameLabel, 0, 8);
        grid.getChildren().add(controllerNameLabel);

        final Label controllerValueLabel = new Label("Current Value");
        controllerValueLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(controllerValueLabel, 1, 8);
        grid.getChildren().add(controllerValueLabel);

        final Label controllerSetpointLabel = new Label("Setpoint Value");
        controllerSetpointLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(controllerSetpointLabel, 2, 8);
        GridPane.setColumnSpan(controllerSetpointLabel, 3);
        grid.getChildren().add(controllerSetpointLabel);

        final AnchorPane controllerPane = new AnchorPane();
        GridPane.setConstraints(controllerPane, 0, 9);
        grid.getChildren().add(controllerPane);

        final AnchorPane controllerValuePane = new AnchorPane();
        GridPane.setConstraints(controllerValuePane, 1, 9);
        grid.getChildren().add(controllerValuePane);

        final AnchorPane controllerValueSetpointPane = new AnchorPane();
        GridPane.setConstraints(controllerValueSetpointPane, 2, 9);
        GridPane.setColumnSpan(controllerValueSetpointPane, 3);
        grid.getChildren().add(controllerValueSetpointPane);

        int sensorCount = 0, actorCount = 0, ventilCount = 0, schalterCount = 0, MCCount = 0;
        for (int i = 0; i < allAbfComponents.length(); i++) {
            JSONObject component = allAbfComponents.getJSONObject(i);
            if (component.getString("type").equals("sensor")) {
                Label componentLabel = new Label();
                componentLabel.setText(component.getString("component_name") + ": ");
                componentLabel.setLayoutX(0);
                componentLabel.setLayoutY(sensorCount * 20);
                Label componentValueLabel = new Label();
                if (component.isNull("value")) {
                    componentValueLabel.setText("-");
                } else {
                    componentValueLabel.setText(String.format("%.2f", component.getDouble("value")));
                }
                componentValueLabel.setLayoutX(0);
                componentValueLabel.setLayoutY(sensorCount * 20);
                sensorLabelsContainer.add(componentValueLabel);
                Label componentChangeRageLabel = new Label();
                if (component.isNull("change_rate")) {
                    componentChangeRageLabel.setText("-");
                } else {
                    componentChangeRageLabel.setText(String.format("%.2f", component.getDouble("change_rate")));
                }
                componentChangeRageLabel.setLayoutX(0);
                componentChangeRageLabel.setLayoutY(sensorCount * 20);
                sensorRateLabelsContainer.add(componentChangeRageLabel);

                Label componentValueSetpointLabel = new Label();
                componentValueSetpointLabel.setText("-");
                componentValueSetpointLabel.setLayoutX(0);
                componentValueSetpointLabel.setLayoutY(sensorCount * 20);
                Label componentChangeRageSetpointLabel = new Label();
                componentChangeRageSetpointLabel.setText("-");
                componentChangeRageSetpointLabel.setLayoutX(0);
                componentChangeRageSetpointLabel.setLayoutY(sensorCount * 20);

                sensorPane.getChildren().add(componentLabel);
                sensorValuePane.getChildren().add(componentValueLabel);
                sensorChangeRatePane.getChildren().add(componentChangeRageLabel);
                sensorValueSetpointPane.getChildren().add(componentValueSetpointLabel);
                sensorChangeRateSetpointPane.getChildren().add(componentChangeRageSetpointLabel);
                sensorCount++;
            } else if (component.getString("type").equals("actor")) {
                Label componentLabel = new Label();
                componentLabel.setText(component.getString("component_name") + ": ");
                componentLabel.setLayoutX(0);
                componentLabel.setLayoutY(actorCount * 20);
                Label componentValueLabel = new Label();
                componentValueLabel.setText("-");
                componentValueLabel.setLayoutX(0);
                componentValueLabel.setLayoutY(actorCount * 20);
                actorLabelsContainer.add(componentValueLabel);

                Label componentValueSetpointLabel = new Label();
                componentValueSetpointLabel.setText("-");
                componentValueSetpointLabel.setLayoutX(0);
                componentValueSetpointLabel.setLayoutY(actorCount * 20);

                actorPane.getChildren().add(componentLabel);
                actorValuePane.getChildren().add(componentValueLabel);
                actorValueSetpointPane.getChildren().add(componentValueSetpointLabel);
                actorCount++;
            } else if (component.getString("type").equals("ventil")) {
                Label componentLabel = new Label();
                componentLabel.setText(component.getString("component_name") + ": ");
                componentLabel.setLayoutX(0);
                componentLabel.setLayoutY(ventilCount * 20);
                Label componentValueLabel = new Label();
                componentValueLabel.setText("-");
                componentValueLabel.setLayoutX(0);
                componentValueLabel.setLayoutY(ventilCount * 20);
                ventilLabelsContainer.add(componentValueLabel);

                Label componentValueSetpointLabel = new Label();
                componentValueSetpointLabel.setText("-");
                componentValueSetpointLabel.setLayoutX(0);
                componentValueSetpointLabel.setLayoutY(ventilCount * 20);

                ventilPane.getChildren().add(componentLabel);
                ventilValuePane.getChildren().add(componentValueLabel);
                ventilValueSetpointPane.getChildren().add(componentValueSetpointLabel);
                ventilCount++;
            } else if (component.getString("type").equals("schalter")) {
                Label componentLabel = new Label();
                componentLabel.setText(component.getString("component_name") + ": ");
                componentLabel.setLayoutX(0);
                componentLabel.setLayoutY(schalterCount * 20);
                Label componentValueLabel = new Label();
                componentValueLabel.setText("-");
                componentValueLabel.setLayoutX(0);
                componentValueLabel.setLayoutY(schalterCount * 20);
                schalterLabelsContainer.add(componentValueLabel);

                Label componentValueSetpointLabel = new Label();
                componentValueSetpointLabel.setText("-");
                componentValueSetpointLabel.setLayoutX(0);
                componentValueSetpointLabel.setLayoutY(schalterCount * 20);

                schalterPane.getChildren().add(componentLabel);
                schalterValuePane.getChildren().add(componentValueLabel);
                schalterValueSetpointPane.getChildren().add(componentValueSetpointLabel);
                schalterCount++;
            } else if (component.getString("type").equals("MC")) {
                Label componentLabel = new Label();
                componentLabel.setText(component.getString("component_name") + ": ");
                componentLabel.setLayoutX(0);
                componentLabel.setLayoutY(MCCount * 20);
                Label componentValueLabel = new Label();
                componentValueLabel.setText("-");
                componentValueLabel.setLayoutX(0);
                componentValueLabel.setLayoutY(MCCount * 20);
                controllerLabelsContainer.add(componentValueLabel);

                Label componentValueSetpointLabel = new Label();
                componentValueSetpointLabel.setText("-");
                componentValueSetpointLabel.setLayoutX(0);
                componentValueSetpointLabel.setLayoutY(MCCount * 20);

                controllerPane.getChildren().add(componentLabel);
                controllerValuePane.getChildren().add(componentValueLabel);
                controllerValueSetpointPane.getChildren().add(componentValueSetpointLabel);
                MCCount++;
            }
        }
    }

    public void refresh(JSONArray allAbfComponents, int function_id, int task_id) {
        if (firstRunFlag) {
            generate(allAbfComponents);
            firstRunFlag = false;
        } else {
            displayProcess(function_id, task_id);
            int sensorCount = 0, actorCount = 0, ventilCount = 0, schalterCount = 0, MCCount = 0;
            for (int i = 0; i < allAbfComponents.length(); i++) {
                JSONObject component = allAbfComponents.getJSONObject(i);
                if (component.getString("type").equals("sensor")) {
                    if (component.isNull("value")) {
                        sensorLabelsContainer.get(sensorCount).setText("-");
                    } else {
                        sensorLabelsContainer.get(sensorCount).setText(String.format("%.2f", component.getDouble("value")));
                    }
                    if (component.isNull("change_rate")) {
                        sensorRateLabelsContainer.get(sensorCount).setText("-");
                    } else {
                        sensorRateLabelsContainer.get(sensorCount).setText(String.format("%.2f", component.getDouble("change_rate")));
                    }
                    sensorCount++;
                }
                if (component.getString("type").equals("actor")) {
                    if (component.isNull("value")) {
                        actorLabelsContainer.get(actorCount).setText("-");
                    } else {
                        actorLabelsContainer.get(actorCount).setText(component.getString("value"));
                    }
                    actorCount++;
                }
                if (component.getString("type").equals("ventil")) {
                    if (component.isNull("value")) {
                        ventilLabelsContainer.get(ventilCount).setText("-");
                    } else {
                        ventilLabelsContainer.get(ventilCount).setText(component.getString("value"));
                    }
                    ventilCount++;
                }
                if (component.getString("type").equals("schalter")) {
                    if (component.isNull("value")) {
                        schalterLabelsContainer.get(schalterCount).setText("-");
                    } else {
                        schalterLabelsContainer.get(schalterCount).setText(component.getString("value"));
                    }
                    schalterCount++;
                }
                if (component.getString("type").equals("MC")) {
                    if (component.isNull("value")) {
                        controllerLabelsContainer.get(MCCount).setText("-");
                    } else {
                        controllerLabelsContainer.get(MCCount).setText(component.getString("value"));
                    }
                    MCCount++;
                }
            }
        }
    }

    private void displayProcess(int function_id, int task_id) {
        String currentRunningInfo = new String();
        switch (function_id) {
            case 1:
                currentRunningInfo = "Function: Air Pumping, ";
                break;
            case 2:
                currentRunningInfo = "Function: Filling, ";
                break;
            case 3:
                currentRunningInfo = "Function: Heating, ";
                break;
            case 4:
                currentRunningInfo = "Function: Pumping, ";
                break;
            default:
                currentRunningInfo = "Function: Stop, ";
                break;
        }

        switch (task_id) {
            case 1:
                currentRunningInfo += "Task: Automatic Cycling 30°C";
                break;
            case 2:
                currentRunningInfo += "Task: Heat 3L,45°C Water";
                break;
            case 3:
                currentRunningInfo += "Task: Pour 5L Water";
                break;
            case 4:
                currentRunningInfo += "Task: Clean Pipe";
                break;
            default:
                currentRunningInfo += "Task: Stop";
                break;
        }
        process.setText(currentRunningInfo);
    }
}
