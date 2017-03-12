package frs.gui.controllers;

import frs.gui.logics.FaultDiagnoseController;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class DemonstrationController {
	AnchorPane demonstration_panel;
	Pane animation_panel;
	JSONArray mFaultProcedureInfos;
	JSONArray lastProcedureInfo = new JSONArray();
	FaultDiagnoseController problemDiagnoseController;
	Timer timer = new Timer();

	boolean firstRunFlag = true;

	public DemonstrationController(FaultDiagnoseController problemDiagnoseController,
			AnchorPane demonstration_panel) {
		this.problemDiagnoseController = problemDiagnoseController;
		this.demonstration_panel = demonstration_panel;

		Button forwardButton = new Button("Forword", new Glyph("FontAwesome", FontAwesome.Glyph.FORWARD));
		forwardButton.setMinWidth(100);
		forwardButton.setMinHeight(30);
		forwardButton.setLayoutX(30);
		forwardButton.setLayoutY(20);
		forwardButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				resetTimer();
				animation_panel.getChildren().clear();
				if (lastProcedureInfo.length() != 0) {
					for (int step = 0; step < lastProcedureInfo.length(); step++) {
						createBlock(step, lastProcedureInfo.getJSONObject(step));
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
				if (lastProcedureInfo.length() != 0) {
					animation();
				}
			}
		});
		demonstration_panel.getChildren().add(replayButton);

		animation_panel = new Pane();
		animation_panel.setLayoutX(30);
		animation_panel.setLayoutY(70);
                animation_panel.setStyle("-fx-background-color: #795548;");
		demonstration_panel.getChildren().add(animation_panel);
	}

	public void checkFault(JSONArray mFaultProcedureInfos) {
		if (firstRunFlag) {
			this.mFaultProcedureInfos = mFaultProcedureInfos;
			firstRunFlag = false;
		} else {
			if (!this.mFaultProcedureInfos.toString().equals(mFaultProcedureInfos.toString())
					&& mFaultProcedureInfos.length() != 0) {
				this.mFaultProcedureInfos = mFaultProcedureInfos;
				lastProcedureInfo = new JSONArray(mFaultProcedureInfos.getJSONObject(mFaultProcedureInfos.length() - 1)
						.getString("diagnose_procedure"));
				warning(mFaultProcedureInfos.getJSONObject(mFaultProcedureInfos.length() - 1).getInt("component_id"),
						mFaultProcedureInfos.getJSONObject(mFaultProcedureInfos.length() - 1).getString("series"),
						mFaultProcedureInfos.getJSONObject(mFaultProcedureInfos.length() - 1).getString("fault_type"),
						mFaultProcedureInfos.getJSONObject(mFaultProcedureInfos.length() - 1).getString("fault_desc"));
			}
		}
	}

	private void animation() {
		animation_panel.getChildren().clear();
		timer.scheduleAtFixedRate(new TimerTask() {
			private int step = 0;

			@Override
			public void run() {
				if (step < lastProcedureInfo.length()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							createBlock(step, lastProcedureInfo.getJSONObject(step++));
						}
					});
				} else {
					resetTimer();
				}
			}
		}, 500, 1000);
	}

	private void warning(int componentId, String series, String faultType, String faultDesc) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Fault Detected");
		alert.setHeaderText("An fault detected on IAS-Abfuellanlage!");
		alert.setContentText("Component ID: " + componentId + "\nSeries: " + series + "\nFault Type: " + faultType
				+ "\nFault Description: " + faultDesc);

		ButtonType buttonShowDemonstartion = new ButtonType("Show Demonstration", ButtonData.CANCEL_CLOSE);
		ButtonType buttonClose = new ButtonType("Close", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonShowDemonstartion, buttonClose);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonShowDemonstartion) {
			problemDiagnoseController.selectTabPane(6);
			animation();
		}
	}

	private void createBlock(int step, JSONObject procedureInfo) {
		VBox block = new VBox(20);
		block.setLayoutX(step % 4 * 300);
		block.setLayoutY(step / 4 * 300);
		block.setPrefWidth(250);
		block.setPrefHeight(250);
		block.setEffect(new DropShadow(4d, +3d, +3d, Color.GRAY));
		if (step < 2) {
			block.setStyle(
					"-fx-background-color: #CECEF6; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10;");
		} else {
			block.setStyle(
					"-fx-background-color: #F5ECCE; -fx-border-color: #848484; -fx-border-width: 1; -fx-padding: 10 10 10 10;");
		}

		block.setPadding(new Insets(10, 10, 10, 10));

		Label StepLabel = new Label("STEP: " + procedureInfo.getInt("step"));
		StepLabel.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 18));

		Label PositionLabel = new Label("POSITION: " + procedureInfo.getString("Position"));
		PositionLabel.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 13));

		Label DoLabel = new Label("DO: " + procedureInfo.getString("Do"));
		DoLabel.setPrefWidth(230);
		DoLabel.setWrapText(true);
		DoLabel.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 13));

		Label ResultLabel = new Label("RESULT: " + procedureInfo.getString("Result"));
		ResultLabel.setWrapText(true);
		ResultLabel.setPrefWidth(230);
		ResultLabel.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 13));

		block.getChildren().add(StepLabel);
		block.getChildren().add(PositionLabel);
		block.getChildren().add(DoLabel);
		block.getChildren().add(ResultLabel);

		animation_panel.getChildren().add(block);
	}

	private void resetTimer() {
		timer.cancel();
		timer.purge();
		timer = new Timer();
	}
}
