package com.fds.gui.models;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Function.
 *
 * @author Li, Yuan
 */
public class Function_List {

    private final IntegerProperty functionId;
    private final StringProperty functionDesc;
    private final StringProperty functionStatus;
    private final StringProperty functionDate;

    /**
     * Default constructor.
     */
    public Function_List() {
        this(0, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param functionId
     * @param componentId
     */
    public Function_List(int functionId, String functionDesc, String functionStatus, String functionDate) {
        this.functionId = new SimpleIntegerProperty(functionId);
        this.functionDesc = new SimpleStringProperty(functionDesc);
        this.functionStatus = new SimpleStringProperty(functionStatus);
        this.functionDate = new SimpleStringProperty(functionDate);
    }

    public int getfunctionId() {
        return functionId.get();
    }

    public void setfunctionId(int functionId) {
        this.functionId.set(functionId);
    }

    public IntegerProperty functionIdProperty() {
        return functionId;
    }

    public String getfunctionDesc() {
        return functionDesc.get();
    }

    public void setfunctionDesc(String functionDesc) {
        this.functionDesc.set(functionDesc);
    }

    public StringProperty functionDescProperty() {
        return functionDesc;
    }

    public String getfunctionStatus() {
        return functionStatus.get();
    }

    public void setfunctionStatus(String functionStatus) {
        this.functionStatus.set(functionStatus);
    }

    public StringProperty functionStatusProperty() {
        return functionStatus;
    }

    public String getfunctionDate() {
        return functionDate.get();
    }

    public void setfunctionDate(String functionDate) {
        this.functionDate.set(functionDate);
    }

    public StringProperty functionDateProperty() {
        return functionDate;
    }
}