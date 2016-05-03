package com.fds.gui.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

public class ComponentModelViewController {
	AnchorPane pane;
	JSONObject mTables;
	Map<Integer, List<Integer>> componentModel;
	Map<Integer, List<Integer>> functionModel;
	
	double bigBoxLabelHeight = 40;
	double marginOfBigBox = 50.0;
	double smallBoxWidth = 60.0;
	double smallBoxHeight = 30.0;
	double marginOfBoxHorizontal = 10;
	double marginOfBoxVertical = 10;
	int numberOfBoxInRow = 5;
	
	public ComponentModelViewController(AnchorPane pane, JSONObject mTables) {
		this.pane = pane;
		pane.setPadding(new Insets(20,20,20,20));
		this.mTables = mTables;
	}

	public void draw() {
		parseModel();
		Platform.runLater(new Runnable() {
			public void run() {
				pane.getChildren().clear();
				JSONArray mSubsystems = mTables.getJSONArray("Subsystems");
				JSONArray mSubfunctions = mTables.getJSONArray("Subfunctions");
				JSONArray mComponents = mTables.getJSONArray("Components");
				JSONArray mFunctions = mTables.getJSONArray("Functions");

				double bigBoxVerticalPosition = 10, bigBoxHeight = 0, bigBoxWidth = (numberOfBoxInRow+1) * marginOfBoxHorizontal +numberOfBoxInRow * smallBoxWidth;
				for (Map.Entry<Integer, List<Integer>> subsystemEntry : componentModel.entrySet()) {
					Label subsystemLabel = new Label();
					subsystemLabel.setText("Subsystem " + subsystemEntry.getKey());
					if (mSubsystems.getJSONObject(subsystemEntry.getKey() - 1).getString("status").equals("inactive")) {
						subsystemLabel.setStyle(
								"-fx-background-color: pink; -fx-border-color: lightgrey; -fx-border-width: 2; -fx-padding: 10 10 10 10; -fx-alignment:top-left;");
					} else {
						subsystemLabel.setStyle(
								"-fx-background-color: lightcyan; -fx-border-color: lightgrey; -fx-border-width: 2; -fx-padding: 10 10 10 10; -fx-alignment:top-left;");
					}
					bigBoxVerticalPosition += bigBoxHeight + marginOfBoxVertical;
					bigBoxHeight = (smallBoxHeight + marginOfBoxVertical) * roundUp(subsystemEntry.getValue().size(), numberOfBoxInRow) + bigBoxLabelHeight;
					subsystemLabel.setMinHeight(bigBoxHeight);
					subsystemLabel.setMinWidth(bigBoxWidth);
					AnchorPane.setLeftAnchor(subsystemLabel, marginOfBigBox);
					AnchorPane.setTopAnchor(subsystemLabel, bigBoxVerticalPosition);
					subsystemLabel.setTooltip(new Tooltip("Subsystem: " + mSubsystems.getJSONObject(subsystemEntry.getKey()-1).getString("subsystem_desc")));
					pane.getChildren().add(subsystemLabel);
					for (int i = 0; i < subsystemEntry.getValue().size(); i++) {
						Label componentLabel = new Label();
						componentLabel.setText("C " + subsystemEntry.getValue().get(i));
						if (mComponents.getJSONObject(subsystemEntry.getValue().get(i) - 1).getString("status")
								.equals("inactive")) {
							componentLabel.setStyle(
									"-fx-background-color: red; -fx-border-color: gold; -fx-border-width: 2; -fx-alignment: center;");
						} else {
							componentLabel.setStyle(
									"-fx-background-color: lemonchiffon; -fx-border-color: gold; -fx-border-width: 2; -fx-alignment: center;");
						}
						componentLabel.setMinHeight(smallBoxHeight);
						componentLabel.setMinWidth(smallBoxWidth);
						AnchorPane.setLeftAnchor(componentLabel, marginOfBigBox + marginOfBoxHorizontal + i % numberOfBoxInRow * (marginOfBoxHorizontal + smallBoxWidth));
						AnchorPane.setTopAnchor(componentLabel, bigBoxLabelHeight + i / numberOfBoxInRow * (marginOfBoxVertical + smallBoxHeight) + bigBoxVerticalPosition);
						JSONObject obj = mComponents.getJSONObject(subsystemEntry.getValue().get(i) - 1);
						componentLabel.setTooltip(new Tooltip("Component ID: " + obj.getInt("component_id") + "\nComponent Name: "
								+ obj.getString("component_name") + "\nComponent Desc: "
								+ obj.getString("component_desc") + "\nSeries: " + obj.getString("series")
								+ "\nType: " + obj.getString("type") + "\nActivition: "
								+ obj.getString("activition") + "\nStatus: " + obj.getString("status")));
						pane.getChildren().add(componentLabel);
					}
				}

				bigBoxVerticalPosition = 10;
				bigBoxHeight = 0;
				for (Map.Entry<Integer, List<Integer>> subfunctionEntry : functionModel.entrySet()) {
					Label subfunctionLabel = new Label();
					subfunctionLabel.setText("Subfunction " + subfunctionEntry.getKey());
					if (mSubfunctions.getJSONObject(subfunctionEntry.getKey() - 1).getString("status").equals("inactive")) {
						subfunctionLabel.setStyle(
								"-fx-background-color: pink; -fx-border-color: lightgrey; -fx-border-width: 2; -fx-padding: 10 10 10 10; -fx-alignment:top-left;");
					} else {
						subfunctionLabel.setStyle(
								"-fx-background-color: honeydew; -fx-border-color: lightgrey; -fx-border-width: 2; -fx-padding: 10 10 10 10; -fx-alignment:top-left;");
					}
					bigBoxVerticalPosition += bigBoxHeight + marginOfBoxVertical;
					bigBoxHeight = (smallBoxHeight + marginOfBoxVertical) * roundUp(subfunctionEntry.getValue().size(), numberOfBoxInRow) + bigBoxLabelHeight;
					subfunctionLabel.setMinHeight(bigBoxHeight);
					subfunctionLabel.setMinWidth(bigBoxWidth);
					AnchorPane.setLeftAnchor(subfunctionLabel, marginOfBigBox*2 + bigBoxWidth);
					AnchorPane.setTopAnchor(subfunctionLabel, bigBoxVerticalPosition);
					subfunctionLabel.setTooltip(new Tooltip("Subfunction: " + mSubfunctions.getJSONObject(subfunctionEntry.getKey()-1).getString("subfunction_desc")));
					pane.getChildren().add(subfunctionLabel);
					for (int i = 0; i < subfunctionEntry.getValue().size(); i++) {
						Label functionLabel = new Label();
						functionLabel.setText("F " + subfunctionEntry.getValue().get(i));
						if (mFunctions.getJSONObject(subfunctionEntry.getValue().get(i) - 1).getString("status")
								.equals("inactive")) {
							functionLabel.setStyle(
									"-fx-background-color: red; -fx-border-color: gold; -fx-border-width: 2; -fx-alignment: center;");
						} else {
							functionLabel.setStyle(
									"-fx-background-color: lemonchiffon; -fx-border-color: gold; -fx-border-width: 2; -fx-alignment: center;");
						}
						functionLabel.setMinHeight(smallBoxHeight);
						functionLabel.setMinWidth(smallBoxWidth);
						AnchorPane.setLeftAnchor(functionLabel, marginOfBigBox*2 + bigBoxWidth + marginOfBoxHorizontal + i % numberOfBoxInRow * (marginOfBoxHorizontal + smallBoxWidth));
						AnchorPane.setTopAnchor(functionLabel, bigBoxLabelHeight + i / numberOfBoxInRow * (marginOfBoxVertical + smallBoxHeight) + bigBoxVerticalPosition);
						JSONObject obj = mFunctions.getJSONObject(subfunctionEntry.getValue().get(i) - 1);
						functionLabel.setTooltip(new Tooltip("Function ID: " + obj.getInt("function_id") + "\nFunction Desc: "
								+ obj.getString("function_desc") + "\nStatus: " + obj.getString("status")));
						pane.getChildren().add(functionLabel);
					}
				}
			}
		});
	}

