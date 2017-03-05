package frs.gui.controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import frs.gui.models.Component_List;
import frs.gui.models.Fault_List;
import frs.gui.models.Function_List;
import frs.gui.models.Mainfunction_List;
import frs.gui.models.Subfunction_List;
import frs.gui.models.Subsystem_List;

import javafx.collections.ObservableList;

public class DatabaseTableViewController {
	
	JSONObject allDataTable;
	ObservableList<Fault_List> faultData;
	ObservableList<Function_List> functionData;
	ObservableList<Subfunction_List> subfunctionData;
	ObservableList<Mainfunction_List> mainfunctionData;
	ObservableList<Component_List> componentData;
	ObservableList<Subsystem_List> subsystemData;
	
	public DatabaseTableViewController(ObservableList<Fault_List> faultData, ObservableList<Function_List> functionData,
			ObservableList<Subfunction_List> subfunctionData, ObservableList<Mainfunction_List> mainfunctionData,
			ObservableList<Component_List> componentData, ObservableList<Subsystem_List> subsystemData, JSONObject allDataTable) {
		this.allDataTable = allDataTable;
		this.faultData = faultData;
		this.functionData = functionData;
		this.subfunctionData = subfunctionData;
		this.mainfunctionData = mainfunctionData;
		this.componentData = componentData;
		this.subsystemData = subsystemData;
	}
	
	public void refresh() {
		try {
			setComponents(allDataTable.getJSONArray("Components"));
			setFunctions(allDataTable.getJSONArray("Functions"));
			setFaults(allDataTable.getJSONArray("Faults"));
			setMainfunctions(allDataTable.getJSONArray("Mainfunctions"));
			setSubfunctions(allDataTable.getJSONArray("Subfunctions"));
			setSubsystems(allDataTable.getJSONArray("Subsystems"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setMainfunctions(JSONArray mMainfunctions) throws Exception {
		for (int i = 0; i < mMainfunctions.length(); i++) {
			JSONObject obj = mMainfunctions.getJSONObject(i);
			if (i >= mainfunctionData.size()) {
				mainfunctionData.add(new Mainfunction_List(obj.getInt("mainfunction_id"),
						obj.getString("mainfunction_desc"), obj.getString("status"), obj.getString("insert_date")));
			} else {
				if (!obj.getString("status").equals(mainfunctionData.get(i).getmainfunctionStatus())) {
					mainfunctionData.set(i, new Mainfunction_List(obj.getInt("mainfunction_id"),
							obj.getString("mainfunction_desc"), obj.getString("status"), obj.getString("insert_date")));
				}
			}
		}
	}

	private void setSubsystems(JSONArray mSubsystems) throws Exception {
		for (int i = 0; i < mSubsystems.length(); i++) {
			JSONObject obj = mSubsystems.getJSONObject(i);
			if (i >= subsystemData.size()) {
				subsystemData.add(new Subsystem_List(obj.getInt("subsystem_id"), obj.getString("subsystem_desc"),
						obj.getString("status"), obj.getString("insert_date")));
			} else {
				if (!obj.getString("status").equals(subsystemData.get(i).getsubsystemStatus())) {
					subsystemData.set(i, new Subsystem_List(obj.getInt("subsystem_id"), obj.getString("subsystem_desc"),
							obj.getString("status"), obj.getString("insert_date")));
				}
			}
		}
	}

	private void setComponents(JSONArray mComponents) throws Exception {
		for (int i = 0; i < mComponents.length(); i++) {
			JSONObject obj = mComponents.getJSONObject(i);
			if (i >= componentData.size()) {
				componentData.add(new Component_List(obj.getInt("component_id"), obj.getString("component_name"),
						obj.getString("series"), obj.getString("type"), obj.getString("component_symbol"), obj.getString("component_desc"),
						obj.getString("activition"), obj.getString("status"), obj.getString("insert_date")));
			} else {
				if (!obj.getString("status").equals(componentData.get(i).getcomponentStatus())) {
					componentData.set(i, new Component_List(obj.getInt("component_id"), obj.getString("component_name"),
							obj.getString("series"), obj.getString("type"), obj.getString("component_symbol"), obj.getString("component_desc"),
							obj.getString("activition"), obj.getString("status"), obj.getString("insert_date")));
				}
			}
		}
	}

	private void setFunctions(JSONArray mFunctions) throws Exception {
		for (int i = 0; i < mFunctions.length(); i++) {
			JSONObject obj = mFunctions.getJSONObject(i);
			if (i >= functionData.size()) {
				functionData.add(new Function_List(obj.getInt("function_id"), obj.getString("function_desc"),
						obj.getString("status"), obj.getString("insert_date")));
			} else {
				if (!obj.getString("status").equals(functionData.get(i).getfunctionStatus())) {
					functionData.set(i, new Function_List(obj.getInt("function_id"), obj.getString("function_desc"),
							obj.getString("status"), obj.getString("insert_date")));
				}
			}
		}
	}

	private void setSubfunctions(JSONArray mSubfunctions) throws Exception {
		for (int i = 0; i < mSubfunctions.length(); i++) {
			JSONObject obj = mSubfunctions.getJSONObject(i);
			if (i >= subfunctionData.size()) {
				subfunctionData.add(new Subfunction_List(obj.getInt("subfunction_id"),
						obj.getString("subfunction_desc"), obj.getString("status"), obj.getString("insert_date")));
			} else {
				if (!obj.getString("status").equals(subfunctionData.get(i).getsubfunctionStatus())) {
					subfunctionData.set(i, new Subfunction_List(obj.getInt("subfunction_id"),
							obj.getString("subfunction_desc"), obj.getString("status"), obj.getString("insert_date")));
				}
			}
		}
	}

	private void setFaults(JSONArray mFaults) throws Exception {
		for (int i = 0; i < mFaults.length(); i++) {
			JSONObject obj = mFaults.getJSONObject(i);
			if (i >= faultData.size()) {
				faultData.add(new Fault_List(obj.getInt("fault_id"), obj.getInt("component_id"),
						obj.getString("fault_type"), obj.getString("fault_desc"), obj.getJSONObject("execute_command"),
						obj.getString("insert_date")));
			}
		}
	}
}
