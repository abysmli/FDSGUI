package frs.gui.controllers;

import frs.gui.logics.RuntimeDataController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class DemonstrationController {

    AnchorPane demonstration_panel;
    ScrollPane demonstration_scroll_panel;
    Pane animation_panel;
    Pane function_analysis_panel;
    Pane requirement_analysis_panel;
    VBox animation_vbox;
    VBox demo_vbox;
    HBox animation_hbox;
    Label requirementTitleLabel;
    Label functionTitleLabel;
    JSONArray mAnalysisProcedureInfos;
    JSONObject lastProcedureInfo = new JSONObject();
    RuntimeDataController runtimeDataController;

    Map FunctionAnalysisBlocks = new HashMap();
    List<Integer> functionLevelList = new ArrayList<>();
    List<Map<String, Integer>> lineEndpoint = new ArrayList<>();
    Map RequirementAnalysisBlocks = new HashMap();
    List<Integer> requirementLevelList = new ArrayList<>();
    List<Map<String, Integer>> requirementlineEndpoint = new ArrayList<>();

    Timer timer = new Timer();
    Timer functionShowTimer = new Timer();
    Timer requirementShowTimer = new Timer();

    boolean firstRunFlag = true;

    private JSONObject faultObj;
    private JSONArray dumpInfo;
    private JSONObject faultLocalizationObj;
    private JSONObject functionAnalysisObj;
    private JSONObject reconfigurationObj;
    private JSONObject resultObj;

    int animationDelay = 100;
    int demoDelay = 500;

    public DemonstrationController(RuntimeDataController runtimeDataController,
            AnchorPane demonstration_panel, ScrollPane demonstration_scroll_panel) {
        this.runtimeDataController = runtimeDataController;
        this.demonstration_panel = demonstration_panel;
        this.demonstration_scroll_panel = demonstration_scroll_panel;
        this.demonstration_scroll_panel.setVvalue(1.0);
        Button forwardButton = new Button("Forword", new Glyph("FontAwesome", FontAwesome.Glyph.FORWARD));
        forwardButton.setMinWidth(100);
        forwardButton.setMinHeight(30);
        forwardButton.setLayoutX(30);
        forwardButton.setLayoutY(20);
        forwardButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                resetTimer();
                resetfunctionShowTimer();
                resetrequirementShowTimer();
                animation_vbox.getChildren().clear();
                function_analysis_panel.getChildren().clear();
                requirement_analysis_panel.getChildren().clear();
                functionTitleLabel.setVisible(false);
                requirementTitleLabel.setVisible(false);
                if (dumpInfo.length() != 0) {
                    for (int step = 0; step < dumpInfo.length(); step++) {
                        showDemo(step);
                    }
                }
            }
        });
        demonstration_panel.getChildren().add(forwardButton);

        Button replayButton = new Button("Replay", new Glyph("FontAwesome", FontAwesome.Glyph.REFRESH));
        replayButton.setMinWidth(100);
        replayButton.setMinHeight(30);
        replayButton.setLayoutX(150);
        replayButton.setLayoutY(20);
        replayButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                resetTimer();
                resetfunctionShowTimer();
                resetrequirementShowTimer();
                functionTitleLabel.setVisible(false);
                requirementTitleLabel.setVisible(false);
                if (lastProcedureInfo.length() != 0) {
                    animation();
                }
            }
        });
        demonstration_panel.getChildren().add(replayButton);

        animation_panel = new Pane();
        animation_panel.setLayoutX(30);
        animation_panel.setLayoutY(70);
        demonstration_panel.getChildren().add(animation_panel);
        animation_hbox = new HBox(10);
        animation_hbox.setPadding(new Insets(10, 10, 10, 10));

        animation_vbox = new VBox(10);
        animation_vbox.setPadding(new Insets(10, 10, 10, 10));
        animation_vbox.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                demonstration_scroll_panel.setHvalue((Double) newValue);
            }
        });

        functionTitleLabel = new Label("Function Analysis Demostration");
        functionTitleLabel.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 18));
        requirementTitleLabel = new Label("Requirement Analysis Demostration");
        requirementTitleLabel.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 18));
        functionTitleLabel.setVisible(false);
        requirementTitleLabel.setVisible(false);

        demo_vbox = new VBox(10);
        function_analysis_panel = new Pane();
        function_analysis_panel.setLayoutX(10);
        function_analysis_panel.setLayoutY(10);
        requirement_analysis_panel = new Pane();
        requirement_analysis_panel.setLayoutX(10);
        requirement_analysis_panel.setLayoutY(10);

        demo_vbox.getChildren().add(functionTitleLabel);
        demo_vbox.getChildren().add(function_analysis_panel);
        demo_vbox.getChildren().add(new Separator());
        demo_vbox.getChildren().add(requirementTitleLabel);
        demo_vbox.getChildren().add(requirement_analysis_panel);

        animation_hbox.getChildren().add(animation_vbox);
        animation_hbox.getChildren().add(demo_vbox);
        animation_panel.getChildren().add(animation_hbox);
        for (int i = 0; i < 100; i++) {
            functionLevelList.add(0);
        }
        lineEndpoint = Arrays.asList(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
        for (int i = 0; i < 100; i++) {
            requirementLevelList.add(0);
        }
        requirementlineEndpoint = Arrays.asList(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public void check(JSONArray mAnalysisProcedureInfos) {
        if (firstRunFlag) {
            this.mAnalysisProcedureInfos = mAnalysisProcedureInfos;
            firstRunFlag = false;
//            lastProcedureInfo = new JSONObject(mAnalysisProcedureInfos.getJSONObject(mAnalysisProcedureInfos.length() - 1).toString());
//            parseAnalysisProcedure();
//            showAlert();
        } else {
            if (!this.mAnalysisProcedureInfos.toString().equals(mAnalysisProcedureInfos.toString())
                    && mAnalysisProcedureInfos.length() != 0) {
                this.mAnalysisProcedureInfos = mAnalysisProcedureInfos;
                lastProcedureInfo = new JSONObject(mAnalysisProcedureInfos.getJSONObject(mAnalysisProcedureInfos.length() - 1).toString());
                parseAnalysisProcedure();
                showAlert();
            }
        }
    }

    private void parseAnalysisProcedure() {
        faultObj = new JSONObject(lastProcedureInfo.getString("fault_info"));
        dumpInfo = new JSONArray(lastProcedureInfo.getString("dump_info"));
        faultLocalizationObj = new JSONObject(lastProcedureInfo.getString("fault_localization_info"));
        functionAnalysisObj = new JSONObject(lastProcedureInfo.getString("function_analysis_info").replace("\n", "").replace("\r", ""));
        reconfigurationObj = new JSONObject(lastProcedureInfo.getString("reconfiguration_info"));
        resultObj = new JSONObject(lastProcedureInfo.getString("result"));

    }

    private void animation() {
        animation_vbox.getChildren().clear();
        function_analysis_panel.getChildren().clear();
        requirement_analysis_panel.getChildren().clear();
        timer.scheduleAtFixedRate(new TimerTask() {
            private int step = 0;

            @Override
            public void run() {
                if (step < dumpInfo.length()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showDemo(step++);
                        }
                    });
                } else {
                    resetTimer();
                }
            }
        }, 1000, animationDelay);
    }

    private void showAlert() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(faultObj.getString("fault_info"));
        alert.setHeaderText("An fault detected on IAS-Abfuellanlage!");
        alert.setContentText("Fault Effect: " + faultObj.getJSONObject("fault_object").getString("fault_effect")
                + "\nFault Value: " + faultObj.getJSONObject("fault_object").getString("fault_value")
                + "\nFault Name: " + faultObj.getJSONObject("fault_object").getString("fault_name")
                + "\nFault Parameter: " + faultObj.getJSONObject("fault_object").getString("fault_parameter")
                + "\nFault Message: " + faultObj.getJSONObject("fault_object").getString("fault_message")
                + "\nFault Type: " + faultObj.getJSONObject("fault_object").getString("fault_type"));

        ButtonType buttonShowDemonstartion = new ButtonType("Show Demonstration", ButtonData.CANCEL_CLOSE);
        ButtonType buttonClose = new ButtonType("Close", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonShowDemonstartion, buttonClose);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonShowDemonstartion) {
            runtimeDataController.selectTabPane(6);
            animation();
        }
    }

    private void showDemo(int step) {
        if (dumpInfo.getJSONObject(step).getString(String.valueOf(step + 1)).substring(0, 3).equals("Now")) {
            animation_vbox.getChildren().add(new Separator());
        }
        Label StepLabel = new Label(dumpInfo.getJSONObject(step).getString(String.valueOf(step + 1)));
        if (dumpInfo.getJSONObject(step).getString(String.valueOf(step + 1)).substring(0, 3).equals("Now") || step == 0) {
            StepLabel.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 15));
        } else {
            StepLabel.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 12));
        }
        animation_vbox.getChildren().add(StepLabel);

        Label ObjectLabel = new Label();
        ObjectLabel.setMaxWidth(500);
        ObjectLabel.setWrapText(true);
        switch (step) {
            case 6:
                ObjectLabel.setText(faultLocalizationObj.getJSONObject("symptom").toString());
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            case 9:
                functionAnalysisAnimation();
                break;
            case 61:
                ObjectLabel.setText(functionAnalysisObj.getJSONArray("basic_functions").toString());
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            case 62:
                ObjectLabel.setText(functionAnalysisObj.getJSONArray("sub_functions").toString());
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            case 63:
                ObjectLabel.setText(functionAnalysisObj.getJSONArray("main_functions").toString());
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            case 70:
                ObjectLabel.setText(reconfigurationObj.getJSONObject("redundanz").toString());
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            case 72:
                ObjectLabel.setText("Restart: " + reconfigurationObj.getString("restart"));
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            case 73:
                ObjectLabel.setText(reconfigurationObj.getJSONObject("reconf_functions").toString());
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            case 74:
                ObjectLabel.setText(reconfigurationObj.getJSONArray("reconf_systemchange").toString());
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            case 96:
                ObjectLabel.setText(reconfigurationObj.getJSONArray("task_list").toString());
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            case 99:
                ObjectLabel.setText(reconfigurationObj.getJSONObject("personal_data").toString());
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            case 102:
                ObjectLabel.setText(faultObj.toString());
                animation_vbox.getChildren().add(ObjectLabel);
                break;
            default:
                break;

        }
    }

    private void resetTimer() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
    }

    private void resetfunctionShowTimer() {
        functionShowTimer.cancel();
        functionShowTimer.purge();
        functionShowTimer = new Timer();
    }

    private void resetrequirementShowTimer() {
        requirementShowTimer.cancel();
        requirementShowTimer.purge();
        requirementShowTimer = new Timer();
    }

    private void functionAnalysisAnimation() {
        function_analysis_panel.getChildren().clear();
        functionShowTimer.scheduleAtFixedRate(new TimerTask() {
            private int step = 0;

            @Override
            public void run() {
                if (step < functionAnalysisObj.getJSONArray("function_analysis").length()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showFunctionAnalysisDemo(step++, functionAnalysisObj.getJSONArray("function_analysis").getJSONObject(step - 1));
                        }
                    });
                } else {
                    resetfunctionShowTimer();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            requirementAnalysisAnimation();
                        }
                    });
                }
            }
        }, 10, demoDelay);
    }

    private void requirementAnalysisAnimation() {
        requirement_analysis_panel.getChildren().clear();
        requirementShowTimer.scheduleAtFixedRate(new TimerTask() {
            private int step = 0;

            @Override
            public void run() {
                if (step < functionAnalysisObj.getJSONArray("requirement_analysis").length()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showRequirementAnalysisDemo(step++, functionAnalysisObj.getJSONArray("requirement_analysis").getJSONObject(step - 1));
                        }

                    });
                } else {
                    resetrequirementShowTimer();
                }
            }
        }, 10, demoDelay);
    }

    private void showFunctionAnalysisDemo(int step, JSONObject functionObj) {
        if (step == 0) {
            functionTitleLabel.setVisible(true);
        }
        if (FunctionAnalysisBlocks.containsKey(functionObj.getString("id"))) {
            VBox block = (VBox) FunctionAnalysisBlocks.get(functionObj.getString("id"));
            switch (functionObj.getString("result")) {
                case "unknown":
                    block.setStyle("-fx-background-color: #DAA520; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                case "true":
                    block.setStyle("-fx-background-color: #00FF7F; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                case "false":
                    block.setStyle("-fx-background-color: #FA8072; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                default:
                    block.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
            }
            int blockCount = functionLevelList.get(functionObj.getInt("level"));
            Label RuleLabel = (Label) block.getChildren().get(4);
            RuleLabel.setText("RULE: " + functionObj.getString("rule"));
            Label ResultLabel = (Label) block.getChildren().get(6);
            ResultLabel.setText("RESULT: " + functionObj.getString("result"));

            Map<String, Integer> mEndpoint = new HashMap<>();
            mEndpoint.put("X", (blockCount - 1) * 200 + 90);
            mEndpoint.put("Y", (functionObj.getInt("level") - 1) * 300 + 180);
            lineEndpoint.set(functionObj.getInt("level"), mEndpoint);

            if (!lineEndpoint.get(functionObj.getInt("level") - 1).isEmpty()) {
                Line line = new Line();
                int lineStartX = (blockCount - 1) * 200 + 90;
                int lineStartY = (functionObj.getInt("level") - 1) * 300;
                line.setStartX(lineStartX);
                line.setStartY(lineStartY);
                int lineEndX = lineEndpoint.get(functionObj.getInt("level") - 1).get("X");
                int lineEndY = lineEndpoint.get(functionObj.getInt("level") - 1).get("Y");
                line.setEndX(lineEndX);
                line.setEndY(lineEndY);
                function_analysis_panel.getChildren().add(line);
            }

        } else {
            VBox block = new VBox(10);

            functionLevelList.set(functionObj.getInt("level"), functionLevelList.get(functionObj.getInt("level")) + 1);
            int blockCount = functionLevelList.get(functionObj.getInt("level"));
            block.setLayoutX((blockCount - 1) * 200);
            block.setLayoutY((functionObj.getInt("level") - 1) * 300);

            block.setPrefWidth(180);
            block.setPrefHeight(180);
            block.setEffect(new DropShadow(4d, +3d, +3d, Color.GRAY));
            switch (functionObj.getString("result")) {
                case "unknown":
                    block.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                case "true":
                    block.setStyle("-fx-background-color: #00FF7F; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                case "false":
                    block.setStyle("-fx-background-color: #FA8072; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                default:
                    block.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
            }
            block.setPadding(new Insets(10, 10, 10, 10));

            Label IDLabel = new Label(functionObj.getString("id"));
            IDLabel.setWrapText(true);
            IDLabel.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 15));
            
            Label NameLabel = new Label(functionObj.getJSONObject("function_info").getString("desc"));
            NameLabel.setWrapText(true);
            NameLabel.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 11));

            Label RuleLabel = new Label("RULE: " + functionObj.getString("rule"));
            RuleLabel.setWrapText(true);
            RuleLabel.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 11));

            Label ResultLabel = new Label("RESULT: " + functionObj.getString("result"));
            ResultLabel.setWrapText(true);
            ResultLabel.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 11));

            block.getChildren().add(IDLabel);
            block.getChildren().add(new Separator());
            block.getChildren().add(NameLabel);
            block.getChildren().add(new Separator());
            block.getChildren().add(RuleLabel);
            block.getChildren().add(new Separator());
            block.getChildren().add(ResultLabel);
            FunctionAnalysisBlocks.put(functionObj.getString("id"), block);

            function_analysis_panel.getChildren().add(block);

            Map<String, Integer> mEndpoint = new HashMap<>();
            mEndpoint.put("X", (blockCount - 1) * 200 + 90);
            mEndpoint.put("Y", (functionObj.getInt("level") - 1) * 300 + 180);
            lineEndpoint.set(functionObj.getInt("level"), mEndpoint);

            if (!lineEndpoint.get(functionObj.getInt("level") - 1).isEmpty()) {
                Line line = new Line();
                int lineStartX = (blockCount - 1) * 200 + 90;
                int lineStartY = (functionObj.getInt("level") - 1) * 300;
                line.setStartX(lineStartX);
                line.setStartY(lineStartY);
                int lineEndX = lineEndpoint.get(functionObj.getInt("level") - 1).get("X");
                int lineEndY = lineEndpoint.get(functionObj.getInt("level") - 1).get("Y");
                line.setEndX(lineEndX);
                line.setEndY(lineEndY);
                function_analysis_panel.getChildren().add(line);
            }

        }
    }

    private void showRequirementAnalysisDemo(int step, JSONObject requirementObj) {
        if (step == 0) {
            requirementTitleLabel.setVisible(true);
        }
        if (RequirementAnalysisBlocks.containsKey(requirementObj.getString("id"))) {
            VBox block = (VBox) RequirementAnalysisBlocks.get(requirementObj.getString("id"));
            switch (requirementObj.getString("result")) {
                case "unknown":
                    block.setStyle("-fx-background-color: #DAA520; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                case "true":
                    block.setStyle("-fx-background-color: #00FF7F; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                case "false":
                    block.setStyle("-fx-background-color: #FA8072; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                default:
                    block.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
            }
            int blockCount = requirementLevelList.get(requirementObj.getInt("level"));
            Label RuleLabel = (Label) block.getChildren().get(4);
            RuleLabel.setText("RULE: " + requirementObj.getString("rule"));
            Label ResultLabel = (Label) block.getChildren().get(6);
            ResultLabel.setText("RESULT: " + requirementObj.getString("result"));

            Map<String, Integer> mEndpoint = new HashMap<>();
            mEndpoint.put("X", (blockCount - 1) * 200 + 90);
            mEndpoint.put("Y", (requirementObj.getInt("level") - 1) * 300 + 180);
            requirementlineEndpoint.set(requirementObj.getInt("level"), mEndpoint);

            if (!lineEndpoint.get(requirementObj.getInt("level") - 1).isEmpty()) {
                Line line = new Line();
                int lineStartX = (blockCount - 1) * 200 + 90;
                int lineStartY = (requirementObj.getInt("level") - 1) * 300;
                line.setStartX(lineStartX);
                line.setStartY(lineStartY);
                int lineEndX = lineEndpoint.get(requirementObj.getInt("level") - 1).get("X");
                int lineEndY = lineEndpoint.get(requirementObj.getInt("level") - 1).get("Y");
                line.setEndX(lineEndX);
                line.setEndY(lineEndY);
                requirement_analysis_panel.getChildren().add(line);
            }

        } else {
            VBox block = new VBox(10);

            requirementLevelList.set(requirementObj.getInt("level"), requirementLevelList.get(requirementObj.getInt("level")) + 1);
            int blockCount = requirementLevelList.get(requirementObj.getInt("level"));
            block.setLayoutX((blockCount - 1) * 200);
            block.setLayoutY((requirementObj.getInt("level") - 1) * 300);

            block.setPrefWidth(180);
            block.setPrefHeight(180);
            block.setEffect(new DropShadow(4d, +3d, +3d, Color.GRAY));
            switch (requirementObj.getString("result")) {
                case "unknown":
                    block.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                case "true":
                    block.setStyle("-fx-background-color: #00FF7F; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                case "false":
                    block.setStyle("-fx-background-color: #FA8072; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
                default:
                    block.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10; -fx-border-radius: 10 10 0 0; -fx-background-radius: 10 10 0 0;");
                    break;
            }
            block.setPadding(new Insets(10, 10, 10, 10));

            Label IDLabel = new Label(requirementObj.getString("id"));
            IDLabel.setWrapText(true);
            IDLabel.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 15));

            Label NameLabel = new Label(requirementObj.getJSONObject("function_info").getString("desc"));
            NameLabel.setWrapText(true);
            NameLabel.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 11));
            
            Label RuleLabel = new Label("RULE: " + requirementObj.getString("rule"));
            RuleLabel.setWrapText(true);
            RuleLabel.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 11));

            Label ResultLabel = new Label("RESULT: " + requirementObj.getString("result"));
            ResultLabel.setWrapText(true);
            ResultLabel.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 11));

            block.getChildren().add(IDLabel);
            block.getChildren().add(new Separator());
            block.getChildren().add(NameLabel);
            block.getChildren().add(new Separator());
            block.getChildren().add(RuleLabel);
            block.getChildren().add(new Separator());
            block.getChildren().add(ResultLabel);
            RequirementAnalysisBlocks.put(requirementObj.getString("id"), block);

            requirement_analysis_panel.getChildren().add(block);

            Map<String, Integer> mEndpoint = new HashMap<>();
            mEndpoint.put("X", (blockCount - 1) * 200 + 90);
            mEndpoint.put("Y", (requirementObj.getInt("level") - 1) * 300 + 180);
            requirementlineEndpoint.set(requirementObj.getInt("level"), mEndpoint);

            if (!requirementlineEndpoint.get(requirementObj.getInt("level") - 1).isEmpty()) {
                Line line = new Line();
                int lineStartX = (blockCount - 1) * 200 + 90;
                int lineStartY = (requirementObj.getInt("level") - 1) * 300;
                line.setStartX(lineStartX);
                line.setStartY(lineStartY);
                int lineEndX = requirementlineEndpoint.get(requirementObj.getInt("level") - 1).get("X");
                int lineEndY = requirementlineEndpoint.get(requirementObj.getInt("level") - 1).get("Y");
                line.setEndX(lineEndX);
                line.setEndY(lineEndY);
                requirement_analysis_panel.getChildren().add(line);
            }

        }
    }
}
