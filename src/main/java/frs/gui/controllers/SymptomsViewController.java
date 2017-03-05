package frs.gui.controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SymptomsViewController {
	AnchorPane symptoms_panel;
	boolean firstRunFlag = true;

	public SymptomsViewController(AnchorPane symptoms_panel) {
		this.symptoms_panel = symptoms_panel;
	}

	public void generate(JSONArray mSymptoms) {
		if (firstRunFlag) {
			final GridPane grid = new GridPane();
			grid.setPadding(new Insets(30, 30, 30, 30));
			grid.setVgap(20);
			grid.setHgap(20);
			grid.setMinWidth(1000);
			AnchorPane.setTopAnchor(grid, 0.0);
			AnchorPane.setRightAnchor(grid, 0.0);
			AnchorPane.setLeftAnchor(grid, 0.0);
			AnchorPane.setBottomAnchor(grid, 0.0);
			symptoms_panel.getChildren().add(grid);

			final Label caption = new Label("Symptoms Check of Problem Diagnose Module");
			caption.setStyle("-fx-font-weight: bold; -fx-font-family: Verdana; -fx-font-size: 20px");
			GridPane.setConstraints(caption, 0, 0);
			GridPane.setColumnSpan(caption, 9);
			grid.getChildren().add(caption);

			final Separator sepTitle = new Separator();
			sepTitle.setValignment(VPos.CENTER);
			GridPane.setConstraints(sepTitle, 0, 1);
			GridPane.setColumnSpan(sepTitle, 9);
			grid.getChildren().add(sepTitle);

			final Label symptomIDLabel = new Label("Symptom ID");
			symptomIDLabel.setStyle("-fx-font-weight: bold");
			GridPane.setConstraints(symptomIDLabel, 0, 2);
			grid.getChildren().add(symptomIDLabel);

			final Label symptomDescLabel = new Label("Symptom Description");
			symptomDescLabel.setStyle("-fx-font-weight: bold");
			GridPane.setConstraints(symptomDescLabel, 1, 2);
			grid.getChildren().add(symptomDescLabel);

			final Label MinLimitLabel = new Label("Min Limit");
			MinLimitLabel.setStyle("-fx-font-weight: bold");
			GridPane.setConstraints(MinLimitLabel, 2, 2);
			grid.getChildren().add(MinLimitLabel);

			final Label MaxLimitLabel = new Label("Max Limit");
			MaxLimitLabel.setStyle("-fx-font-weight: bold");
			GridPane.setConstraints(MaxLimitLabel, 3, 2);
			grid.getChildren().add(MaxLimitLabel);

			final AnchorPane symptomIDPane = new AnchorPane();
			GridPane.setConstraints(symptomIDPane, 0, 3);
			grid.getChildren().add(symptomIDPane);

			final AnchorPane symptomDescPane = new AnchorPane();
			GridPane.setConstraints(symptomDescPane, 1, 3);
			grid.getChildren().add(symptomDescPane);

			final AnchorPane MinLimitPane = new AnchorPane();
			GridPane.setConstraints(MinLimitPane, 2, 3);
			grid.getChildren().add(MinLimitPane);

			final AnchorPane MaxLimitPane = new AnchorPane();
			GridPane.setConstraints(MaxLimitPane, 3, 3);
			grid.getChildren().add(MaxLimitPane);

			for (int i = 0; i < mSymptoms.length(); i++) {
				JSONObject symptom = mSymptoms.getJSONObject(i);

				Label symptomIDValue = new Label();
				symptomIDValue.setText(String.valueOf(symptom.getInt("symptom_id")));
				symptomIDValue.setLayoutX(0);
				symptomIDValue.setLayoutY(i * 20);

				Label symptomDescValue = new Label();
				symptomDescValue.setText(symptom.getString("description"));
				symptomDescValue.setLayoutX(0);
				symptomDescValue.setLayoutY(i * 20);

				Label MinLimitValue = new Label();
				MinLimitValue.setText(String.format("%.2f", symptom.getDouble("min_limit")));
				MinLimitValue.setLayoutX(0);
				MinLimitValue.setLayoutY(i * 20);

				Label MaxLimitValue = new Label();
				MaxLimitValue.setText(String.format("%.2f", symptom.getDouble("max_limit")));
				MaxLimitValue.setLayoutX(0);
				MaxLimitValue.setLayoutY(i * 20);

				symptomIDPane.getChildren().add(symptomIDValue);
				symptomDescPane.getChildren().add(symptomDescValue);
				MinLimitPane.getChildren().add(MinLimitValue);
				MaxLimitPane.getChildren().add(MaxLimitValue);
			}
			firstRunFlag = false;
		}
	}
}
