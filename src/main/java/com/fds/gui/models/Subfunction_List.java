package com.fds.gui.models;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Subsubfunction.
 *
 * @author Li, Yuan
 */
public class Subfunction_List {

    private final IntegerProperty subfunctionId;
    private final StringProperty subfunctionDesc;
    private final StringProperty subfunctionStatus;
    private final StringProperty subfunctionDate;

    /**
     * Default constructor.
     */
    public Subfunction_List() {
        this(0, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param subfunctionId
     * @param componentId
     */
    public Subfunction_List(int subfunctionId, String subfunctionDesc, String subfunctionStatus, String subfunctionDate) {
        this.subfunctionId = new SimpleIntegerProperty(subfunctionId);
        this.subfunctionDesc = new SimpleStringProperty(subfunctionDesc);
        this.subfunctionStatus = new SimpleStringProperty(subfunctionStatus);
        this.subfunctionDate = new SimpleStringProperty(subfunctionDate);
    }

    public int getsubfunctionId() {
        return subfunctionId.get();
    }

    public void setsubfunctionId(int subfunctionId) {
        this.subfunctionId.set(subfunctionId);
    }

    public IntegerProperty subfunctionIdProperty() {
        return subfunctionId;
    }

    public String getsubfunctionDesc() {
        return subfunctionDesc.get();
    }

    public void setsubfunctionDesc(String subfunctionDesc) {
        this.subfunctionDesc.set(subfunctionDesc);
    }

    public StringProperty subfunctionDescProperty() {
        return subfunctionDesc;
    }

    public String getsubfunctionStatus() {
        return subfunctionStatus.get();
    }

    public void setsubfunctionStatus(String subfunctionStatus) {
        this.subfunctionStatus.set(subfunctionStatus);
    }

    public StringProperty subfunctionStatusProperty() {
        return subfunctionStatus;
    }

    public String getsubfunctionDate() {
        return subfunctionDate.get();
    }

    public void setsubfunctionDate(String subfunctionDate) {
        this.subfunctionDate.set(subfunctionDate);
    }

    public StringProperty subfunctionDateProperty() {
        return subfunctionDate;
    }
}