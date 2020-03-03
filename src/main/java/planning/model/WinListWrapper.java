package planning.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="wins")
public class WinListWrapper {

    private ObservableList<Win> wins;

    public WinListWrapper() {
        this(FXCollections.observableArrayList());
    }

    public WinListWrapper(ObservableList<Win> winObservableList) {
        this.wins = winObservableList;
    }

    @XmlElement(name="win")
    public ObservableList<Win> getWins() {
        return wins;
    }

    public void setWins(ObservableList<Win> wins) {
        this.wins = wins;
    }
}
