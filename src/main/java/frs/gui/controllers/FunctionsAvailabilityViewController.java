package frs.gui.controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class FunctionsAvailabilityViewController {

	private JSONObject mAllDataTable;
	AnchorPane functions_availability_panel;
	boolean firstRunFlag = true;

	public FunctionsAvailabilityViewController(AnchorPane functions_availability_panel, JSONObject mAllDataTable) {
		this.functions_availability_panel = functions_availability_panel;
		this.mAllDataTable = mAllDataTable;
	}

	public void refresh() {
		Platform.runLater(new Runnable() {
			public void run() {
				functions_availability_panel.getChildren().clear();
				JSONArray mFunctions = mAllDataTable.getJSONArray("Functions");
				final GridPane grid = new GridPane();
				grid.setPadding(new Insets(30, 30, 30, 30));
				grid.setVgap(20);
				grid.setHgap(20);
				grid.setMinWidth(1000);
				AnchorPane.setTopAnchor(grid, 0.0);
				AnchorPane.setRightAnchor(grid, 0.0);
				AnchorPane.setLeftAnchor(grid, 0.0);
				AnchorPane.setBottomAnchor(grid, 0.0);
				functions_availability_panel.getChildren().add(grid);

				final Label caption = new Label("Functions Availability");
				caption.setStyle("-fx-font-weight: bold; -fx-font-family: Verdana; -fx-font-size: 20px");
				GridPane.setConstraints(caption, 0, 0);
				GridPane.setColumnSpan(caption, 3);
				grid.getChildren().add(caption);

				final Separator sepTitle = new Separator();
				sepTitle.setValignment(VPos.CENTER);
				GridPane.setConstraints(sepTitle, 0, 1);
				GridPane.setColumnSpan(sepTitle, 3);
				grid.getChildren().add(sepTitle);

				final Label functionIDLabel = new Label("ID");
				functionIDLabel.setStyle("-fx-font-weight: bold");
				GridPane.setConstraints(functionIDLabel, 0, 2);
				grid.getChildren().add(functionIDLabel);

				final Label functionDescLabel = new Label("Description");
				functionDescLabel.setStyle("-fx-font-weight: bold");
				GridPane.setConstraints(functionDescLabel, 1, 2);
				grid.getChildren().add(functionDescLabel);

				final Label StatusLabel = new Label("Status");
				StatusLabel.setStyle("-fx-font-weight: bold");
				StatusLabel.setMinWidth(100);
				GridPane.setConstraints(StatusLabel, 2, 2);
				grid.getChildren().add(StatusLabel);

				final AnchorPane functionIDPane = new AnchorPane();
				GridPane.setConstraints(functionIDPane, 0, 3);
				grid.getChildren().add(functionIDPane);

				final AnchorPane functionDescPane = new AnchorPane();
				GridPane.setConstraints(functionDescPane, 1, 3);
				grid.getChildren().add(functionDescPane);

				final AnchorPane StatusPane = new AnchorPane();
				GridPane.setConstraints(StatusPane, 2, 3);
				grid.getChildren().add(StatusPane);

				for (int i = 0; i < mFunctions.length(); i++) {
					JSONObject function = mFunctions.getJSONObject(i);

					Label functionIDValue = new Label();
					functionIDValue.setText(String.valueOf(function.getInt("function_id")));
					functionIDValue.setLayoutX(0);
					functionIDValue.setLayoutY(i * 20);

					Label functionDescValue = new Label();
					functionDescValue.setText(function.getString("function_desc"));
					functionDescValue.setLayoutX(0);
					functionDescValue.setLayoutY(i * 20);

					Label StatusValue = new Label();
					StatusValue.setText(function.getString("status"));
					StatusValue.setLayoutX(0);
					StatusValue.setLayoutY(i * 20);

					functionIDPane.getChildren().add(functionIDValue);
					functionDescPane.getChildren().add(functionDescValue);
					StatusPane.getChildren().add(StatusValue);
				}
			}
		});
	}
}
