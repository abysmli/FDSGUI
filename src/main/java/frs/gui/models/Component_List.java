package frs.gui.models;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Subcomponent.
 *
 * @author Li, Yuan
 */
public class Component_List {

    private final IntegerProperty componentId;
    private final StringProperty componentName;
    private final StringProperty componentSeries;
    private final StringProperty componentType;
    private final StringProperty componentSymbol;
    private final StringProperty componentDesc;
    private final StringProperty componentActivition;
    private final StringProperty componentStatus;
    private final StringProperty componentDate;

    /**
     * Default constructor.
     */
    public Component_List() {
        this(0, null, null, null, null, null, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param componentId
     * @param componentId
     */
    public Component_List(int componentId, String componentName, String componentSeries, String componentType, String componentSymbol, String componentDesc, String componentActivition, String componentStatus, String componentDate) {
        this.componentId = new SimpleIntegerProperty(componentId);
        this.componentName = new SimpleStringProperty(componentName);
        this.componentSeries = new SimpleStringProperty(componentSeries);
        this.componentType = new SimpleStringProperty(componentType);
        this.componentSymbol = new SimpleStringProperty(componentSymbol);
        this.componentDesc = new SimpleStringProperty(componentDesc);
        this.componentActivition = new SimpleStringProperty(componentActivition);
        this.componentStatus = new SimpleStringProperty(componentStatus);
        this.componentDate = new SimpleStringProperty(componentDate);
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

    public String getcomponentName() {
        return componentDesc.get();
    }

    public void setcomponentName(String componentName) {
        this.componentName.set(componentName);
    }

    public StringProperty componentNameProperty() {
        return componentName;
    }
    
    public String getcomponentSeries() {
        return componentSeries.get();
    }

    public void setcomponentSeries(String componentSeries) {
        this.componentSeries.set(componentSeries);
    }

    public StringProperty componentSeriesProperty() {
        return componentSeries;
    }
    
    public String getcomponentType() {
        return componentType.get();
    }

    public void setcomponentType(String componentType) {
        this.componentType.set(componentType);
    }

    public StringProperty componentTypeProperty() {
        return componentType;
    }
    
    public String getcomponentSymbol() {
        return componentSymbol.get();
    }

    public void setcomponentSymbol(String componentSymbol) {
        this.componentSymbol.set(componentSymbol);
    }

    public StringProperty componentSymbolProperty() {
        return componentSymbol;
    }
    
    public String getcomponentDesc() {
        return componentDesc.get();
    }

    public void setcomponentDesc(String componentDesc) {
        this.componentDesc.set(componentDesc);
    }

    public StringProperty componentDescProperty() {
        return componentDesc;
    }
    
    public String getcomponentActivition() {
        return componentActivition.get();
    }

    public void setcomponentActivition(String componentActivition) {
        this.componentActivition.set(componentActivition);
    }

    public StringProperty componentActivitionProperty() {
        return componentActivition;
    }

    public String getcomponentStatus() {
        return componentStatus.get();
    }

    public void setcomponentStatus(String componentStatus) {
        this.componentStatus.set(componentStatus);
    }

    public StringProperty componentStatusProperty() {
        return componentStatus;
    }

    public String getcomponentDate() {
        return componentDate.get();
    }

    public void setcomponentDate(String componentDate) {
        this.componentDate.set(componentDate);
    }

    public StringProperty componentDateProperty() {
        return componentDate;
    }
}