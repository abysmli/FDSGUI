package frs.gui.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Symptom_List {

	    private final IntegerProperty symptomId;
	    private final IntegerProperty componentId;
	    private final StringProperty description;
	    private final DoubleProperty minLimit;
	    private final DoubleProperty maxLimit;
	    /**
	     * Default constructor.
	     */
	    public Symptom_List() {
	        this(0, 0, null, 0.0, 0.0);
	    }

	    /**
	     * Constructor with some initial data.
	     * 
	     * @param symptomId
	     * @param componentId
	     */
	    public Symptom_List(int symptomId, int componentId, String description, double minLimit, double maxLimit) {
	        this.symptomId = new SimpleIntegerProperty(symptomId);
	        this.componentId = new SimpleIntegerProperty(componentId);
	        this.description = new SimpleStringProperty(description);
	        this.minLimit = new SimpleDoubleProperty(minLimit);
	        this.maxLimit = new SimpleDoubleProperty(maxLimit);
	    }

	    public int getsymptomId() {
	        return symptomId.get();
	    }

	    public void setsymptomId(int symptomId) {
	        this.symptomId.set(symptomId);
	    }

	    public IntegerProperty symptomIdProperty() {
	        return symptomId;
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
	    
	    public String getdescription() {
	        return description.get();
	    }

	    public void setdescription(String description) {
	        this.description.set(description);
	    }

	    public StringProperty descriptionProperty() {
	        return description;
	    }
	    
	    public double getminLimit() {
	        return minLimit.get();
	    }

	    public void setminLimit(double minLimit) {
	        this.minLimit.set(minLimit);
	    }

	    public DoubleProperty minLimitProperty() {
	        return minLimit;
	    }
	    
	    public double getmaxLimit() {
	        return maxLimit.get();
	    }

	    public void setmaxLimit(double maxLimit) {
	        this.maxLimit.set(maxLimit);
	    }

	    public DoubleProperty maxLimitProperty() {
	        return maxLimit;
	    }

}
