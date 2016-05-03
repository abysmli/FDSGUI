package com.fds.gui.controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fds.gui.models.Symptom_List;

import javafx.collections.ObservableList;

public class SymptomTableViewController {

	ObservableList<Symptom_List> symptomData;
	boolean firstRunFlag = true;

	public SymptomTableViewController(ObservableList<Symptom_List> symptomData) {
		this.symptomData = symptomData;
	}
	
	public void generate(JSONArray mSymptoms) {
		if (firstRunFlag) {
			try {
				setSymptoms(mSymptoms);
				firstRunFlag = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setSymptoms(JSONArray mSymptoms) throws Exception {
		for (int i = 0; i < mSymptoms.length(); i++) {
			JSONObject obj = mSymptoms.getJSONObject(i);
			if (i >= symptomData.size()) {
				symptomData.add(new Symptom_List(obj.getInt("symptom_id"), obj.getInt("component_id"),
						obj.getString("description"), obj.getDouble("min_limit"), obj.getDouble("max_limit")));
			} else {
				symptomData.set(i, new Symptom_List(obj.getInt("symptom_id"), obj.getInt("component_id"),
						obj.getString("description"), obj.getDouble("min_limit"), obj.getDouble("max_limit")));
			}
		}
	}


}
