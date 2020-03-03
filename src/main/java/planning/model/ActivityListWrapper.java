package planning.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="activities")
public class ActivityListWrapper {

    private ObservableList<Activity> activities;

    public ActivityListWrapper() {
        this(FXCollections.observableArrayList());
    }

    public ActivityListWrapper(ObservableList<Activity> activityObservableList) {
        activities = FXCollections.observableArrayList(activityObservableList);
    }

    @XmlElement(name = "activity")
    public ObservableList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ObservableList<Activity> activities) {
        this.activities = activities;
    }

}
