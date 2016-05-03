package com.fds.gui.models;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Submainfunction.
 *
 * @author Li, Yuan
 */
public class Mainfunction_List {

    private final IntegerProperty mainfunctionId;
    private final StringProperty mainfunctionDesc;
    private final StringProperty mainfunctionStatus;
    private final StringProperty mainfunctionDate;

    /**
     * Default constructor.
     */
    public Mainfunction_List() {
        this(0, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param mainfunctionId
     * @param componentId
     */
    public Mainfunction_List(int mainfunctionId, String mainfunctionDesc, String mainfunctionStatus, String mainfunctionDate) {
        this.mainfunctionId = new SimpleIntegerProperty(mainfunctionId);
        this.mainfunctionDesc = new SimpleStringProperty(mainfunctionDesc);
        this.mainfunctionStatus = new SimpleStringProperty(mainfunctionStatus);
        this.mainfunctionDate = new SimpleStringProperty(mainfunctionDate);
    }

    public int getmainfunctionId() {
        return mainfunctionId.get();
    }

    public void setmainfunctionId(int mainfunctionId) {
        this.mainfunctionId.set(mainfunctionId);
    }

    public IntegerProperty mainfunctionIdProperty() {
        return mainfunctionId;
    }

    public String getmainfunctionDesc() {
        return mainfunctionDesc.get();
    }

    public void setmainfunctionDesc(String mainfunctionDesc) {
        this.mainfunctionDesc.set(mainfunctionDesc);
    }

    public StringProperty mainfunctionDescProperty() {
        return mainfunctionDesc;
    }

    public String getmainfunctionStatus() {
        return mainfunctionStatus.get();
    }

    public void setmainfunctionStatus(String mainfunctionStatus) {
        this.mainfunctionStatus.set(mainfunctionStatus);
    }

    public StringProperty mainfunctionStatusProperty() {
        return mainfunctionStatus;
    }

    public String getmainfunctionDate() {
        return mainfunctionDate.get();
    }

    public void setmainfunctionDate(String mainfunctionDate) {
        this.mainfunctionDate.set(mainfunctionDate);
    }

    public StringProperty mainfunctionDateProperty() {
        return mainfunctionDate;
    }
}