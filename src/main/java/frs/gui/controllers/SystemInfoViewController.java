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

public class SystemInfoViewController {

    final GridPane grid = new GridPane();
    AnchorPane systeminfo_panel;
    boolean firstRunFlag = true;
    List<ChartViewContorller> charts = new ArrayList<ChartViewContorller>();

    List<Label> sensorLabelsContainer = new ArrayList<>();
    List<Label> sensorRateLabelsContainer = new ArrayList<>();
    List<Label> actorLabelsContainer = new ArrayList<>();
    List<Label> ventilLabelsContainer = new ArrayList<>();
    List<Label> schalterLabelsContainer = new ArrayList<>();
    List<Label> controllerLabelsContainer = new ArrayList<>();

    Label process;

    public SystemInfoViewController(AnchorPane systeminfo_panel) {
        this.systeminfo_panel = systeminfo_panel;
    }

    public void refresh(JSONArray allAbfComponents, int function_id, int task_id) {

        if (firstRunFlag) {
            grid.setPadding(new Insets(30, 30, 30, 30));
            grid.setVgap(20);
            grid.setHgap(20);
            grid.setMinWidth(1000);
            AnchorPane.setTopAnchor(grid, 0.0);
            AnchorPane.setRightAnchor(grid, 0.0);
            AnchorPane.setLeftAnchor(grid, 0.0);
            AnchorPane.setBottomAnchor(grid, 0.0);
            systeminfo_panel.getChildren().add(grid);

            generateInfos(allAbfComponents);
            generateCharts(allAbfComponents);
        } else {
            updateInfos(allAbfComponents, function_id, task_id);
            updateChart(allAbfComponents);
        }
    }

