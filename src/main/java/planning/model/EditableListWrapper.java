package planning.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;

public class EditableListWrapper <T extends Editable> {

    private ObservableList<T> editables;

    public EditableListWrapper() {
        this(FXCollections.observableArrayList());
    }

    public EditableListWrapper(ObservableList<T> editableObservableList) {
        editables = FXCollections.observableArrayList(editableObservableList);
    }

    public ObservableList<T> getEditables() {
        return editables;
    }

    public void setEditables(ObservableList<T> editables) {
        this.editables = editables;
    }
}
