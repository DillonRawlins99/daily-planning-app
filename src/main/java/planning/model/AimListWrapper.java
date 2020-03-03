package planning.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="aims")
public class AimListWrapper {

    private ObservableList<Aim> aims;

    public AimListWrapper() {
        this(FXCollections.observableArrayList());
    }

    public AimListWrapper(ObservableList<Aim> aimObservableList) {
        aims = aimObservableList;
    }

    @XmlElement(name = "aim")
    public ObservableList<Aim> getAims() {
        return aims;
    }

    public void setAims(ObservableList<Aim> aims) {
        this.aims = aims;
    }
}