    private void updateInfos(JSONArray allAbfComponents, int function_id, int task_id) {
        displayProcess(function_id, task_id);
        int sensorCount = 0, actorCount = 0, ventilCount = 0, schalterCount = 0, MCCount = 0;
        for (int i = 0; i < allAbfComponents.length(); i++) {
            JSONObject component = allAbfComponents.getJSONObject(i);
            if (component.getString("type").equals("sensor")) {
                if (component.isNull("value")) {
                    sensorLabelsContainer.get(MCCount).setText("-");
                } else {
                    sensorLabelsContainer.get(sensorCount).setText(String.format("%.2f", component.getDouble("value")));
                }
                if (component.isNull("change_rate")) {
                    sensorRateLabelsContainer.get(MCCount).setText("-");
                } else {
                    sensorRateLabelsContainer.get(sensorCount).setText(String.format("%.2f", component.getDouble("change_rate")));
                }
                sensorCount++;
            }
            if (component.getString("type").equals("actor")) {
                if (component.isNull("value")) {
                    actorLabelsContainer.get(MCCount).setText("-");
                } else {
                    actorLabelsContainer.get(MCCount).setText(component.getString("value"));
                }
                actorCount++;
            }
            if (component.getString("type").equals("ventil")) {
                if (component.isNull("value")) {
                    ventilLabelsContainer.get(MCCount).setText("-");
                } else {

                    ventilLabelsContainer.get(MCCount).setText(component.getString("value"));
                }
                ventilCount++;
            }
            if (component.getString("type").equals("schalter")) {
                if (component.isNull("value")) {
                    schalterLabelsContainer.get(MCCount).setText("-");
                } else {
                    schalterLabelsContainer.get(MCCount).setText(component.getString("value"));
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

    private void generateCharts(JSONArray allAbfComponents) {
        int chart_num = 0;
        for (int i = 0; i < allAbfComponents.length(); i++) {
            JSONObject Component = allAbfComponents.getJSONObject(i);
            if (Component.getString("type").equals("sensor")) {
                final AnchorPane chartsPane = new AnchorPane();
                GridPane.setConstraints(chartsPane, chart_num % 2 * 4, 10 + chart_num / 2);
                GridPane.setColumnSpan(chartsPane, 4 + chart_num % 2);
                grid.getChildren().add(chartsPane);
                charts.add(new ChartViewContorller(chartsPane, Component.getString("component_name"), "time 1/s",
                        "Signal", 60, 0, 0, 530, 400));
                chart_num++;
            }
        }
        firstRunFlag = false;
    }

    ;

	private void generateInfos(JSONArray allAbfComponents) {
        final Label caption = new Label("System Informations");
        caption.setStyle("-fx-font-weight: bold; -fx-font-family: Verdana; -fx-font-size: 20px");
        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 4);
        grid.getChildren().add(caption);

        process = new Label("");
        process.setStyle("-fx-font-weight: bold; -fx-font-family: Verdana; -fx-font-size: 16px");
        GridPane.setConstraints(process, 4, 0);
        GridPane.setColumnSpan(process, 5);
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

        final Separator sepMid1 = new Separator();
        sepMid1.setOrientation(Orientation.VERTICAL);
        sepMid1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepMid1, 3, 2);
        GridPane.setRowSpan(sepMid1, 2);
        grid.getChildren().add(sepMid1);

        final Label actorNameLabel = new Label("Actor Name");
        actorNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(actorNameLabel, 4, 2);
        grid.getChildren().add(actorNameLabel);

        final Label actorValueLabel = new Label("Current Value");
        actorValueLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(actorValueLabel, 5, 2);
        grid.getChildren().add(actorValueLabel);

        final Separator sepMid11 = new Separator();
        sepMid11.setOrientation(Orientation.VERTICAL);
        sepMid11.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepMid11, 6, 2);
        GridPane.setRowSpan(sepMid11, 2);
        grid.getChildren().add(sepMid11);

        final Label ventilNameLabel = new Label("Ventil Name");
        ventilNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(ventilNameLabel, 7, 2);
        grid.getChildren().add(ventilNameLabel);

        final Label ventilValueLabel = new Label("Current Value");
        ventilValueLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(ventilValueLabel, 8, 2);
        grid.getChildren().add(ventilValueLabel);

        final AnchorPane sensorPane = new AnchorPane();
        GridPane.setConstraints(sensorPane, 0, 3);
        grid.getChildren().add(sensorPane);

        final AnchorPane sensorValuePane = new AnchorPane();
        GridPane.setConstraints(sensorValuePane, 1, 3);
        grid.getChildren().add(sensorValuePane);

        final AnchorPane sensorChangeRatePane = new AnchorPane();
        GridPane.setConstraints(sensorChangeRatePane, 2, 3);
        grid.getChildren().add(sensorChangeRatePane);

        final AnchorPane actorPane = new AnchorPane();
        GridPane.setConstraints(actorPane, 4, 3);
        grid.getChildren().add(actorPane);

        final AnchorPane actorValuePane = new AnchorPane();
        GridPane.setConstraints(actorValuePane, 5, 3);
        grid.getChildren().add(actorValuePane);

        final AnchorPane ventilPane = new AnchorPane();
        GridPane.setConstraints(ventilPane, 7, 3);
        grid.getChildren().add(ventilPane);

        final AnchorPane ventilValuePane = new AnchorPane();
        GridPane.setConstraints(ventilValuePane, 8, 3);
        grid.getChildren().add(ventilValuePane);

        final Separator sepRow1 = new Separator();
        sepRow1.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepRow1, 0, 4);
        GridPane.setColumnSpan(sepRow1, 9);
        grid.getChildren().add(sepRow1);

        final Label schalterNameLabel = new Label("Schalter Name");
        schalterNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(schalterNameLabel, 0, 5);
        grid.getChildren().add(schalterNameLabel);

        final Label schalterValueLabel = new Label("Current Value");
        schalterValueLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(schalterValueLabel, 1, 5);
        grid.getChildren().add(schalterValueLabel);

        final Separator sepMid2 = new Separator();
        sepMid2.setOrientation(Orientation.VERTICAL);
        sepMid2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepMid2, 3, 5);
        GridPane.setRowSpan(sepMid2, 2);
        grid.getChildren().add(sepMid2);

        final Label controllerNameLabel = new Label("Micro Controller Name");
        controllerNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(controllerNameLabel, 4, 5);
        grid.getChildren().add(controllerNameLabel);

        final Label controllerValueLabel = new Label("Current Value");
        controllerValueLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(controllerValueLabel, 5, 5);
        grid.getChildren().add(controllerValueLabel);

