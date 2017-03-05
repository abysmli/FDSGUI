package frs.gui.models;
import org.json.JSONObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Fault.
 *
 * @author Li, Yuan
 */
public class Fault_List {

    private final IntegerProperty faultId;
    private final IntegerProperty componentId;
    private final StringProperty faultType;
    private final StringProperty faultDesc;
    private final ObjectProperty<JSONObject> executeCommand;
    private final StringProperty insertDate;

    /**
     * Default constructor.
     */
    public Fault_List() {
        this(0, 0, null, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param faultId
     * @param componentId
     */
    public Fault_List(int faultId, int componentId, String faultType, String faultDesc, JSONObject executeCommand, String insertDate) {
        this.faultId = new SimpleIntegerProperty(faultId);
        this.componentId = new SimpleIntegerProperty(componentId);
        this.faultType = new SimpleStringProperty(faultType);
        this.faultDesc = new SimpleStringProperty(faultDesc);
        this.executeCommand = new SimpleObjectProperty<JSONObject>(executeCommand);
        this.insertDate = new SimpleStringProperty(insertDate);
    }

    public int getfaultId() {
        return faultId.get();
    }

    public void setfaultId(int faultId) {
        this.faultId.set(faultId);
    }

    public IntegerProperty faultIdProperty() {
        return faultId;
    }

    public int getcomponentId() {
        return componentId.get();
    }

    public void setcomponentId(int componentId) {
        this.componentId.set(componentId);
    }

    public IntegerProperty componentIdProperty() {
        return componentId;
    }

    public String getfaultType() {
        return faultType.get();
    }

    public void setfaultType(String faultType) {
        this.faultType.set(faultType);
    }

    public StringProperty faultTypeProperty() {
        return faultType;
    }

    public String getfaultDesc() {
        return faultDesc.get();
    }

    public void setfaultDesc(String faultDesc) {
        this.faultDesc.set(faultDesc);
    }

    public StringProperty faultDescProperty() {
        return faultDesc;
    }

    public JSONObject getexecuteCommand() {
        return executeCommand.get();
    }

    public void setexecuteCommand(JSONObject executeCommand) {
        this.executeCommand.set(executeCommand);
    }

    public ObjectProperty<JSONObject> executeCommandProperty() {
        return executeCommand;
    }

    public String getinsertDate() {
        return insertDate.get();
    }

    public void setinsertDate(String insertDate) {
        this.insertDate.set(insertDate);
    }

    public StringProperty insertDateProperty() {
        return insertDate;
    }
}