	private void parseModel() {
		componentModel = new HashMap<Integer, List<Integer>>();
		JSONArray mSubsystemComponentRel = mTables.getJSONArray("SubsystemComponentRel");
		for (int i = 0; i < mSubsystemComponentRel.length(); i++) {
			boolean flag = true;
			for (Integer subsystemID : componentModel.keySet()) {
				if (subsystemID == mSubsystemComponentRel.getJSONObject(i).getInt("subsystem_id")) {
					componentModel.get(subsystemID).add(mSubsystemComponentRel.getJSONObject(i).getInt("component_id"));
					flag = false;
				}
			}
			if (flag) {
				List<Integer> componentIDs = new ArrayList<>();
				componentIDs.add(mSubsystemComponentRel.getJSONObject(i).getInt("component_id"));
				componentModel.put(mSubsystemComponentRel.getJSONObject(i).getInt("subsystem_id"), componentIDs);
			}
		}

		functionModel = new HashMap<Integer, List<Integer>>();
		JSONArray mSubfunctionFunctionRel = mTables.getJSONArray("SubfunctionFunctionRel");
		for (int i = 0; i < mSubfunctionFunctionRel.length(); i++) {
			boolean flag = true;
			for (Integer subfunctionID : functionModel.keySet()) {
				if (subfunctionID == mSubfunctionFunctionRel.getJSONObject(i).getInt("subfunction_id")) {
					functionModel.get(subfunctionID)
							.add(mSubfunctionFunctionRel.getJSONObject(i).getInt("function_id"));
					flag = false;
				}
			}
			if (flag) {
				List<Integer> functionIDs = new ArrayList<>();
				functionIDs.add(mSubfunctionFunctionRel.getJSONObject(i).getInt("function_id"));
				functionModel.put(mSubfunctionFunctionRel.getJSONObject(i).getInt("subfunction_id"), functionIDs);
			}
		}
	}

	private int roundUp(int num, int divisor) {
		return (num + divisor - 1) / divisor;
	}
}
