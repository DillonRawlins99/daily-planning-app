package planning.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Maybe make this an interface instead??? No because we can implement methods with inheritance, so abstract class?
public class Editable {
    private StringProperty editableName;
    private StringProperty editableDescription;

    public Editable() {
        this("","");
    }

    public Editable(String editableName, String editableDescription) {
        this.editableName = new SimpleStringProperty(editableName);
        this.editableDescription = new SimpleStringProperty(editableDescription);
    }

    public String getEditableName() {
        return editableName.get();
    }

    public StringProperty editableNameProperty() {
        return editableName;
    }

    public void setEditableName(String editableName) {
        this.editableName.set(editableName);
    }

    public String getEditableDescription() {
        return editableDescription.get();
    }

    public StringProperty editableDescriptionProperty() {
        return editableDescription;
    }

    public void setEditableDescription(String editableDescription) {
        this.editableDescription.set(editableDescription);
    }

    public String toString() {
        return getEditableName();
    }
}
