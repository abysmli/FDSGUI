package com.fds.gui.models;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Subsubsystem.
 *
 * @author Li, Yuan
 */
public class Subsystem_List {

    private final IntegerProperty subsystemId;
    private final StringProperty subsystemDesc;
    private final StringProperty subsystemStatus;
    private final StringProperty subsystemDate;

    /**
     * Default constructor.
     */
    public Subsystem_List() {
        this(0, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param subsystemId
     * @param componentId
     */
    public Subsystem_List(int subsystemId, String subsystemDesc, String subsystemStatus, String subsystemDate) {
        this.subsystemId = new SimpleIntegerProperty(subsystemId);
        this.subsystemDesc = new SimpleStringProperty(subsystemDesc);
        this.subsystemStatus = new SimpleStringProperty(subsystemStatus);
        this.subsystemDate = new SimpleStringProperty(subsystemDate);
    }

    public int getsubsystemId() {
        return subsystemId.get();
    }

    public void setsubsystemId(int subsystemId) {
        this.subsystemId.set(subsystemId);
    }

    public IntegerProperty subsystemIdProperty() {
        return subsystemId;
    }

    public String getsubsystemDesc() {
        return subsystemDesc.get();
    }

    public void setsubsystemDesc(String subsystemDesc) {
        this.subsystemDesc.set(subsystemDesc);
    }

    public StringProperty subsystemDescProperty() {
        return subsystemDesc;
    }

    public String getsubsystemStatus() {
        return subsystemStatus.get();
    }

    public void setsubsystemStatus(String subsystemStatus) {
        this.subsystemStatus.set(subsystemStatus);
    }

    public StringProperty subsystemStatusProperty() {
        return subsystemStatus;
    }

    public String getsubsystemDate() {
        return subsystemDate.get();
    }

    public void setsubsystemDate(String subsystemDate) {
        this.subsystemDate.set(subsystemDate);
    }

    public StringProperty subsystemDateProperty() {
        return subsystemDate;
    }
}