        final AnchorPane schalterPane = new AnchorPane();
        GridPane.setConstraints(schalterPane, 0, 6);
        grid.getChildren().add(schalterPane);

        final AnchorPane schalterValuePane = new AnchorPane();
        GridPane.setConstraints(schalterValuePane, 1, 6);
        grid.getChildren().add(schalterValuePane);

        final AnchorPane controllerPane = new AnchorPane();
        GridPane.setConstraints(controllerPane, 4, 6);
        grid.getChildren().add(controllerPane);

        final AnchorPane controllerValuePane = new AnchorPane();
        GridPane.setConstraints(controllerValuePane, 5, 6);
        grid.getChildren().add(controllerValuePane);

        final Separator sepRow2 = new Separator();
        sepRow2.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepRow2, 0, 7);
        GridPane.setColumnSpan(sepRow2, 9);
        grid.getChildren().add(sepRow2);

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

                sensorPane.getChildren().add(componentLabel);
                sensorValuePane.getChildren().add(componentValueLabel);
                sensorChangeRatePane.getChildren().add(componentChangeRageLabel);
                sensorCount++;
            } else if (component.getString("type").equals("actor")) {
                Label componentLabel = new Label();
                componentLabel.setText(component.getString("component_name") + ": ");
                componentLabel.setLayoutX(0);
                componentLabel.setLayoutY(actorCount * 20);
                Label componentValueLabel = new Label();
                componentValueLabel.setText("open");
                componentValueLabel.setLayoutX(0);
                componentValueLabel.setLayoutY(actorCount * 20);
                actorLabelsContainer.add(componentValueLabel);

                actorPane.getChildren().add(componentLabel);
                actorValuePane.getChildren().add(componentValueLabel);
                actorCount++;
            } else if (component.getString("type").equals("ventil")) {
                Label componentLabel = new Label();
                componentLabel.setText(component.getString("component_name") + ": ");
                componentLabel.setLayoutX(0);
                componentLabel.setLayoutY(ventilCount * 20);
                Label componentValueLabel = new Label();
                componentValueLabel.setText("open");
                componentValueLabel.setLayoutX(0);
                componentValueLabel.setLayoutY(ventilCount * 20);
                ventilLabelsContainer.add(componentValueLabel);

                ventilPane.getChildren().add(componentLabel);
                ventilValuePane.getChildren().add(componentValueLabel);
                ventilCount++;
            } else if (component.getString("type").equals("schalter")) {
                Label componentLabel = new Label();
                componentLabel.setText(component.getString("component_name") + ": ");
                componentLabel.setLayoutX(0);
                componentLabel.setLayoutY(schalterCount * 20);
                Label componentValueLabel = new Label();
                componentValueLabel.setText("open");
                componentValueLabel.setLayoutX(0);
                componentValueLabel.setLayoutY(schalterCount * 20);
                schalterLabelsContainer.add(componentValueLabel);

                schalterPane.getChildren().add(componentLabel);
                schalterValuePane.getChildren().add(componentValueLabel);
                schalterCount++;
            } else if (component.getString("type").equals("MC")) {
                Label componentLabel = new Label();
                componentLabel.setText(component.getString("component_name") + ": ");
                componentLabel.setLayoutX(0);
                componentLabel.setLayoutY(MCCount * 20);
                Label componentValueLabel = new Label();
                componentValueLabel.setText("open");
                componentValueLabel.setLayoutX(0);
                componentValueLabel.setLayoutY(MCCount * 20);
                controllerLabelsContainer.add(componentValueLabel);

                controllerPane.getChildren().add(componentLabel);
                controllerValuePane.getChildren().add(componentValueLabel);
                MCCount++;
            }
        }
    }

    private void updateChart(JSONArray allAbfComponents) {
        int chart_num = 0;
        for (int i = 0; i < allAbfComponents.length(); i++) {
            JSONObject Component = allAbfComponents.getJSONObject(i);
            if (Component.getString("type").equals("sensor")) {
                if (Component.isNull("change_rate")) {
                    charts.get(chart_num++).update(0.0);
                } else {
                    charts.get(chart_num++).update(Component.getDouble("value"));
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
