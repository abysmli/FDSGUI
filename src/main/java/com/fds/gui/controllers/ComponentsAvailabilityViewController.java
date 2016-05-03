package com.fds.gui.controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ComponentsAvailabilityViewController {

	private JSONObject mAllDataTable;
	AnchorPane components_availability_panel;
	boolean firstRunFlag = true;

	public ComponentsAvailabilityViewController(AnchorPane components_availability_panel, JSONObject mAllDataTable) {
		this.components_availability_panel = components_availability_panel;
		this.mAllDataTable = mAllDataTable;
	}

	public void refresh() {
		Platform.runLater(new Runnable() {
			public void run() {
				components_availability_panel.getChildren().clear();
				JSONArray mComponents = mAllDataTable.getJSONArray("Components");
				final GridPane grid = new GridPane();
				grid.setPadding(new Insets(30, 30, 30, 30));
				grid.setVgap(20);
				grid.setHgap(20);
				grid.setMinWidth(1000);
				AnchorPane.setTopAnchor(grid, 0.0);
				AnchorPane.setRightAnchor(grid, 0.0);
				AnchorPane.setLeftAnchor(grid, 0.0);
				AnchorPane.setBottomAnchor(grid, 0.0);
				components_availability_panel.getChildren().add(grid);

				final Label caption = new Label("Components Availability");
				caption.setStyle("-fx-font-weight: bold; -fx-font-family: Verdana; -fx-font-size: 20px");
				GridPane.setConstraints(caption, 0, 0);
				GridPane.setColumnSpan(caption, 6);
				grid.getChildren().add(caption);

				final Separator sepTitle = new Separator();
				sepTitle.setValignment(VPos.CENTER);
				GridPane.setConstraints(sepTitle, 0, 1);
				GridPane.setColumnSpan(sepTitle, 6);
				grid.getChildren().add(sepTitle);

				final Label componentIDLabel = new Label("ID");
				componentIDLabel.setStyle("-fx-font-weight: bold;");
				GridPane.setConstraints(componentIDLabel, 0, 2);
				grid.getChildren().add(componentIDLabel);

				final Label componentNameLabel = new Label("Component Name");
				componentNameLabel.setStyle("-fx-font-weight: bold");
				GridPane.setConstraints(componentNameLabel, 1, 2);
				grid.getChildren().add(componentNameLabel);

				final Label SeriesLabel = new Label("Series");
				SeriesLabel.setStyle("-fx-font-weight: bold");
				SeriesLabel.setMinWidth(120);
				GridPane.setConstraints(SeriesLabel, 2, 2);
				grid.getChildren().add(SeriesLabel);

				final Label TypeLabel = new Label("Type");
				TypeLabel.setStyle("-fx-font-weight: bold");
				TypeLabel.setMinWidth(120);
				GridPane.setConstraints(TypeLabel, 3, 2);
				grid.getChildren().add(TypeLabel);

				final Label ActivitionLabel = new Label("Aktivition");
				ActivitionLabel.setStyle("-fx-font-weight: bold");
				ActivitionLabel.setMinWidth(120);
				GridPane.setConstraints(ActivitionLabel, 4, 2);
				grid.getChildren().add(ActivitionLabel);

				final Label StatusLabel = new Label("Status");
				StatusLabel.setStyle("-fx-font-weight: bold");
				StatusLabel.setMinWidth(120);
				GridPane.setConstraints(StatusLabel, 5, 2);
				grid.getChildren().add(StatusLabel);

				final AnchorPane componentIDPane = new AnchorPane();
				GridPane.setConstraints(componentIDPane, 0, 3);
				grid.getChildren().add(componentIDPane);

				final AnchorPane componentNamePane = new AnchorPane();
				GridPane.setConstraints(componentNamePane, 1, 3);
				grid.getChildren().add(componentNamePane);

				final AnchorPane SeriesPane = new AnchorPane();
				GridPane.setConstraints(SeriesPane, 2, 3);
				grid.getChildren().add(SeriesPane);

				final AnchorPane TypePane = new AnchorPane();
				GridPane.setConstraints(TypePane, 3, 3);
				grid.getChildren().add(TypePane);

				final AnchorPane ActivitionPane = new AnchorPane();
				GridPane.setConstraints(ActivitionPane, 4, 3);
				grid.getChildren().add(ActivitionPane);

				final AnchorPane StatusPane = new AnchorPane();
				GridPane.setConstraints(StatusPane, 5, 3);
				grid.getChildren().add(StatusPane);

				for (int i = 0; i < mComponents.length(); i++) {
					JSONObject component = mComponents.getJSONObject(i);

					Label componentIDValue = new Label();
					componentIDValue.setText(String.valueOf(component.getInt("component_id")));
					componentIDValue.setLayoutX(0);
					componentIDValue.setLayoutY(i * 20);

					Label componentNameValue = new Label();
					componentNameValue.setText(component.getString("component_name"));
					componentNameValue.setLayoutX(0);
					componentNameValue.setLayoutY(i * 20);

					Label SeriesValue = new Label();
					SeriesValue.setText(component.getString("series"));
					SeriesValue.setLayoutX(0);
					SeriesValue.setLayoutY(i * 20);

					Label TypeValue = new Label();
					TypeValue.setText(component.getString("type"));
					TypeValue.setLayoutX(0);
					TypeValue.setLayoutY(i * 20);

					Label ActivitionValue = new Label();
					ActivitionValue.setText(component.getString("activition"));
					ActivitionValue.setLayoutX(0);
					ActivitionValue.setLayoutY(i * 20);

					Label StatusValue = new Label();
					StatusValue.setText(component.getString("status"));
					StatusValue.setLayoutX(0);
					StatusValue.setLayoutY(i * 20);

					componentIDPane.getChildren().add(componentIDValue);
					componentNamePane.getChildren().add(componentNameValue);
					SeriesPane.getChildren().add(SeriesValue);
					TypePane.getChildren().add(TypeValue);
					ActivitionPane.getChildren().add(ActivitionValue);
					StatusPane.getChildren().add(StatusValue);
				}
			}
		});
	}

}
